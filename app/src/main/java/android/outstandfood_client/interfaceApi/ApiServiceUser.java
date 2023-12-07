package android.outstandfood_client.interfaceApi;

import android.outstandfood_client.models.AddressModel;
import android.outstandfood_client.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiServiceUser {
    @POST("user/login")
    Call<User> login(@Body User loginRequest);
    @FormUrlEncoded
    @POST("user/changePassword")
    Call<Void> changePassword(
            @Field("userName") String userName,
            @Field("password") String oldPassword,
            @Field("newPassword") String newPassword
    );
    @PUT("user/edit/{id}")
    @FormUrlEncoded
    Call<User> updateUser(
            @Path("id") String id,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("userEmail") String userEmail,
            @Field("image") String image
    );
}
