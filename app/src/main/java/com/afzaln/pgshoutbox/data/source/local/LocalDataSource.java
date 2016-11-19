package com.afzaln.pgshoutbox.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afzaln.pgshoutbox.data.models.Post;
import com.afzaln.pgshoutbox.data.source.DataSource;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by afzal on 2016-11-19.
 */
public class LocalDataSource implements DataSource {
    private static LocalDataSource INSTANCE = null;
    private List<Post> posts;

    private LocalDataSource(@NonNull Context context)  {
        checkNotNull(context);

        // initialize database here ...
        posts = new ArrayList<>();
        posts.add(Post.from(1, 1, "Title1", "Body1"));
        posts.add(Post.from(2, 2, "Title2", "Body2"));
    }

    @Override
    public Observable<List<Post>> getPosts() {
        return Observable.just(posts);
    }

    /**
     * Write newly fetched posts to database
     * @param posts
     */
    public void updatePosts(List<Post> posts) {
        this.posts = posts;
    }

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
