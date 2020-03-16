package Database.HibernateClasses;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TAGS")
public class Tags implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private int id;

    @Column(name = "tag")
    private String tag;

    @Column(name = "photo_id")
    private int photoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
