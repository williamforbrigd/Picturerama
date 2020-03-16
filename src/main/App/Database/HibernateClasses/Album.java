package Database.HibernateClasses;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ALBUMS")
public class Album implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private int id;

    @Column(name ="name")
    private String name;

    @Column(name = "user_id")
    private int userId;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private List<AlbumPhoto> albumPhotos = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<AlbumPhoto> getAlbumPhotos() {
        return albumPhotos;
    }

    public void setAlbumPhotos(List<AlbumPhoto> albumPhotos) {
        this.albumPhotos = albumPhotos;
    }
}
