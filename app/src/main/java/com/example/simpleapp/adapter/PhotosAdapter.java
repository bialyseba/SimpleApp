package com.example.simpleapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.simpleapp.R;
import com.example.simpleapp.model.Photo;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter {
    private List<Photo> data;
    private RequestManager glide;

    public PhotosAdapter(RequestManager glide, List<Photo> photos){
        this.data = photos;
        this.glide = glide;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemViewLayout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_item, null);
        PhotoViewHolder photoViewHolder = new PhotoViewHolder(itemViewLayout);
        return photoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //configuration of glide and loading images from urls to placeholders
        RequestOptions options = new RequestOptions()
                .placeholder(android.R.drawable.picture_frame)
                .error(android.R.drawable.stat_notify_error);

        glide.load(data.get(i).getThumbnailUrl())
                .apply(options)
                .into(((PhotoViewHolder) viewHolder).photoImageView);
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView photoImageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
