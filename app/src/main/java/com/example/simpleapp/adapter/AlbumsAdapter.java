package com.example.simpleapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simpleapp.R;
import com.example.simpleapp.model.Photo;
import com.example.simpleapp.utils.AlbumsHelper;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<Integer> data;
    private List<Photo> photos;
    private Context context;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public AlbumsAdapter(Context context, List<Integer> albums, List<Photo> photos, RecyclerView recyclerView) {
        this.data = albums;
        this.photos = photos;
        this.context = context;
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    //check if are items to loading
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            //use loading new items
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        if (i == VIEW_ITEM) {
            //Normal item case
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.album_item, viewGroup, false);
            viewHolder = new AlbumViewHolder(view);
        }else{
            //progress spinner item case
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progressbar, viewGroup, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof AlbumViewHolder) {
            String albumId = String.valueOf(data.get(i));
            //configuration of nested photos recyclerview and adapter
            ((AlbumViewHolder) viewHolder).idTextview.setText("Album: " + albumId);
            ((AlbumViewHolder) viewHolder).photosRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            ((AlbumViewHolder) viewHolder).photosRecyclerView.setLayoutManager(linearLayoutManager);
            PhotosAdapter photosAdapter = new PhotosAdapter(Glide.with(context), AlbumsHelper.getPhotosForAlbumId(data.get(i), photos));
            ((AlbumViewHolder) viewHolder).photosRecyclerView.setAdapter(photosAdapter);
        } else {
            //configuration of progress spinner
            ((ProgressViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded(){
        loading = false;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        public TextView idTextview;
        public RecyclerView photosRecyclerView;

        public AlbumViewHolder(View view) {
            super(view);
            idTextview = view.findViewById(R.id.albumIdTextView);
            photosRecyclerView = view.findViewById(R.id.photosRecyclerView);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }
}
