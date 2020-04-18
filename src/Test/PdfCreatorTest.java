import Components.PdfCreator;
import Components.UserInfo;
import Database.HibernateClasses.Photo;
import Database.HibernateClasses.User;
import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PdfCreatorTest {

	private static final String PATH_TO_PDF = "src/Test/Assets/generatedAlbumPdf.pdf";
	private static List<Photo> photoList = new ArrayList<>();

	@BeforeAll
	static void setup() {
		User user = new User();
		user.setUsername("testPhotoUser");
		user.setId(10);
		UserInfo.initializeUser(user);

		Photo photo = new Photo();
		photo.setUrl("file:src/Test/Assets/test_image.jpg");
		photo.setTitle("Test image");
		photo.setWidth(4000);
		photo.setHeight(3000);
		photoList.add(photo);
	}

	/**
	 * Delete the generated pdf after all tests have been executed.
	 */
	@AfterAll
	static void tearDown() {
		File file = new File(PATH_TO_PDF);
		file.delete();
	}

	@Test
	void createPdf_ShouldWork_GeneratedFileNotNull() throws DocumentException, IOException {
		PdfCreator.createPdf(photoList, PATH_TO_PDF, "Test");
		File pdfFile = new File(PATH_TO_PDF);

		assertNotNull(pdfFile);
	}
}