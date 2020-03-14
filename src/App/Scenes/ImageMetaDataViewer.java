package Scenes;


import Components.Photo;
import Components.TagContainer;
import Css.Css;
import Database.DBConnection;
import java.sql.SQLException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ImageMetaDataViewer {
  //The flowPane containing the tag containers
  private static FlowPane tagContainer = new FlowPane();

  private static Photo photo;

  private static Stage stage;

  /**
   * Private constructor to hinder the creation of the utility class
   */
  private ImageMetaDataViewer() {
    throw new IllegalStateException("Utility class");
  }

  public static Stage getStage() {
    return stage;
  }

  public static FlowPane getTagContainer() {
    return tagContainer;
  }

  public static void display(int photo_id) throws SQLException {
    photo = DBConnection.getPhoto(photo_id);
    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(StageInitializer.getStage());

    stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (!isNowFocused && AddTag.status == false) {
        stage.hide();
      }
    });

    stage.setTitle("Image metadata");
    stage.setMinWidth(250.0);


    BorderPane layout = new BorderPane();

    VBox imageInfoContainer = new VBox();
    Label photoTitleLabel = new Label();

    Css.setTitleLabel(photoTitleLabel);
    Label metadataLabel = new Label();
    Css.setParagraphLabel(metadataLabel);
    Label errorInLoadingImage = new Label("Image could not be loaded");
    Css.setErrorLabel(errorInLoadingImage);


    Scene scene;
    try {

      photoTitleLabel.setText(photo.getTitle());
      metadataLabel.setText(photo.toString() + "\nTags:\n");


      imageInfoContainer.getChildren().addAll(photoTitleLabel, metadataLabel, tagContainer);

      //Stream that runs through all the photo's tags
      photo.getTags().forEach(s -> {
        //Creates a new tag container object
        TagContainer tagContainerObject = new TagContainer(s);
        //Adds the tag container object to the flowpane tagContainer
        tagContainer.getChildren().add(tagContainerObject.getContainer());
        setButtonFunctionality(tagContainerObject);
      });


      ImageView imageView = new ImageView(new Image(photo.getPath(), 200, 200, true, true));

      layout.setLeft(imageInfoContainer);
      layout.setRight(imageView);


      scene = new Scene(layout, 800, 600);
    } catch (NullPointerException e) {
      //TODO add logger here
      layout.getChildren().add(errorInLoadingImage);
      scene = new Scene(layout);
    }

    Button addTag = new Button("Add tag");
    addTag.setOnAction(e -> AddTag.display(photo));

    Button closeButton = new Button("Close stage");
    Css.setButtonsSignUpLogin(addTag, closeButton);
    closeButton.setOnAction(e -> stage.close());
    HBox buttons = new HBox();
    buttons.getChildren().addAll(addTag, closeButton);
    layout.setBottom(buttons);

    stage.setScene(scene);
    stage.showAndWait();
  }

  static void setButtonFunctionality(TagContainer tagContainerObject) {
    //Programs the delete button each tag to remove the tag
    tagContainerObject.getDeleteTag().setOnAction(e -> {
      photo.getTags().removeIf(t -> t.equals(tagContainerObject.getTagAsString()));
      tagContainer.getChildren().removeIf(t -> t.equals(tagContainerObject.getContainer()));
    });
  }
}
