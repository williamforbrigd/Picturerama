package Scenes;

import Components.ActionPopup;
import Components.PhotoContainer;
import Components.UserInfo;
import Css.Css;
import Database.Hibernate;
import Database.HibernateClasses.Album;
import Database.HibernateClasses.Photo;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ChoiceBox;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Screen;

/**
 * Class for the Search scene
 */
public class Search extends SceneBuilder {
  private ArrayList<Photo> photoList = new ArrayList<>();
  private ScrollPane scrollPane = new ScrollPane();
  private VBox scrollPaneVBox = new VBox();
  private ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
  private ArrayList<PhotoContainer> photoContainerList = new ArrayList<>();
  private TextField searchTextField = new TextField();
  private HBox selectAllHBox = new HBox();
  private CheckBox selectAllCheckBox = new CheckBox();
  private Button addToAlbumButton = new Button("Add to album");
  ChoiceBox<String> choiceBox = new ChoiceBox<>();
  private Button deleteButton = new Button("Delete selected photos");


  /**
   * Sets up the search scene and adds all the users photos to the photo list
   */
  public Search(){
    super();
    photoList.addAll(UserInfo.getUser().getPhotos());
    this.setLayout();
  }

  /**
   * Sets up the layout of the search scene overrides the setlayout method of scenbuilder
   */
  @Override
  public void setLayout(){
    super.setLayout();
    super.setPageTitle("Search");
    setupImagesInAScrollPane();
    setupSearchBar();
    setupAlbumButtons();
    setupSelectAllHBox();
    setupDeleteButton();
    super.getGridPane().add(scrollPane,0,1, 3, 1);
    super.getGridPane().add(searchTextField, 0, 0, 2, 1);
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
    photoList.stream().forEach(photo -> {
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
    selectAllCheckBox.setOnAction(action -> checkBoxArrayList.stream().forEach(checkBox -> checkBox.setSelected(selectAllCheckBox.isSelected())));
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
   * Method for the search functionality
   */
  private void filter(){
    scrollPaneVBox.getChildren().clear();
    if(searchTextField.getText().equals("")){
      photoContainerList.stream().forEach(child -> scrollPaneVBox.getChildren().add(child.getPhotoContainer()));
    } else {
      photoContainerList.stream().forEach(child -> {
        if(child.getPhoto().getTitle().toLowerCase().contains(searchTextField.getText().toLowerCase())) {
          scrollPaneVBox.getChildren().add(child.getPhotoContainer());
        }
      });
    }
  }

  /**
   * Method that creates the popup that can create albums and creates the action popup
   */
  private void createNewAlbumButtonPressed() {
    ActionPopup ap = new ActionPopup(500,100);

    ap.getDialogWindow().setTitle("Add to Album");

    ap.getDialogText().setText("Please select the name of the album: ");

    setupChoiceBox();
    Css.setChoiceBoxAlbums(choiceBox);

    Button addAlbum = new Button("Add to album");
    Css.setButton(500,20,17,addAlbum);
    addAlbum.setOnAction(e -> {
      updateUser(choiceBox.getValue());
      ap.getDialogWindow().close();
    });

    ap.getDialogHBox().getChildren().addAll(choiceBox, addAlbum);
  }

  /**
   * Sets up the checkboxes and adds styling to it
   */
  public void setupChoiceBox(){
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
   * Gets the index of the album that is selected
   * @param albumName the name of the selected album
   * @return the index of the selected album in the users album list
   */
  public int indexOfAlbum(String albumName){
    int index = -1;
    List<Album> albums = UserInfo.getUser().getAlbums();
    for (int i = 0; i <albums.size() ; i++) {
      if(albums.get(i).getName().equals(albumName)){
        index = i;
      }
    }
    return index;
  }

  /**
   * Method that updates the photos in the selected album
   * @param albumName name of the selected album
   */
  public void updateUser(String albumName){
    int index = indexOfAlbum(albumName);
    ArrayList<Photo> checkedPhoto = getCheckedPhotos();
    checkedPhoto.forEach(s -> UserInfo.getUser().getAlbums().get(index).getAlbumPhotos().add(s));
    Hibernate.updateUser(UserInfo.getUser());
  }

  /**
   * Private method for deleting selected photos.
   */
  private void deleteSelectedPhotos(){
    ArrayList<Photo> selectedPhotos = getCheckedPhotos();
    selectedPhotos.forEach(photo -> {
      UserInfo.getUser().getPhotos().remove(photo);
      PhotoContainer photoContainer = photoContainerList.stream().filter(c -> c.getPhoto().equals(photo)).findAny().get();
      scrollPaneVBox.getChildren().remove(photoContainer.getPhotoContainer());
    });
    Hibernate.updateUser(UserInfo.getUser());
  }
}

