package com.example.simpleapp.model;

import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("albumId")
    private int albumId;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;

    public int getId() {
        return id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
