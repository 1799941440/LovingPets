package com.example.wz.lovingpets.net;

import com.example.wz.lovingpets.entity.Address;
import com.example.wz.lovingpets.entity.GoodsDetailInfo;
import com.example.wz.lovingpets.entity.ListResponse;
import com.example.wz.lovingpets.entity.OrderInfo;
import com.example.wz.lovingpets.entity.PetInfo;
import com.example.wz.lovingpets.entity.ShoppingCartDetail;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.utils.LoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 使用retrofit2+Rxjava2+OkHttp3+自定义的网络日志记录器构建的网络访问类
 * LoggingInterceptor：带格式化json的自定义日志记录器，继承于okhttp3的日志记录器
 */
public class HttpRequest {
//    public static final String BASE_URL = "http://192.168.1.106:8080";//网络访问基地址
    public static final String BASE_URL = "http://192.168.43.234:8080";//网络访问基地址
//    public static final String BASE_URL = "http://193.112.48.16:8080";//网络访问基地址
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
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
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

        // 获取收货地址
        @FormUrlEncoded
        @POST("/petserviceplatform/AddressAction/getAllAddress")
        Observable<ListResponse<Address>> getAllAddress(
                @Field("userId") int userId);

        // 获取订单
        @FormUrlEncoded
        @POST("/petserviceplatform/OrderAction/getOrdersByUserId")
        Observable<ListResponse<OrderInfo>> getOrder(
                @Field("userId") int userId);

        // 删除订单
        @FormUrlEncoded
        @POST("/petserviceplatform/OrderAction/deleteOrder")
        Observable<ListResponse> delOrder(
                @Field("orderId") int orderId);

        // 收货、付款
        @FormUrlEncoded
        @POST("/petserviceplatform/OrderAction/changeOrderState")
        Observable<ListResponse> changeOrderState(
                @Field("id") int orderId,
                @Field("orderState") int orderState);

        // 修改、添加、收货地址
        @FormUrlEncoded
        @POST("/petserviceplatform/AddressAction/addAddress")
        Observable<ListResponse> manageAddress(
                @Field("id") int id,
                @Field("userId") int userId,
                @Field("receiver") String receiver,
                @Field("contact") String contact,
                @Field("province") String province,
                @Field("city") String city,
                @Field("fullAddress") String fullAddress,
                @Field("isCommonAddress") int isCommonAddress);

        @FormUrlEncoded
        @POST("/petserviceplatform/PetAction/getMyPet")
        Observable<ListResponse<PetInfo>> getPets(@Field("id")int id);

        @FormUrlEncoded
        @POST("/petserviceplatform/PetAction/delete")
        Observable<ListResponse> delete(@Field("petId")int id);

        @FormUrlEncoded
        @POST("/petserviceplatform/PetAction/managePet")
        Observable<ListResponse> managePet(@Field("id") int id,
                                             @Field("nickName")String nickName,
                                             @Field("familyId")int familyId,
                                             @Field("userId")int userId,
                                             @Field("classId")int classId,
                                             @Field("state")String state,
                                             @Field("sex")String sex,
                                             @Field("icon")String icon,
                                             @Field("bs")String bs);

        @FormUrlEncoded
        @POST("/petserviceplatform/UploadAction/getToken")
        Observable<String> getToken(@Field("path") String path);

        @FormUrlEncoded
        @POST("/petserviceplatform/ShoppingCartAction/getMyCart")
        Observable<ListResponse<ShoppingCartDetail>> getSC(@Field("shoppingCartId") Integer shoppingCartId);
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

    public Observable<ListResponse<Address>> getAllAddress(int userId){
        return apiService.getAllAddress(userId);
    }

    public Observable<ListResponse<OrderInfo>> getOrder(int userId){
        return apiService.getOrder(userId);
    }

    public Observable<ListResponse> delOrder(int orderId){
        return apiService.delOrder(orderId);
    }

    public Observable<ListResponse> changeOrderState(int orderId,int orderState){
        return apiService.changeOrderState(orderId,orderState);
    }

    public Observable<ListResponse> manageAddress( int id,int userId,String receiver, String contact,
                      String province,String city,String fullAddress,int isCommonAddress){
        return apiService.manageAddress(id,userId,receiver,contact,
                province,city,fullAddress,isCommonAddress);
    }
    
    public Observable<ListResponse<PetInfo>> getPets(int id){
        return apiService.getPets(id);
    }

    public Observable<ListResponse> deletePet(int id){
        return apiService.delete(id);
    }

    public Observable<ListResponse> update(int id,String nickName,int familyId,int userId,
                                           int classId,String state,String sex,String icon,String bs){
        return apiService.managePet(id,nickName,familyId,userId,
        classId,state,sex,icon,bs);
    }

    public Observable<String> getToken(String path){
        return apiService.getToken(path);
    }

    public Observable<ListResponse<ShoppingCartDetail>> getSC(Integer SCId){
        return apiService.getSC(SCId);
    }
}
