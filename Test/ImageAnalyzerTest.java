import Components.ImageAnalyzer;
import Components.UserInfo;
import Database.HibernateClasses.Photo;
import Database.HibernateClasses.User;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for testing of
 * ImageAnalyzer class
 */
class ImageAnalyzerTest {

  /**
   * Test method for analyze-method when everything is executed as expected.
   * Firstly, a user is initialized. Then we create a Photo object by calling the
   * analyze-method we want to test. The method then checks if all metadata of the
   * photo object is not equals null or 0. The test executes successfully.
   */
  @Test
  void analyze_TitleAndUrlIsGiven_AllMetadataTestedSuccessfully() {
    try {
      User user = new User();
      user.setUsername("_Test_");
      user.setId(32);
      UserInfo.initializeUser(user);
      Photo photo = ImageAnalyzer.analyze("TestPicture", "https://www.dropbox.com/s/vz90348372vevwt/IMG_20191018_101819.jpg?dl=1");
      assertNotNull(photo); //Checking if the object != null
      assertTrue(photo instanceof Photo);
      assertEquals("TestPicture", photo.getTitle());
      assertEquals("https://www.dropbox.com/s/vz90348372vevwt/IMG_20191018_101819.jpg?dl=1", photo.getUrl());
      assertNotNull(photo.getTime());
      assertNotNull(photo.getCamera());
      assertNotNull(photo.getExposureTime());
      assertNotNull(photo.getAperture());
      assertNotEquals(0, photo.getHeight(), 0);
      assertNotEquals(0, photo.getWidth(), 0);
      assertNotEquals(0, photo.getFileSize(), 0);
      assertNotNull(photo.getFileType());
      assertEquals(user.getId(), photo.getUserId());
      System.out.println("Tested all metadata successfully.");
    } catch (Exception e) {
      fail("Caught an Exception, wanted an expression..." + e);
    }
  }

  /**
   * Tests that analyze-method throws IOException
   * when we pass null and null as arguments.
   */
  @Test
  void analyze_TitleAndUrlIsNull_IOExceptionThrown() {
    assertThrows(IOException.class, () -> {
      ImageAnalyzer.analyze(null, null);
    });
  }
}
