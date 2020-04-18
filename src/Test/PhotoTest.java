import Components.ImageAnalyzer;
import Components.UserInfo;
import Database.HibernateClasses.Photo;
import Database.HibernateClasses.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to upload and delete a photo.
 */
class PhotoTest {
    private static Photo photo;
    private static User user;

    @BeforeAll
    static void setup() {
        try {
            user = new User();
            user.setUsername("testPhotoUser");
            user.setId(10);
            UserInfo.initializeUser(user);

            photo = ImageAnalyzer.analyze("TestPicture", "file:src/Test/Assets/test_image.jpg");
            photo.setId(232);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test to upload a photo.
     */
    @Test
    void add_NewPhoto_True() {
        assertTrue(user.getPhotos().add(photo));
    }

    /**
     * An IndexOutOfBoundsException is expected to be thrown when adding a photo to a non existing album.
     */
    @Test
    void add_PhotoToNonExistingAlbum_IndexOutOfBoundsExceptionThrown() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            user.getAlbums().get(0).getPhotos().add(photo);
        });
    }

    /**
     * Test to check whether the photo was uploaded to the correct user.
     */
    @Test
    void getUserId_CorrectPhotoUserID_isEqual() {
        assertEquals(photo.getUserId(), user.getId());
    }

    /**
     * Test to check whether the user has multiple photos with the same id.
     * The id is unique and the user should not be able to register
     * multiple photos with the same id.
     */
    @Test
    void getId_MaxOnePhotoSameId_OneWithSame() {
        List<Photo> findPhotoId = user.getPhotos().stream()
                .filter(p -> p.getId() == photo.getId())
                .collect(Collectors.toList());
        assert findPhotoId.size() <= 1 : "Only one photo can have the same id";
    }
}
