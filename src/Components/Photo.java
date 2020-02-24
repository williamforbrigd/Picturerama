package Components;

import java.sql.Blob;

public class Photo {
    private int id;
    private String title;
    private String path;
    private Blob image;
    private String tags;
    private String latitude;
    private String longitude;
    private int width;
    private int height;
    private String fileType;
    private double aperture;
    private double exposureTime;
    private int size;
    private String camera;
    private String registered;
    private int userId;

    public Photo(int id, String title, String path, Blob image, String tags, String latitude, String longitude, int width, int height, String fileType, double aperture, double exposureTime, int size, String camera, String registered, int userId) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.image = image;
        this.tags = tags;
        this.latitude = latitude;
        this.longitude = longitude;
        this.width = width;
        this.height = height;
        this.fileType = fileType;
        this.aperture = aperture;
        this.exposureTime = exposureTime;
        this.size = size;
        this.camera = camera;
        this.registered = registered;
        this.userId = userId;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPath() {
        return this.path;
    }

    public Blob getImage() {
        return this.image;
    }

    public String getTags() {
        return this.tags;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getFileType() {
        return this.fileType;
    }

    public double getAperture() {
        return this.aperture;
    }

    public double getExposureTime() {
        return this.exposureTime;
    }

    public int getSize() {
        return this.size;
    }

    public String getCamera() {
        return this.camera;
    }

    public String getRegistered() {
        return this.registered;
    }

    public int getUserId() {
        return this.userId;
    }

}
