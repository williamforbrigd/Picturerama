package Scenes;

import Components.Photo;
import Components.PhotoContainer;
import Css.Css;
import Database.DBConnection;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.stage.Screen;

public class Search extends SceneBuilder {
  private ArrayList<Photo> photoList = new ArrayList<>();
  private ScrollPane scrollPane = new ScrollPane();
  private VBox scrollPaneVBox = new VBox();
  private ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
  private ArrayList<PhotoContainer> photoContainerList = new ArrayList<>();
  private TextField searchTextField = new TextField();
  private CheckBox selectAllCheckBox = new CheckBox("Select all:");
  private Button addToAlbumButton = new Button("Add to album");

  public Search(){
    super();
    photoList.addAll(DBConnection.getPhotos());
    this.setLayout();
  }

  @Override
  public void setLayout(){
    super.setLayout();
    super.setPageTitle("Search");
    setupImagesInAScrollPane();
    setupSearchBar();
    setupAlbumButtons();
    super.getGridPane().add(scrollPane,0,1, 3, 1);
    super.getGridPane().add(searchTextField, 0, 0, 2, 1);
    super.getGridPane().add(selectAllCheckBox, 2, 0, 1, 1);
    GridPane.setHalignment(selectAllCheckBox, HPos.RIGHT);
    super.getGridPane().add(addToAlbumButton, 0, 2, 3, 1);
    super.getGridPane().setGridLinesVisible(false);
    super.getGridPane().setMaxWidth(700.0D);
    Css.setTextField(searchTextField);
    super.getGridPane().getStylesheets().add("file:src/App/Css/SearchField.css");
  }

  private void setupImagesInAScrollPane(){
    photoList.stream().forEach(photo -> {
      PhotoContainer photoContainer = new PhotoContainer(photo);
      scrollPaneVBox.getChildren().add(photoContainer.getPhotoContainer());
      photoContainerList.add(photoContainer);
      checkBoxArrayList.add(photoContainer.getCheckBox());
    });
    scrollPane.setContent(scrollPaneVBox);
    scrollPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
    scrollPaneVBox.setStyle("-fx-background-color: #FFFFFF");
    scrollPane.fitToWidthProperty().set(true);
    scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
  }

  private void setupSearchBar(){
    searchTextField.setId("searchField");
    searchTextField.setPromptText("Search for image...");
    searchTextField.setOnKeyTyped(action -> filter());
    selectAllCheckBox.setOnAction(action -> checkBoxArrayList.stream().forEach(checkBox -> checkBox.setSelected(selectAllCheckBox.isSelected())));
  }

  private void setupAlbumButtons(){
    Css.setButtonsSignUpLogin(addToAlbumButton);
  }

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
}

