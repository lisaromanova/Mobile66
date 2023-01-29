package com.example.exm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @GET("Movies")
    Call<List<Model>> getmodel();

    @POST("Movies")
    Call<Model> postData(@Body Model moviesModal);

    @PUT("Movies/")
    Call<Model> putData(@Query("ID") int id, @Body Model moviesModal);
}
