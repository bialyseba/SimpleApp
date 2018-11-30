package com.example.simpleapp.network;

import com.example.simpleapp.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRequest {
    private final Retrofit retrofit;
    private final String URL = "https://jsonplaceholder.typicode.com";

    public APIRequest(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getPhotos(Callback callback){
        API apiService = retrofit.create(API.class);
        Call<List<Photo>> call = apiService.getPhotos();
        call.enqueue(callback);
    }
}
