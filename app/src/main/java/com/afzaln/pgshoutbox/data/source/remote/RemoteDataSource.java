package com.afzaln.pgshoutbox.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afzaln.pgshoutbox.data.models.ShoutboxData;
import com.afzaln.pgshoutbox.util.Utils.HashUtils;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by afzal on 2016-11-19.
 */
public class RemoteDataSource {
    private static String DO_VALUE = "login";
    private static String COOKIEUSER_VALUE = "1";

    private static RemoteDataSource INSTANCE = null;
    private static final String API_URL = "http://www.pakgamers.com"; // TODO API URL goes here
    private final ShoutboxApiService shoutboxApiService;
    private final ForumsApiService forumsApiService;


    private RemoteDataSource(@NonNull Context context) {
        checkNotNull(context);
        Retrofit xmlRetrofit = buildRetrofit(context, API_URL, SimpleXmlConverterFactory.create());
        shoutboxApiService = xmlRetrofit.create(ShoutboxApiService.class);

        Retrofit jsonRetrofit = buildRetrofit(context, API_URL, GsonConverterFactory.create());
        forumsApiService = xmlRetrofit.create(ForumsApiService.class);
    }

    // Public methods

    /**
     * Log the user in
     *
     * @param username User's username
     * @param password User's password
     * @return RxObservable with the result
     */
    public Observable<Response<ResponseBody>> login(String username, String password) {
        // TODO return a boolean indication successful login or failure
        String passwordHash = HashUtils.md5(password);
        return shoutboxApiService
                .postLogin(username, password, DO_VALUE, COOKIEUSER_VALUE, passwordHash, passwordHash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    public Observable<ShoutboxData> postMessage(String message, String securityToken) {
        return shoutboxApiService.postMessage(message, "ajax", "shouts", "1", "save", securityToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * @deprecated Call postMessage(message, securityToken) instead
     */
    @Deprecated
    public Observable<ShoutboxData> postMessage(String message) {
        throw new UnsupportedOperationException("Call postMessage(message, securityToken) instead");
    }

    /**
     * Get shouts from remote API
     *
     * @return RxObservable with the shouts
     */
    public Observable<ShoutboxData> getMessages() {
        return shoutboxApiService.getMessages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    // Creation methods

    private Retrofit buildRetrofit(Context context, String baseUrl, Converter.Factory converterFactory) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request build = chain.request().newBuilder()
                            .addHeader("content-type", "application/x-www-form-urlencoded")
                            .build();
                    return chain.proceed(build);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(converterFactory)
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
