package Components;

import java.io.File;
import java.util.HashMap;

public class ImageAnalyzer {
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    public static HashMap<String, String> analyze(String url){
        javaxt.io.Image image = new javaxt.io.Image(url);
        java.util.HashMap<Integer, Object> exif = image.getExifTags();
        File file = new File(url);
        double[] coord = image.getGPSCoordinate();
        HashMap<String, String> analyzedPhoto = new HashMap<String, String>();
        analyzedPhoto.put("registered", exif.get(0x0132).toString());
        analyzedPhoto.put("camera",exif.get(0x0110).toString());
        analyzedPhoto.put("exposureTime",exif.get(0x829A).toString());
        analyzedPhoto.put("aperture",exif.get(0x9202).toString());
        analyzedPhoto.put("latitude",String.valueOf(coord[0]));
        analyzedPhoto.put("longitude",String.valueOf(coord[1]));
        analyzedPhoto.put("height",String.valueOf(image.getHeight()));
        analyzedPhoto.put("width",String.valueOf(image.getWidth()));
        analyzedPhoto.put("size",String.valueOf(file.length()/1024));
        analyzedPhoto.put("filetype",getFileExtension(file));
        return analyzedPhoto;
    }
}
