package Components;

import Css.Css;
import Scenes.ImageMetaDataViewer;
import java.sql.SQLException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PhotoContainer {

    private Photo photo;
    private Image image;
    private ImageView imageView;
    private VBox imageBox;
    private CheckBox checkBox;
    private Button photoButton;
    private HBox photoContainer;

    public PhotoContainer(Photo photo){
        this.photo = photo;
        setupPhotoContainer(photo);
    }

    public Photo getPhoto(){
        return photo;
    }

    public Image getImage(){
        return image;
    }

    public ImageView getImageView(){
        return imageView;
    }

    public VBox getImageBox(){
        return imageBox;
    }

    public CheckBox getCheckBox(){
        return checkBox;
    }

    public Button getPhotoButton(){
        return photoButton;
    }

    public HBox getPhotoContainer() {
        return photoContainer;
    }

    private void setupPhotoContainer(Photo photo){
        image = new Image(photo.getPath(), 150, 150, true, true);
        imageView = new ImageView(image);
        imageBox = new VBox(imageView);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setStyle("-fx-background-color: #F1F1F1;");
        imageBox.setMinSize(155, 155);

        photoButton = new Button(photo.getTitle(), imageBox);
        Css.setImageContainerButtons(photoButton);

        photoButton.setOnAction(action -> {
            ImageMetaDataViewer imageMetaDataViewer = new ImageMetaDataViewer(photo.getId());
            imageMetaDataViewer.display();
        });
        setupContainerCheckBox();
        photoContainer = new HBox(photoButton, checkBox);
        photoContainer.getStylesheets().add("file:src/App/Css/CheckBoxStyle.css");
        photoContainer.setSpacing(10);
        photoContainer.setAlignment(Pos.CENTER_LEFT);
        photoContainer.setStyle("-fx-border-radius: 1; -fx-border-color: #616161");
    }

    private void setupContainerCheckBox(){
        checkBox = new CheckBox();
        checkBox.getStyleClass().add("check-box");
    }
}
