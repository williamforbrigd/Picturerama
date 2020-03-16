package Components;

import Database.HibernateClasses.Photo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageAnalyzer {
    public static Photo analyze(String title, String url) throws IOException, NullPointerException {
        URL path = new URL(url);
        URLConnection conn = path.openConnection();
        InputStream in = conn.getInputStream();
        javaxt.io.Image image = new javaxt.io.Image(in);
        java.util.HashMap<Integer, Object> exif = image.getExifTags();
        try {
	        double[] coord = image.getGPSCoordinate();
	        Photo photo = new Photo();
	        photo.setTitle(title);
	        photo.setUrl(url);
	        photo.setTime((String) exif.get(0x0132));
	        photo.setCamera((String) exif.get(0x0110));
	        photo.setExposureTime((String) exif.get(0x829A));
	        photo.setAperture((String) exif.get(0x9202));
	        if (coord != null) {
		        photo.setLatitude(coord[0]);
		        photo.setLongitude(coord[1]);
	        }
	        photo.setHeight(image.getHeight());
	        photo.setWidth(image.getWidth());
	        photo.setFileSize(conn.getContentLength());
	        photo.setFileType(conn.getContentType());
	        photo.setUserId(UserInfo.getUser().getId());
	        return photo;
        } catch (NullPointerException e) {
        	throw e;
        }
    }
}
