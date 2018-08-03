package com.example.wz.lovingpets.net;

import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.utils.LoggingInterceptor;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class HttpRequest {
    public static final String BASE_URL = "http://119.29.250.84:8080";
    private static OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
            .addNetworkInterceptor(new LoggingInterceptor());
    private static ApiService apiService;

    public static ApiService getApiservice(){
        if(apiService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public interface ApiService {
        // 登录用户
        @FormUrlEncoded
        @POST("/IntergratedPlatform/UserAction/login")
        Observable<ListResponse<User>> login(
                @Field("userName") String username,
                @Field("password") String password);

    }
    public Observable<ListResponse<User>> login(String username, String password) {
        return apiService.login(username, password);
    }
}
