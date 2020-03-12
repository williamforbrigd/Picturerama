package Scenes;

import Components.Photo;
import Components.PhotoContainer;
import Css.Css;
import Database.DBConnection;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.stage.Screen;

public class Search extends SceneBuilder {
    private static ArrayList<Photo> photoList = new ArrayList<>();
    private static ScrollPane scrollPane = new ScrollPane();
    private static VBox scrollPaneVBox = new VBox();
    private static ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();
    private static ArrayList<PhotoContainer> photoContainerList = new ArrayList<>();
    private static Button searchButton = new Button("Search");
    private static TextField searchTextField = new TextField();
    private static CheckBox selectAllCheckBox = new CheckBox("Select all:");
    private static Button addToAlbumButton = new Button("Add to album");
    private static Button viewAlbumsButton = new Button("View albums");

    public Search(){
        super();
        DBConnection.getPhotos().stream().forEach(photo -> {
            photoList.add(photo);
        });
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
        super.getGridPane().add(searchTextField, 0, 0, 1, 1);
        super.getGridPane().add(searchButton, 1, 0, 1, 1);
        super.getGridPane().add(selectAllCheckBox, 2, 0, 1, 1);
        GridPane.setHalignment(selectAllCheckBox, HPos.RIGHT);
        super.getGridPane().add(addToAlbumButton, 0, 2);
        super.getGridPane().add(viewAlbumsButton, 2, 2);
        super.getGridPane().setGridLinesVisible(false);
        Css.setTextField(searchTextField);
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
        searchTextField.setPromptText("Write some keywords to filter images...");
        searchTextField.setOnKeyTyped(action -> filter());
        searchButton.setOnAction(action -> filter());
        Css.setButtonsSignUpLogin(searchButton);
        selectAllCheckBox.setOnAction(action -> checkBoxArrayList.stream().forEach(checkBox -> checkBox.setSelected(selectAllCheckBox.isSelected())));
    }

    private void setupAlbumButtons(){
        Css.setButtonsSignUpLogin(addToAlbumButton, viewAlbumsButton);
        viewAlbumsButton.setPrefSize(500, 100);
        addToAlbumButton.setPrefSize(500, 100);
    }

    private void filter(){
        scrollPaneVBox.getChildren().clear();
        if(searchTextField.getText().equals("")){
            photoContainerList.stream().forEach(child -> {
                scrollPaneVBox.getChildren().add(child.getPhotoContainer());
            });
        } else {
            photoContainerList.stream().forEach(child -> {
                if(child.getPhoto().getTitle().toLowerCase().contains(searchTextField.getText().toLowerCase())) {
                    scrollPaneVBox.getChildren().add(child.getPhotoContainer());
                }
            });
        }
    }
}

