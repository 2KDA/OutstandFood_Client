package android.outstandfood_client.interfaceApi;

import android.outstandfood_client.models.AddressModel;
import android.outstandfood_client.models.AddressResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServiceAddress {
    @GET("address/list")
    Call<AddressResponse> getAddressList(@Query("id_user") String userId);
    @FormUrlEncoded
    @POST("address/add")
    Call<AddressModel> addAddress(
            @Field("id_user") String id_user,
            @Field("address") String address,
            @Field("phone") String phone
    );
    @PUT("address/update/{id}")
    @FormUrlEncoded
    Call<AddressModel> updateAddress(
            @Path("id") String id,
            @Field("address") String address,
            @Field("phone") String phone
    );

    @DELETE("address/delete/{id}")
    Call<Void> deleteAddress(@Path("id") String id);

}


