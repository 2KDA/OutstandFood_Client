package android.outstandfood_client.interfaceApi;

import android.outstandfood_client.models.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
        @Multipart
        @PUT("user/edit/{id}")
        Call<User> updateUser(
                @Path("id") String id,
                @Part("name") RequestBody name,
                @Part("phone") RequestBody phone,
                @Part("userEmail") RequestBody userEmail,
                @Part MultipartBody.Part image
        );
    }
