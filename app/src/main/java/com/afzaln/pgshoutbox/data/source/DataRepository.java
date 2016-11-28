package com.afzaln.pgshoutbox.data.source;

import android.support.annotation.NonNull;

import com.afzaln.pgshoutbox.data.models.ShoutboxData;
import com.afzaln.pgshoutbox.data.source.local.LocalDataSource;
import com.afzaln.pgshoutbox.data.source.preference.PreferenceProvider;
import com.afzaln.pgshoutbox.data.source.remote.RemoteDataSource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by afzal on 2016-11-19.
 */
public class DataRepository implements DataSource {
    private static DataRepository INSTANCE = null;
    private final PreferenceProvider preferenceProvider;
    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;

    Pattern token = Pattern.compile("var\\s*SECURITYTOKEN\\s*=\\s*\"(.+)\"");

    private DataRepository(@NonNull LocalDataSource localdataSource, RemoteDataSource remoteDataSource, PreferenceProvider preferenceProvider) {
        this.localDataSource = checkNotNull(localdataSource);
        this.remoteDataSource = checkNotNull(remoteDataSource);
        this.preferenceProvider = checkNotNull(preferenceProvider);
    }

    /**
     * Posts login, then posts login again to get a security token.
     * Saves the token in SharedPreferences
     *
     * @param username The user's username
     * @param password The user's password
     * @return Whether the login was successful or not
     */
    @Override
    public Observable<Boolean> login(String username, String password) {
        // TODO have different errors for different cases
        // ex: incorrect login is different from securitytoken doesn't exist
        // this is, after all, a recursive call
        return remoteDataSource.login(username, password).map(response -> {
            Map<String, List<String>> headerMap = response.headers().toMultimap();
            List<String> cookies = headerMap.get("Set-Cookie");
            boolean passwordCookieExists = false;
            for (String cookie : cookies) {
                if (cookie.contains("bb_password")) {
                    if (cookie.contains("bb_password=deleted")) {
                        passwordCookieExists = false;
                    } else {
                        passwordCookieExists = true;
                    }
                }
            }

            if (!passwordCookieExists) {
                Timber.e("Unable to log in");
                return true;
            }

            String responseStr = null;
            try {
                responseStr = response.body().string();
                Matcher matcher = token.matcher(responseStr);
                if (matcher.find()) {
                    String securityToken = matcher.group(1);
                    if (securityToken.isEmpty()) {
                        return false;
                    } else if (securityToken.contains("guest")) {
                        Timber.e("User not logged in");
                    }
                    Timber.d("Got security token, saving");
                    preferenceProvider.saveSecurityToken(securityToken);
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }).flatMap(loggedIn -> {
            if (!loggedIn) {
                Timber.d("Getting security token");
                return login(username, password);
            }

            return Observable.just(loggedIn);
        });
    }

    @Override
    public Observable<ShoutboxData> postMessage(String message) {
        String securityToken = preferenceProvider.getSecurityToken();
        return remoteDataSource.postMessage(message, securityToken);
    }

    @Override
    public Observable<ShoutboxData> getMessages() {
        return remoteDataSource.getMessages();
    }

//    private Observable.Transformer<List<Post>, List<Post>> saveLocalPosts() {
//        return posts -> posts.doOnNext(this::saveLocalPosts);
//    }

//    private void saveLocalPosts(List<Post> posts) {
//        localDataSource.updatePosts(posts);
//    }

    /**
     * Returns the single instance from this class, creating it if necessary.
     *
     * @param dataSource the local data source
     * @return the {@link DataRepository} instance
     */

    public static DataRepository getInstance(LocalDataSource localDataSource, RemoteDataSource remoteDataSource, PreferenceProvider preferenceProvider) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(localDataSource, remoteDataSource, preferenceProvider);
        }

        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(LocalDataSource, RemoteDataSource, PreferenceProvider)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
