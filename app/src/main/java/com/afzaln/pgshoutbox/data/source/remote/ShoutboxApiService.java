package com.afzaln.pgshoutbox.data.source.remote;

import com.afzaln.pgshoutbox.data.models.ShoutboxData;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Retrofit HTTP API Interface
 * https://square.github.io/retrofit/
 */

interface ShoutboxApiService {
    String USERNAME_FIELD = "vb_login_username";
    String PASSWORD_FIELD = "vb_login_password";
    String DO_FIELD = "do";
    String COOKIEUSER_FIELD = "cookieuser";
    String MD5_PASSWORD_FIELD = "vb_login_md5password";
    String MD5_PASSWORD_FIELD_UTF = "vb_login_md5password_utf";

    @GET("/forums/vbshout.php?tabs[shouts]=1&do=ajax&instanceid=1&action=fetch")
    Observable<ShoutboxData> getMessages();

    @FormUrlEncoded
    @POST("/forums/vbshout.php")
    Observable<ShoutboxData> postMessage(
            @Field("message") String message,
            @Field(DO_FIELD) String doValue,
            @Field("tabid") String tabId,
            @Field("instanceid") String instanceId,
            @Field("action") String action,
            @Field("securitytoken") String securityToken);

    @FormUrlEncoded
    @POST("/forums/login.php?do=login")
    Observable<Response<ResponseBody>> postLogin(
            @Field(USERNAME_FIELD) String username,
            @Field(PASSWORD_FIELD) String password,
            @Field(DO_FIELD) String doValue,
            @Field(COOKIEUSER_FIELD) String cookieUser,
            @Field(MD5_PASSWORD_FIELD) String passwordHash,
            @Field(MD5_PASSWORD_FIELD_UTF) String passwordHashUtf);
}
