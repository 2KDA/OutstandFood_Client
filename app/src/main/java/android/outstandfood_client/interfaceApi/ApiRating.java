package android.outstandfood_client.interfaceApi;

import android.outstandfood_client.models.Rating;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRating {

    Gson gson = new GsonBuilder().setLenient().create();

    ApiRating apiRating = new Retrofit.Builder().baseUrl("https://outstanfood-com.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiRating.class);


    @GET("rating/list")
    Call<List<Rating>> getListRating();

    @POST("rating/add")
    Call<Rating> addRating(@Body Rating objRating);
}
