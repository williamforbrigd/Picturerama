import Components.UserInfo;
import Database.HibernateClasses.Album;
import Database.HibernateClasses.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to check if a user can upload an delete an album.
 */
class AlbumTest {
    static User user;
    static Album album;

    @BeforeAll
    static void setup(){
        user = new User();
        user.setUsername("testAlbumUser");
        user.setId(10);
        UserInfo.initializeUser(user);

        album = new Album();
        album.setName("Album 1");
        album.setUserId(user.getId());
        album.setId(100);
        user.getAlbums().add(album);
    }

    /**
     * Checks whether the album has the correct user id.
     */
    @Test
    void getID_AlbumCorrectID_isEqual() {
        assertEquals(album.getUserId(), user.getId());
    }

    @Test
    void getAlbums_AddNewAlbum_True() {
        assertTrue(user.getAlbums().add(new Album("Album 2", user.getId())));
    }

    /**
     * Test to check whether the user has multiple albums with the same id.
     */
    @Test
    void getAlbumId_AlbumIdIsUnique_OneWithSame() {
        List<Album> findAlbumId = user.getAlbums().stream()
                .filter(a -> a.getId() == album.getId())
                .collect(Collectors.toList());
        assert findAlbumId.size() <= 1 : "Only one album can have the same id";
    }
}
