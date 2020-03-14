package Components;

import Css.Css;
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

        photoButton = new Button("Title: " + photo.getTitle() +  "\nWidth: " + photo.getWidth() + "\nHeight: " + photo.getHeight() + "\nFile Type: " + photo.getFileType()
                + "\nPath: " + photo.getPath(), imageBox);
        Css.setImageContainerButtons(photoButton);

        photoButton.setOnAction(action -> {
            Scene pictureScene = new Scene(new HBox(new ImageView(new Image(photo.getPath(), 150, 150, true, true))));
            Stage pictureStage = new Stage();
            pictureStage.setScene(pictureScene);
            pictureStage.initModality(Modality.APPLICATION_MODAL);
            pictureStage.show();
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
