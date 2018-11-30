package com.example.simpleapp.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.simpleapp.R;
import com.example.simpleapp.adapter.AlbumsAdapter;
import com.example.simpleapp.adapter.OnLoadMoreListener;
import com.example.simpleapp.model.Photo;
import com.example.simpleapp.network.APIRequest;
import com.example.simpleapp.utils.AlbumsHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView albumsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private AlbumsAdapter albumsAdapter;
    private Handler handler;
    private List<Integer> albumsIdies;
    private List<Integer> visibleAlbumsIdies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albumsRecyclerView = findViewById(R.id.albumsRecyclerView);
        albumsIdies = new ArrayList<>();
        visibleAlbumsIdies = new ArrayList<>();
        handler = new Handler();

        //JSON download request
        APIRequest apiRequest = new APIRequest();
        apiRequest.getPhotos(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                //casting response
                final List<Photo> photos = (List<Photo>) response.body();
                //getting list of idies of all albums
                albumsIdies = AlbumsHelper.getAlbumsForPhotosList(photos);

                loadInitialData();
                //configuration of albums recyclerview and adapter
                albumsRecyclerView.setHasFixedSize(true);
                linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                albumsRecyclerView.setLayoutManager(linearLayoutManager);
                albumsAdapter = new AlbumsAdapter(MainActivity.this, visibleAlbumsIdies, photos, albumsRecyclerView);
                albumsRecyclerView.setAdapter(albumsAdapter);
                albumsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        //adding null item to show progress spinner
                        visibleAlbumsIdies.add(null);
                        albumsAdapter.notifyItemInserted(visibleAlbumsIdies.size() - 1);
                        //handler to load new items
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //removing progress and inserting new loaded items
                                visibleAlbumsIdies.remove(visibleAlbumsIdies.size() - 1);
                                albumsAdapter.notifyItemRemoved(visibleAlbumsIdies.size());
                                int start = visibleAlbumsIdies.size();
                                int end = start + 2;
                                for(int i = start; i <= end; i++) {
                                    if(i < albumsIdies.size()) {
                                        visibleAlbumsIdies.add(albumsIdies.get(i));
                                        albumsAdapter.notifyItemInserted(visibleAlbumsIdies.size());
                                    }
                                }
                                //setting loaded flag of album adapter
                                albumsAdapter.setLoaded();
                            }
                        }, 2000);
                    }
                });
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                //Error while downloading JSON
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //getting first 2 albums for present to user
    private void loadInitialData(){
        for(int i = 0; i<= 2; i++){
            if(i < albumsIdies.size()) {
                visibleAlbumsIdies.add(albumsIdies.get(i));
            }
        }
    }
}
