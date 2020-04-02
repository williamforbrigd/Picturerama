package Components;

import Database.HibernateClasses.Photo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that is used to create a photo album in the form of a pdf file
 */
public class PDFcreator {
    /**
     * Creates a pdf from a list of photos and saves them where the user wants them to be saved
     * @param photos Is the list of photos
     * @param saveLocation Is where the user wants the pdf saved
     * @throws DocumentException Is thrown is a document can't be made or created
     * @throws IOException Is thrown if there is a mistake in the savelocation url and or the image url is wrong
     */
    public static void createPDF(ArrayList<Photo> photos, String saveLocation) throws DocumentException, IOException{
        // Opens a new document and maks it a pdf
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(saveLocation));
        document.open();
        PdfContentByte content = writer.getDirectContent();
        int row = 0;
        int photoCounter = 0;
        int x_pos;
        int y_pos;
        for (int i = 0; i < photos.size(); i++) {
            Photo photo = photos.get(i);
            x_pos = 20;
            Image img = Image.getInstance(photo.getUrl());
            // Sets up photos side by side
            if(photoCounter == 1 || photoCounter == 3 || photoCounter == 5){
                x_pos = x_pos + 320;
            }
            // Switches row
            if(photoCounter == 2 || photoCounter == 4){
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
                y_pos = 600 - (row * 270);
                content.showTextAligned(1, photo.getTitle() , x_pos + 80, y_pos - 20 , 0);
                content.endText();
                img.setAbsolutePosition(x_pos, y_pos);
                img.scaleToFit(200, 400);
                content.addImage(img);
            } else {
                y_pos = 600 - (row * 270);
                content.showTextAligned(1, photo.getTitle(), x_pos + 80, y_pos - 20, 0);
                content.endText();
                img.setAbsolutePosition(x_pos, y_pos);
                img.scaleToFit(400, 200);
                content.addImage(img);
            }
            photoCounter++;
        }
        document.close();
    }
}
