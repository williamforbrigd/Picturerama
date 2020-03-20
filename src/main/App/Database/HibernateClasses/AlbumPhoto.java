package Database.HibernateClasses;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Hibernate database class for the table ALBUMPHOTO
 */
@Entity
@Table(name = "ALBUMPHOTO")
public class AlbumPhoto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private int id;

    @Column(name = "album_id")
    private int albumId;

    @Column(name = "photo_id")
    private int photoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
