package android.outstandfood_client.interfaces;

import android.outstandfood_client.models.AddressResponse;
import android.outstandfood_client.models.Category;
import android.outstandfood_client.models.HistoryModel;
import android.outstandfood_client.models.ListCategory;
import android.outstandfood_client.models.ListProduct;
import android.outstandfood_client.models.OrderModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH-mm-ss").create();

    ApiService API_SERVICER=new Retrofit.Builder()
            .baseUrl("https://outstanfood-com.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .build()
            .create(ApiService.class);

    @GET("category/list")
    Call<ListCategory> getListCate();
    @GET("product/list")
    Call<ListProduct> getListProduct(@Query("id_category") String id_category);

    @POST("order/add?")
    Call <OrderModel> addOrder(@Body OrderModel orderModel);

    @GET("order/ordered/{id_user}")
    Call<List<HistoryModel>> getOrderById(@Path("id_user") String id_user);

    @GET("product/list")
    Call<ListProduct> getProductList(@Query("id_category") String categoryId);

    // This is API for get all product include food, drink ...etc.
    @GET("product/list")
    Call<ListProduct> getProductList();

    @GET("address/list")
    Call<AddressResponse> getAddressList(@Query("id_user") String userId);
}
