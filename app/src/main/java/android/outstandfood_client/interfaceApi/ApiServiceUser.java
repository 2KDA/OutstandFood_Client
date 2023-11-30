package android.outstandfood_client.interfaceApi;

import android.outstandfood_client.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServiceUser {
    @POST("user/login")
    Call<User> login(@Body User loginRequest);
}
