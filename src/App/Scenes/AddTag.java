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
  boolean isVisible = false;
  private ImageMetaDataViewer owner;
  private Photo photo;
  private Stage stage;
  /**
   * Private constructor to hinder the creation of the utility class
   */
  public AddTag(ImageMetaDataViewer owner, Photo photo) {
      this.stage = setupStage();
      this.owner = owner;
      this.photo = photo;
  }

  public Stage setupStage(){
    //Stage to be returned
    //Easier to test when doing this
    Stage _stage = new Stage();
    _stage.initModality(Modality.APPLICATION_MODAL);
    _stage.initOwner(owner.getStage());
    _stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (!isNowFocused) {
         isVisible = false;
        _stage.hide();
      }
    });
    _stage.setTitle("Add tag");
    _stage.setMinWidth(150);

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
        owner.getTagContainer().getChildren().add(tagContainerObject.getContainer());
        // TODO: Create tag in database related to photo
        owner.setButtonFunctionality(tagContainerObject);
        isVisible = false;
        _stage.close();
      }
    });
    layout.getChildren().addAll(tagLabel, tagField, addTag, feedbackLabel);
    _stage.setScene(new Scene(layout, 300, 300));
    return _stage;
  }

  public void display(){
    isVisible = true;
    stage.showAndWait();
  }
}
