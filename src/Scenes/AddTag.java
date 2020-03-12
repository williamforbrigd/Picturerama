package Scenes;

import Components.Photo;
import Components.TagContainer;
import Css.Css;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddTag{

  //Shows if the window is open
  static boolean status = false;

  /**
   * Private constructor to hinder the creation of the utility class
   */
  private AddTag() {
    throw new IllegalStateException("Utility class");
  }

  public static void display(Photo photo){
    status = true;
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(ImageMetaDataViewer.getStage());
    stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (!isNowFocused) {
        status = false;
        stage.hide();
      }
    });
    stage.setTitle("Add tag");
    stage.setMinWidth(150);

    VBox layout = new VBox();

    Label tagLabel = new Label("Add a tag to picture: " + photo.getTitle());
    TextField tagField = new TextField();
    tagField.setPromptText("Tag name");

    Label feedbackLabel = new Label();
    Css.setErrorLabel(feedbackLabel);

    Button addTag = new Button("Add tag");
    addTag.setOnAction(e -> {
      if(tagField.getText() == null || tagField.getText().trim().equals("")) feedbackLabel.setText("Error: the tag has to have a name");
      else if(photo.getTags().contains(tagField.getText())) feedbackLabel.setText("Tag already in registered");
      else {
        photo.getTags().add(tagField.getText());
        TagContainer tagContainerObject = new TagContainer(tagField.getText());
        ImageMetaDataViewer.getTagContainer().getChildren().add(tagContainerObject.getContainer());
        // TODO: Create tag in database related to photo
        ImageMetaDataViewer.setButtonFunctionality(tagContainerObject);
        status = false;
        stage.close();
      }
    });
    layout.getChildren().addAll(tagLabel, tagField, addTag, feedbackLabel);
    stage.setScene(new Scene(layout, 300, 300));
    stage.showAndWait();
  }
}
