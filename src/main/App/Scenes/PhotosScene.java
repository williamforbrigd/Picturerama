package Scenes;

import Components.PopupWindow;
import Components.PhotoContainer;
import Components.UserInfo;
import Css.Css;
import Css.FeedBackType;
import Database.Hibernate;
import Database.HibernateClasses.Album;
import Database.HibernateClasses.Photo;
import Database.HibernateClasses.Tags;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.stage.Screen;

/**
 * Class for the Search scene
 */
class PhotosScene extends SceneBuilder {
  private ArrayList<Photo> photoList = new ArrayList<>();
  private ScrollPane scrollPane = new ScrollPane();
  private VBox scrollPaneVBox = new VBox();
  private ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
  private ArrayList<PhotoContainer> photoContainerList = new ArrayList<>();
  private TextField searchTextField = new TextField();
  private HBox selectAllHBox = new HBox();
  private CheckBox selectAllCheckBox = new CheckBox();
  private Button addToAlbumButton = new Button("Add to album");
  private ChoiceBox<String> choiceBox = new ChoiceBox<>();
  private Button deleteButton = new Button("Delete selected photos");
  private Label feedbackLabel = new Label();

  /**
   * Sets up the photos scene and adds all the users photos to the photo list
   */
  PhotosScene(){
    super();
    photoList.addAll(UserInfo.getUser().getPhotos());
    this.setLayout();
  }

  /**
   * Sets up the layout of the photos scene overrides the setLayout method of SceneBuilder
   */
  @Override
  void setLayout(){
    super.setLayout();
    super.setPageTitle("Photos");
    setupImagesInAScrollPane();
    setupSearchBar();
    setupAlbumButtons();
    setupSelectAllHBox();
    setupDeleteButton();
    super.getGridPane().add(scrollPane,0,1, 3, 1);
    super.getGridPane().add(searchTextField, 0, 0, 2, 1);
    super.getGridPane().add(feedbackLabel, 2, 0, 1, 1);
    super.getGridPane().setHalignment(feedbackLabel, HPos.LEFT);
    super.getGridPane().add(selectAllHBox, 2, 0, 1, 1);
    super.getGridPane().setHalignment(selectAllHBox, HPos.RIGHT);
    super.getGridPane().getStylesheets().add("file:src/main/App/Css/SelectAllCheckBoxStyle.css");
    super.getGridPane().add(addToAlbumButton, 0, 2, 1, 1);
    super.getGridPane().add(deleteButton, 2, 2, 1, 1);
    super.getGridPane().setGridLinesVisible(false);
    super.getGridPane().setMaxWidth(700.0D);
    //Styles layout components
    Css.setTextField(700,20,17,searchTextField);
    super.getGridPane().getStylesheets().add("file:src/main/App/Css/SearchField.css");
    Css.setAlbumScrollPaneBorder(scrollPane);
  }

  /**
   * Sets up the scroll pane in the search scene with all the photos of the user
   */
  private void setupImagesInAScrollPane(){
    photoList.forEach(photo -> {
      PhotoContainer photoContainer = new PhotoContainer(photo);
      scrollPaneVBox.getChildren().add(photoContainer.getPhotoContainer());
      photoContainerList.add(photoContainer);
      checkBoxArrayList.add(photoContainer.getCheckBox());
    });
    scrollPane.setContent(scrollPaneVBox);
    scrollPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
    scrollPane.fitToWidthProperty().set(true);
    scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
  }

  /**
   * Sets up the search bar and its functionality
   */
  private void setupSearchBar(){
    searchTextField.setId("searchField");
    searchTextField.setPromptText("Search for image...");
    searchTextField.setOnKeyTyped(action -> filter());
    selectAllCheckBox.setOnAction(action -> checkBoxArrayList.forEach(checkBox -> checkBox.setSelected(selectAllCheckBox.isSelected())));
  }

  /**
   * Sets up the select-all button
   */
  private void setupSelectAllHBox(){
    selectAllHBox.getChildren().addAll(new Label("Select all:"), selectAllCheckBox);
    selectAllHBox.setAlignment(Pos.CENTER_RIGHT);
    selectAllCheckBox.getStyleClass().add("check-box");
  }

  /**
   * Sets up button for deleting photos
   */
  private void setupDeleteButton(){
    Css.setButton(700, 25, 20, deleteButton);
    deleteButton.setOnAction(action -> deleteSelectedPhotos());
  }

  /**
   * Sets up the add to album button
   */
  private void setupAlbumButtons(){
    Css.setButton(700,25,20,addToAlbumButton);
    addToAlbumButton.setOnAction(s -> createNewAlbumButtonPressed());
  }

  /**
   * Method for the search functionality.
   * Filters the scrollpanes photos, showing a photo if its title contains the searchtext or one if its tags are equal to the searchtext.
   */
  private void filter(){
    scrollPaneVBox.getChildren().clear();
    if(searchTextField.getText().trim().equals("")){
      photoContainerList.forEach(child -> scrollPaneVBox.getChildren().add(child.getPhotoContainer()));
    } else {
      photoContainerList.forEach(container -> {
        if(container.getPhoto().getTitle().toLowerCase().contains(searchTextField.getText().trim().toLowerCase())) {
          scrollPaneVBox.getChildren().add(container.getPhotoContainer());
        } else {
          String textInput = searchTextField.getText().trim().toLowerCase().replaceAll(" ","");
          String[] multipleTags = textInput.split(",");
          if (getPhotoTags(container.getPhoto()).containsAll(Arrays.asList(multipleTags))) {
            scrollPaneVBox.getChildren().add(container.getPhotoContainer());
          }
        }
      });
    }
  }

  /**
   * Method that creates the popup that can create albums and creates the action popup
   */
  private void createNewAlbumButtonPressed() {
    PopupWindow popupWindow = new PopupWindow(500,100);
    popupWindow.getDialogWindow().setTitle("Add to Album");
    popupWindow.getDialogText().setText("Please select the name of the album: ");

    setupChoiceBox();
    Css.setChoiceBoxAlbums(choiceBox);

    Button addAlbum = new Button("Add to album");
    Css.setButton(500,20,17,addAlbum);
    addAlbum.setOnAction(e -> {
      updateUser(choiceBox.getValue());
      popupWindow.getDialogWindow().close();
    });

    popupWindow.getDialogHBox().getChildren().addAll(choiceBox, addAlbum);
  }

  /**
   * Sets up the checkboxes and adds styling to it
   */
  private void setupChoiceBox(){
    choiceBox.getItems().clear();
    choiceBox.getStyleClass().add("choice-box");
    choiceBox.getStylesheets().add("file:src/main/App/Css/ChoiceBoxStyle.css");
    UserInfo.getUser().getAlbums().forEach(s -> choiceBox.getItems().add(s.getName()));
  }

  /**
   * Helper method to get the checked photos in the search scene
   * @return a list of checked photos
   */
  private ArrayList<Photo> getCheckedPhotos(){
    ArrayList<Photo> checkedPhotos = new ArrayList<>();
    for (int i = 0; i<checkBoxArrayList.size(); i++) {
      if(checkBoxArrayList.get(i).isSelected()){
        checkedPhotos.add(photoList.get(i));
      }
    }
    return checkedPhotos;
  }

  /**
   * Method that updates the photos in the selected album
   * @param albumName name of the selected album
   */
  private void updateUser(String albumName){
    Album album = UserInfo.getUser().getAlbums().stream().filter(a -> a.getName().equals(albumName)).findAny().orElse(null);
    ArrayList<Photo> checkedPhoto = getCheckedPhotos();
    if (checkedPhoto.isEmpty()) {
      feedbackLabel.setText("Unsuccessful: No photos were chosen");
      Css.playFeedBackLabelTransition(FeedBackType.Error, 13, feedbackLabel);
    } else if (album == null) {
      feedbackLabel.setText("Unsuccessful: No album were chosen");
      Css.playFeedBackLabelTransition(FeedBackType.Error, 13, feedbackLabel);
    } else {
      checkedPhoto.forEach(s -> s.addAlbum(album));
      feedbackLabel.setText("Added to " + albumName);
      Css.playFeedBackLabelTransition(FeedBackType.Successful, 13, feedbackLabel);
    }
    Hibernate.updateUser(UserInfo.getUser());
  }

  /**
   * Private method for deleting selected photos.
   */
  private void deleteSelectedPhotos(){
    ArrayList<Photo> selectedPhotos = getCheckedPhotos();
    selectedPhotos.forEach(photo -> {
      photo.getAlbums().forEach(album -> {
        album.getPhotos().remove(photo);
      });
      UserInfo.getUser().getPhotos().remove(photo);
      PhotoContainer photoContainer = photoContainerList.stream().filter(c -> c.getPhoto().equals(photo)).findAny().get();
      photoContainer.getCheckBox().setSelected(false);
      scrollPaneVBox.getChildren().remove(photoContainer.getPhotoContainer());
    });
    Hibernate.updateUser(UserInfo.getUser());
    feedbackLabel.setText("Deleted successfully");
    Css.playFeedBackLabelTransition(FeedBackType.Successful, 13, feedbackLabel);
  }

  /**
   * Helping method to retrieve all the tags to a specific photo.
   * @param photo gets the tags of the photo.
   * @return all the tags to the specific photo.
   */
  private List<String> getPhotoTags(Photo photo) {
    return photo.getTags().stream()
                          .map(Tags::getTag)
                          .map(String::toLowerCase)
                          .collect(Collectors.toList());
  }
}
