package Scenes;

import Components.TagContainer;
import Components.UserInfo;
import Css.Css;
import Css.FeedBackType;
import Database.Hibernate;
import Database.HibernateClasses.Photo;
import Database.HibernateClasses.Tags;
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
public class ImageMetaDataViewer {
  //The flowPane containing the tag containers
  private FlowPane tagContainer = new FlowPane();
  private Photo photo;
  private Stage stage;

  public ImageMetaDataViewer(Photo photo) {
    this.photo = photo;
    this.stage = setup();
  }

  /**
   * Setup creates a new stage then sets it up
   * Since the methods returns a stage it will make testing the method easier
   *
   * @return stage, a new stage
   */
  private Stage setup() {

    //Making new stage here to make unit testing easier, since the method will return a result
    Stage _stage = new Stage();
    _stage.initModality(Modality.APPLICATION_MODAL);
    _stage.initOwner(StageInitializer.getStage());

    _stage.setOnCloseRequest(e -> {
     e.consume();
     updateDatabase();
     _stage.close();
    });

    _stage.setTitle(photo.getTitle());

    AnchorPane layout = new AnchorPane();

    VBox imageInfoContainer = new VBox();
    Label photoTitleLabel = new Label();

    Css.setLabel(30,photoTitleLabel);
    Label metadataLabel = new Label();
    Css.setLabel(12, metadataLabel);
    Label errorInLoadingImage = new Label("Image could not be loaded");
    Css.setFeedBackLabel(FeedBackType.Error, 13, errorInLoadingImage);


    Scene scene;
    try {
      photoTitleLabel.setText(photo.getTitle());
      metadataLabel.setText(photo.toString());


      imageInfoContainer.getChildren().addAll(photoTitleLabel, metadataLabel);
      imageInfoContainer.maxWidth(275);


      tagContainer.setMaxWidth(580);
      tagContainer.setPrefWrapLength(580);
      tagContainer.setPadding(new Insets(0, 10, 10, 0));
      tagContainer.setHgap(4);
      tagContainer.setVgap(4);

      //Stream that runs through all the photo's tags
      photo.getTags().forEach(s -> {
        //Creates a new tag container object
        TagContainer tagContainerObject = new TagContainer(s.getTag());
        //Adds the tag container object to the flowpane tagContainer
        tagContainer.getChildren().add(tagContainerObject.getContainer());
        setButtonFunctionality(tagContainerObject);
      });


      ImageView imageView = new ImageView(new Image(photo.getUrl(), 255, 255, true, true));

      AnchorPane.setLeftAnchor(imageInfoContainer, 10.0);
      AnchorPane.setRightAnchor(imageView, 10.0);

      //Adding child nodes to parent node
      layout.getChildren().addAll(imageInfoContainer, imageView);

      scene = new Scene(layout, 600, 560);
    } catch (NullPointerException e) {
      AnchorPane.setTopAnchor(errorInLoadingImage, 10.0);
      layout.getChildren().add(errorInLoadingImage);
      scene = new Scene(layout);
    }

    VBox bottomContainer = new VBox();
    bottomContainer.setSpacing(10);

    Label tagLabel = new Label("Tags:");
    tagLabel.setPadding(new Insets(10,0,0,0));
    Label addTagLabel = new Label("Add a tag to picture:");
    Css.setLabel(15, tagLabel, addTagLabel);

    TextField tagField = new TextField();
    Css.setTextField(150,10,14,tagField);
    tagField.setPromptText("Tag name");

    Label feedbackLabel = new Label();
    Css.setFeedBackLabel(FeedBackType.Error, 13, feedbackLabel);

    Button addTagButton = new Button("Add tag");
    addTagButton.setOnAction(e -> {
      Tags tag = new Tags(tagField.getText(), photo.getId());
      if (tagField.getText() == null || tagField.getText().trim().equals("")) {
        feedbackLabel.setText("Error: the tag has to have a name");
      } else if (photo.getTags().contains(tag)) {
        feedbackLabel.setText("Tag already registered");
      } else {
        tag.setPhotoId(photo.getId());
        photo.getTags().add(tag);
        TagContainer tagContainerObject = new TagContainer(tagField.getText());
        tagContainer.getChildren().add(tagContainerObject.getContainer());
        this.setButtonFunctionality(tagContainerObject);
        feedbackLabel.setText("");
        tagField.clear();
      }
    });


    //Closes stage if it is not the focus
    _stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (!isNowFocused) {
        updateDatabase();
        _stage.close();
      }
    });

    Button closeButton = new Button("Close");
    Css.setButton(582,25,20,addTagButton,closeButton);
    closeButton.setOnAction(e -> {
      updateDatabase();
      _stage.close();
    });
    bottomContainer.setPadding(new Insets(10, 10, 10, 10));
    bottomContainer.setSpacing(6);
    bottomContainer.getChildren().addAll(tagLabel, tagContainer, addTagLabel, tagField, feedbackLabel, addTagButton, closeButton);
    AnchorPane.setBottomAnchor(bottomContainer, 5.0);
    layout.getChildren().add(bottomContainer);

    layout.setPadding(new Insets(30, 10, 10, 10));
    layout.setStyle("-fx-padding: 10 0 0 0");

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
  private void updateDatabase() {
    int index = UserInfo.getUser().getPhotos().indexOf(photo);
    UserInfo.getUser().getPhotos().get(index).setTags(photo.getTags());
    Hibernate.updateUser(UserInfo.getUser());
  }

  /**
   * Displays the the stage created in the object
   */
  public void display() {
    stage.showAndWait();
  }
}