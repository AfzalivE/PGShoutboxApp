package com.afzaln.pgshoutbox.data.source;

import android.support.annotation.NonNull;

import com.afzaln.pgshoutbox.data.models.Post;
import com.afzaln.pgshoutbox.data.source.local.LocalDataSource;
import com.afzaln.pgshoutbox.data.source.preference.PreferenceProvider;
import com.afzaln.pgshoutbox.data.source.remote.RemoteDataSource;

import java.util.List;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by afzal on 2016-11-19.
 */
public class DataRepository implements DataSource {
    private static DataRepository INSTANCE = null;
    private final PreferenceProvider preferenceProvider;
    private final LocalDataSource localDataSource;
    private final RemoteDataSource remoteDataSource;

    private DataRepository(@NonNull LocalDataSource localdataSource, RemoteDataSource remoteDataSource, PreferenceProvider preferenceProvider) {
        this.localDataSource = checkNotNull(localdataSource);
        this.remoteDataSource = checkNotNull(remoteDataSource);
        this.preferenceProvider = checkNotNull(preferenceProvider);
    }

    @Override
    public Observable<List<Post>> getPosts() {
        return remoteDataSource
                .getPosts()
                .compose(saveLocalPosts());
    }

    private Observable.Transformer<List<Post>, List<Post>> saveLocalPosts() {
        return posts -> posts.doOnNext(this::saveLocalPosts);
    }

    private void saveLocalPosts(List<Post> posts) {
        localDataSource.updatePosts(posts);
    }

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
