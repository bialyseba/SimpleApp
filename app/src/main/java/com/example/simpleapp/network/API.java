package com.example.simpleapp.network;

import com.example.simpleapp.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    @GET("/photos")
    Call<List<Photo>> getPhotos();
}
