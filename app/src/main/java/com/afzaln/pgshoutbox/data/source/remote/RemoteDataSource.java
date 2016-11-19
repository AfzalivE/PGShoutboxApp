package com.afzaln.pgshoutbox.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afzaln.pgshoutbox.data.models.Post;
import com.afzaln.pgshoutbox.data.source.DataSource;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by afzal on 2016-11-19.
 */
public class RemoteDataSource implements DataSource {
    private static RemoteDataSource INSTANCE = null;
    private static final String API_URL = "https://jsonplaceholder.typicode.com"; // TODO API URL goes here
    private final RemoteApiService remoteApiService;


    private RemoteDataSource(@NonNull Context context)  {
        checkNotNull(context);
        Retrofit retrofit = buildRetrofit(API_URL);
        remoteApiService = retrofit.create(RemoteApiService.class);
    }

    // Public methods

    /**
     * Get posts from remote API
     *
     * @return RxObservable with the posts
     */
    public Observable<List<Post>> getPosts() {
        return remoteApiService.getPosts();
    }

    // Creation methods

    private Retrofit buildRetrofit(String baseUrl) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request build = chain.request().newBuilder().addHeader("Content-type", "application/json").build();
                    return chain.proceed(build);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build();

        return retrofit;
    }

    // Static creation methods

    /**
     * Returns the single instance from this class, creating it if necessary.
     *
     * @param dataSource the local data source
     * @return the {@link RemoteDataSource} instance
     */

    public static RemoteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(context);
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
