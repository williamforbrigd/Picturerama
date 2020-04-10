package Components;

import Components.TagContainer;
import Components.UserInfo;
import Css.Css;
import Css.FeedBackType;
import Database.Hibernate;
import Database.HibernateClasses.Photo;
import Database.HibernateClasses.Tags;
import Scenes.StageInitializer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class that is used to display photo metadata
 */
public class PhotoViewer {
  //The flowPane containing the tag containers
  private FlowPane tagContainer = new FlowPane();
  private Photo photo;
  private Stage stage;

  public PhotoViewer(Photo photo) {
    if (photo == null) throw new NullPointerException();
    this.photo = photo;
    this.stage = setup();
  }

  /**
   * Setup creates a new stage then sets it up
   *
   * @return stage, a new stage
   */
  private Stage setup() {
    // Making new stage here to make unit testing easier, since the method will return a result
    Stage _stage = new Stage();
    _stage.initModality(Modality.APPLICATION_MODAL);
    _stage.initOwner(StageInitializer.getStage());
    _stage.setTitle(photo.getTitle());

    Label photoTitleLabel = new Label();
    photoTitleLabel.setText(photo.getTitle());
    Css.setLabel(30,photoTitleLabel);
    Label metadataLabel = new Label();
    metadataLabel.setText(photo.toString());
    Css.setLabel(12, metadataLabel);
    Label tagLabel = new Label("Tags:");
    tagLabel.setPadding(new Insets(10,0,0,0));
    Label addTagLabel = new Label("Add a tag to picture:");
    Css.setLabel(15, tagLabel, addTagLabel);
    Label feedbackLabel = new Label();
    Css.setFeedBackLabel(FeedBackType.Error, 13, feedbackLabel);

    TextField tagField = new TextField();
    tagField.setPromptText("Tag name");
    Css.setTextField(150,10,14, tagField);

    Button addTagButton = new Button("Add tag");
    Button closeButton = new Button("Close");
    Css.setButton(582,25,20, addTagButton, closeButton);

    AnchorPane layout = new AnchorPane();
    VBox bottomContainer = new VBox();
    bottomContainer.setSpacing(10);
    bottomContainer.setPadding(new Insets(10, 10, 10, 10));
    bottomContainer.setSpacing(6);
    bottomContainer.getChildren().addAll(tagLabel, tagContainer, addTagLabel, tagField, feedbackLabel, addTagButton, closeButton);
    AnchorPane.setBottomAnchor(bottomContainer, 5.0);
    layout.getChildren().add(bottomContainer);

    VBox imageInfoContainer = new VBox();
    imageInfoContainer.getChildren().addAll(photoTitleLabel, metadataLabel);
    imageInfoContainer.maxWidth(275);

    tagContainer.setMaxWidth(580);
    tagContainer.setPrefWrapLength(580);
    tagContainer.setPadding(new Insets(0, 10, 10, 0));
    tagContainer.setHgap(4);
    tagContainer.setVgap(4);

    photo.getTags().forEach(t -> {
      //Creates a new tag container object and adds it to the tagContainer flowpane
      TagContainer tagContainerObject = new TagContainer(t.getTag());
      tagContainer.getChildren().add(tagContainerObject.getContainer());
      setButtonFunctionality(tagContainerObject);
    });

    //Sets the functionality of the add tag button
    addTagButton.setOnAction(e -> {
      Tags tag = new Tags(tagField.getText(), photo.getId());
      //Checks if there is an input in the textfield
      if (tagField.getText() == null || tagField.getText().trim().equals("")) {
        feedbackLabel.setText("Error: The tag must have a name");
      }
      //Checks if the photo already has the tag
      else if (photo.getTags().contains(tag)) {
        feedbackLabel.setText("Error: This tag is already registered");
      } else {
        tag.setPhotoId(photo.getId());
        photo.getTags().add(tag);

        //Creates new tag container and gives it functionality
        TagContainer tagContainerObject = new TagContainer(tagField.getText());
        tagContainer.getChildren().add(tagContainerObject.getContainer());
        this.setButtonFunctionality(tagContainerObject);

        feedbackLabel.setText("");
        tagField.clear();
      }
    });

    //All closing mechanisms
    _stage.setOnCloseRequest(e -> {
      e.consume();
      updateDatabaseAndClose();
    });
    //Closes stage if it is not the focus, and updates the database
    _stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (!isNowFocused) {
        updateDatabaseAndClose();
      }
    });
    closeButton.setOnAction(e -> updateDatabaseAndClose());

    ImageView imageView = new ImageView(new Image(photo.getUrl(), 255, 255, true, true, true));

    //Adding child nodes to parent nodes
    AnchorPane.setLeftAnchor(imageInfoContainer, 10.0);
    AnchorPane.setRightAnchor(imageView, 10.0);
    layout.getChildren().addAll(imageInfoContainer, imageView);
    layout.setPadding(new Insets(30, 10, 10, 10));
    layout.setStyle("-fx-padding: 10 0 0 0");

    Scene scene = new Scene(layout, 600, 560);
    _stage.setScene(scene);
    return _stage;
  }

  /**
   * Sets the up the buttons of a tag container
   * @param tagContainerObject is the tag container that is being setup
   */
  private void setButtonFunctionality(TagContainer tagContainerObject) {
    //Programs the delete button each tag to remove the tag
    tagContainerObject.getDeleteTag().setOnAction(e -> {
      photo.getTags().removeIf(t -> t.getTag().equals(tagContainerObject.getTagAsString()));
      tagContainer.getChildren().removeIf(t -> t.equals(tagContainerObject.getContainer()));
    });
  }

  /**
   * Updates the the tags of the photo in the database
   */
  private void updateDatabaseAndClose() {
    int index = UserInfo.getUser().getPhotos().indexOf(photo);
    UserInfo.getUser().getPhotos().get(index).setTags(photo.getTags());
    Hibernate.updateUser(UserInfo.getUser());
    stage.close();
  }

  /**
   * Displays the the stage created in the object
   */
  public void display() {
    stage.showAndWait();
  }
}