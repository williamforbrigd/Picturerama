package Components;

import Css.Css;
import Database.HibernateClasses.Photo;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Class that is used to display photos in the search scene
 */
public class PhotoContainer {

    private Photo photo;
    private Image image;
    private ImageView imageView;
    private CheckBox checkBox;
    private Button photoButton;
    private HBox photoContainer;

    /**
     * Constructor that takes a photo object and initializes the photocontainer with that photo in it
     *
     * @param photo a photo object
     */
    public PhotoContainer(Photo photo) {
        this.photo = photo;
        setupPhotoContainer(photo);
    }

    public Photo getPhoto() {
        return photo;
    }

    public Image getImage() {
        return image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public Button getPhotoButton() {
        return photoButton;
    }

    public HBox getPhotoContainer() {
        return photoContainer;
    }

    /**
     * Makes a photo container that is a button that contains a photo and a checkbox
     *
     * @param photo a photo object
     */
    private void setupPhotoContainer(Photo photo) {
        image = new Image(photo.getUrl(), 150, 150, true, true, true);
        imageView = new ImageView(image);

        photoButton = new Button(photo.getTitle(), imageView);
        photoButton.setOnAction(action -> {
            PhotoViewer photoViewer = new PhotoViewer(photo);
            photoViewer.display();
        });
        setupContainerCheckBox();
        photoContainer = new HBox(photoButton, checkBox);
        photoContainer.getStylesheets().add("file:src/main/App/Css/CheckBoxStyle.css");
        photoContainer.setSpacing(10);
        photoContainer.setAlignment(Pos.CENTER_LEFT);
        Css.setImageContainer(photoButton, photoContainer);
    }

    /**
     * Creates a checkbox and adds the correct styling to it
     */
    private void setupContainerCheckBox() {
        checkBox = new CheckBox();
        checkBox.getStyleClass().add("check-box");
    }
}
