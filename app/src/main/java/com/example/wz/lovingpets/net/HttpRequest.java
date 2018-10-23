package com.example.wz.lovingpets.net;

import com.example.wz.lovingpets.entity.GoodsDetailInfo;
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

/**
 * 使用retrofit2+Rxjava2+OkHttp3+自定义的网络日志记录器构建的网络访问类
 * LoggingInterceptor：带格式化json的自定义日志记录器，继承于okhttp3的日志记录器
 */
public class HttpRequest {
    public static final String BASE_URL = "http://192.168.43.234:8080";//网络访问基地址
    private static OkHttpClient.Builder builder = new OkHttpClient()
            .newBuilder()
            .addNetworkInterceptor(new LoggingInterceptor());
    private static ApiService apiService;

    /**
     * 获取单例
     * @return
     */
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
        // 用户登录
        @FormUrlEncoded
        @POST("/petserviceplatform/UserAction/login")
        Observable<ListResponse<User>> login(
                @Field("userName") String username,
                @Field("password") String password);
        // 获取商品
        @FormUrlEncoded
        @POST("/petserviceplatform/GoodsAction/getByClassify")
        Observable<ListResponse<GoodsDetailInfo>> getGoods(
                @Field("classify") String classify,
                @Field("condition") String condition);
        // 加入购物车
        @FormUrlEncoded
        @POST("/petserviceplatform/ShoppingCartAction/addGoodsToCart")
        Observable<ListResponse> addToCart(
                @Field("param") String param);

    }

    public Observable<ListResponse<User>> login(String username, String password) {
        return apiService.login(username, password);
    }

    public Observable<ListResponse<GoodsDetailInfo>> getGoods(String classify,String condition){
        return apiService.getGoods(classify,condition);
    }

    public Observable<ListResponse> addToCart(String param){
        return apiService.addToCart(param);
    }
}
