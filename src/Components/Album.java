package Components;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Album {

    private String albumName;
    private int albumId;
    private ArrayList<Photo> photos;

    public Album(int albumId,String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
        photos = new ArrayList<>();
    }


    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String newAlbumName) {
        this.albumName = newAlbumName;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
