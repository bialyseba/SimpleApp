package com.example.simpleapp.utils;

import com.example.simpleapp.model.Photo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlbumsHelper {
    //getting albums idies for photos list from api
    public static List<Integer> getAlbumsForPhotosList(List<Photo> photos){
        Set<Integer> albumsIdies = new HashSet<>();
        for(final Photo photo: photos) {
            albumsIdies.add(photo.getAlbumId());
        }

        List<Integer> idiesList = new ArrayList<>();
        idiesList.addAll(albumsIdies);
        return idiesList;
    }

    //getting photos list for specified album id
    public static List<Photo> getPhotosForAlbumId(int id, List<Photo> photos) {
        List<Photo> photosForId = new ArrayList<>();
        for(Photo p : photos) {
            if(p.getAlbumId() == id) {
                photosForId.add(p);
            }
        }
        return photosForId;
    }
}
