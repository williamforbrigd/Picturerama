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
        //opens a new document and maks it a pdf
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(saveLocation));
        document.open();
        PdfContentByte content = writer.getDirectContent();
        int row = 0;
        int photoCounter = 0;
        int x_pos = 20;
        int y_pos;
        for (int i = 0; i < photos.size(); i++) {
            Image img = Image.getInstance(photos.get(i).getUrl());
            if (row == 3) {
                x_pos = x_pos + 300;
                row = 0;
            }
            //makes sure that a new page is created if 6 images are added to a page
            if (photoCounter == 6) {
                document.newPage();
                row = 0;
                photoCounter = 0;
                x_pos= 20;
            }
            //begins writing text for the image titles
            content.beginText();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);
            content.setFontAndSize(bf, 14);
            //if sentence that changes position of the image according to the image size
            if (img.getWidth() > img.getHeight()) {
                y_pos = 600 - (row * 250);
                content.showTextAligned(1, "center", x_pos + 20, y_pos + 130, 0);
                content.endText();
                img.setAbsolutePosition(x_pos, y_pos);
                img.scaleToFit(250, 500);
                content.addImage(img);
            } else {
                y_pos = 525 - (row * 250);
                content.showTextAligned(1, "center", x_pos + 20, y_pos + 255, 0);
                content.endText();
                img.setAbsolutePosition(x_pos, y_pos);
                img.scaleToFit(500, 250);
                content.addImage(img);
            }
            row++;
            photoCounter++;
        }
        document.close();
    }
}
