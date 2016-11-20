package com.afzaln.pgshoutbox.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afzaln.pgshoutbox.data.models.ShoutboxData;
import com.afzaln.pgshoutbox.data.source.DataSource;

import okhttp3.ResponseBody;
import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by afzal on 2016-11-19.
 */
public class LocalDataSource implements DataSource {
    private static LocalDataSource INSTANCE = null;

    private LocalDataSource(@NonNull Context context)  {
        checkNotNull(context);

        // initialize database here ...
    }

    /**
     * Not called locally
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Observable<ResponseBody> login(String username, String password) {
        return null;
    }

    @Override
    public Observable<ShoutboxData> postMessage(String message) {

        return null;
    }

    @Override
    public Observable<ShoutboxData> getMessages() {
        return null;
    }

//    /**
//     * Write newly fetched posts to database
//     * @param posts
//     */
//    public void updatePosts(List<Post> posts) {
//        this.posts = posts;
//    }

    /**
     * Returns the single instance from this class, creating it if necessary.
     *
     * @param dataSource the local data source
     * @return the {@link LocalDataSource} instance
     */

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }

        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(Context)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
