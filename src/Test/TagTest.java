import Components.ImageAnalyzer;
import Components.UserInfo;
import Database.HibernateClasses.Photo;
import Database.HibernateClasses.Tags;
import Database.HibernateClasses.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to add and remove a tag from a photo.
 */
class TagTest {
    private static User user;
    private static Photo photo;
    private static Tags golfTag;

    @BeforeAll
    static void setup() {
        try {
            user = new User();
            user.setUsername("testPhotoUser");
            user.setId(10);
            UserInfo.initializeUser(user);

            photo = ImageAnalyzer.analyze("TestPicture", "file:src/Test/Assets/test_image.jpg");
            golfTag = new Tags("golf", photo.getId());
            golfTag.setId(1000);
            photo.getTags().add(golfTag);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a tag to a photo and check if the tag has the correct photo id.
     */
    @Test
    void getId_TagValidPhotoId_isEqual() {
        Tags tag = new Tags("sport", photo.getId());
        tag.setId(23);
        photo.getTags().add(tag);
        assertEquals(tag.getPhotoId(), photo.getId());
    }

    /**
     * Trying to add a new tag to the photo, but the tag has the incorrect photoId.
     */
    @Test
    void add_NewTagIncorrectPhotoId_isNotEqual() {
        Tags newTag = new Tags("football", 32213);
        newTag.setId(21);
        photo.getTags().add(newTag);
        assertNotEquals(newTag.getPhotoId(), photo.getId());
    }

    /**
     * An IndexOutOfBoundsException is thrown when trying to get
     * a photo tag that does not exist.
     */
    @Test
    void getTags_PhotoHasNoTags_IndexOutOfBoundsExceptionThrown() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            photo.getTags().clear();
            photo.getTags().get(0).getTag();
        });
    }

    /**
     * Test to check if there exists multiple tags with the same id.
     */
    @Test
    void getId_TagIdIsUnique_OneWithSame() {
        List<Tags> findTagId = photo.getTags()
                                    .stream()
                                    .filter(tag -> tag.getId() == golfTag.getId())
                                    .collect(Collectors.toList());
        assert findTagId.size() <= 1 : "Can only add one tag with the same id";
    }
}
