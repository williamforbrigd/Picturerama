package Components;

import Database.HibernateClasses.Photo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Class that is used to create a photo album in the form of a pdf file
 */
public class PDFcreator {

    /**
     * Private constructor to hinder creation of utility class
     */
    private PDFcreator() {
        throw new IllegalStateException("Can not make instance of utility class");
    }

    /**
     * Creates a pdf from a list of photos and saves them where the user wants them to be saved
     * @param photos Is the list of photos
     * @param saveLocation Is where the user wants the pdf saved
     * @throws DocumentException Is thrown is a document can't be made or created
     * @throws IOException Is thrown if there is a mistake in the save location url and or the image url is wrong
     */
    public static void createPDF(List<Photo> photos, String saveLocation) throws DocumentException, IOException{
        // Opens a new document and makes it a pdf
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(saveLocation));
        document.open();
        PdfContentByte content = writer.getDirectContent();
        int row = 0;
        int photoCounter = 0;
        int xPos;
        int yPos;
        for (Photo photo : photos) {
            xPos = 20;
            Image img = Image.getInstance(photo.getUrl());
            // Sets up photos side by side
            if (photoCounter == 1 || photoCounter == 3 || photoCounter == 5) {
                xPos = xPos + 320;
            }
            // Switches row
            if (photoCounter == 2 || photoCounter == 4) {
                row++;
            }
            // Makes sure that a new page is created if 6 images are added to a page
            if (photoCounter == 6) {
                document.newPage();
                row = 0;
                photoCounter = 0;
            }
            // Begins writing text for the image titles
            content.beginText();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);
            content.setFontAndSize(bf, 14);
            // If sentence that changes position of the image according to the image size
            if (img.getWidth() > img.getHeight()) {
                yPos = 600 - (row * 270);
                content.showTextAligned(1, photo.getTitle(), (float)xPos + 80, (float)yPos - 20, 0);
                content.endText();
                img.setAbsolutePosition(xPos, yPos);
                img.scaleToFit(200, 400);
                content.addImage(img);
            } else {
                yPos = 600 - (row * 270);
                content.showTextAligned(1, photo.getTitle(), (float)xPos + 80, (float)yPos - 20, 0);
                content.endText();
                img.setAbsolutePosition(xPos, yPos);
                img.scaleToFit(400, 200);
                content.addImage(img);
            }
            photoCounter++;
        }
        document.close();
    }
}
