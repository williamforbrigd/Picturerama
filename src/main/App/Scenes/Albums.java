package Scenes;

import Components.ActionPopup;
import Components.FileLogger;
import Components.UserInfo;
import Css.Css;
import Database.Hibernate;
import Database.HibernateClasses.Album;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Class for the scene albums
 */
class Albums extends SceneBuilder {

    private ArrayList<Button> albumButtons = new ArrayList<>();
    private ScrollPane scrollPane = new ScrollPane();
    private VBox scrollPaneVbox = new VBox();
    private Button newAlbumButton = new Button("New Album");
    private Button deleteAlbumButton = new Button("Delete Album");
    private ChoiceBox<String> choiceBox = new ChoiceBox();

    /**
     * Constructor that initializes the basic structure of the app and the structure of the album scene
     */
    Albums() {
        super();
        this.setLayout();
    }

    /**
     * Overrides the setLayout in scenebuilder and adds the structure of the album scene
     */
    @Override
    void setLayout() {
        super.setLayout();
        super.setGridPane();
        super.setPageTitle("Albums");

        this.addScrollPane();
        this.addAlbumsScrollPane();
        this.addButtonsToBorderPane();
    }

    /**
     * Adds all the albums of the user in to the scrollpane of the scene
     */
    private void addAlbumsScrollPane() {
        try {
            UserInfo.getUser().getAlbums().forEach(album -> {
                Button albumButton = new Button(album.getName());
                albumButton.setOnAction(e -> StageInitializer.setAlbumScene(album));
                albumButtons.add(albumButton);
                Css.setButton(650,50,18,albumButton);
                scrollPaneVbox.getChildren().add(albumButton);
            });
        } catch(NullPointerException e) {
            FileLogger.getLogger().log(Level.FINE,e.getMessage());
            FileLogger.closeHandler();
        }
    }

    /**
     * Creates the scroll pane of the scene and adds it to the application
     */
    private void addScrollPane() {
        scrollPaneVbox.setPadding(new Insets(10,10,10,10));
        scrollPaneVbox.setSpacing(10);
        scrollPane.setContent(scrollPaneVbox);
        scrollPane.setPrefSize(700, 700);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        Css.setAlbumScrollPaneBorder(scrollPane);
        super.getGridPane().add(scrollPane, 0, 0);
    }

    /**
     * Adds the buttons to the scene
     */
    private void addButtonsToBorderPane() {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(newAlbumButton, deleteAlbumButton);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10,10,10,10));
        super.getBorderPane().setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);

        newAlbumButton.setOnAction(e -> createNewAlbumButtonPressed());
        deleteAlbumButton.setOnAction(e -> deleteButtonPressed());
        Css.setButton(340,50,18,newAlbumButton,deleteAlbumButton);
    }

    /**
     * Opens opp a new scene where you can create a new album
     */
    private void createNewAlbumButtonPressed() {
        ActionPopup ap = new ActionPopup(500, 100);

        ap.getDialogWindow().setTitle("Add Album");
        ap.getDialogText().setText("Please enter the name of the album to be added:");

        TextField nameAlbumInput = new TextField();
        nameAlbumInput.setPromptText("Album name");
        Css.setTextField(700,20,17,nameAlbumInput);

        Button addAlbum = new Button("Add album");
        Css.setButton(500,20,17,addAlbum);
        ap.getDialogHBox().getChildren().addAll(nameAlbumInput, addAlbum);

        addAlbum.setOnAction(e -> {
            if(nameAlbumInput.getText().trim().equals("") || nameAlbumInput.getText() == null) {
                ap.getDialogText().setText("Please enter a valid name");
                Button tryAgain = new Button("Try again");
                Css.setButton(500,20,17,tryAgain);
                ap.getDialogVbox().getChildren().clear();
                ap.getDialogVbox().getChildren().addAll(ap.getDialogText(), tryAgain);
                tryAgain.setOnAction(event -> {
                    ap.getDialogVbox().getChildren().clear();
                    ap.getDialogText().setText("Please enter the name of the album to be added:");
                    ap.getDialogVbox().getChildren().addAll(ap.getDialogText(), ap.getDialogHBox());
                });
            } else {
                addAlbumButtonPressed(nameAlbumInput.getText().trim());
                nameAlbumInput.clear();
                ap.getDialogWindow().close();
            }
        });
    }

    /**
     * Uploads the new album that is created to the database
     */
    private void addAlbumButtonPressed(String albumName) {
        Album album = new Album();
        album.setUserId(UserInfo.getUser().getId());
        album.setName(albumName);
        UserInfo.getUser().getAlbums().add(album);
        Hibernate.updateUser(UserInfo.getUser());
        Button albumButton = new Button(albumName);
        albumButton.setOnAction(e -> StageInitializer.setAlbumScene(album));
        albumButtons.add(albumButton);
        Css.setButton(650,50,18,albumButton);
        scrollPaneVbox.getChildren().add(albumButton);
    }

    /**
     * Creates a new scene for the user to delete an album, and the user can select the album to be deleted.
     * Calls the deleteAlbum()-method that deletes the specific album selected. If the user has no albums, a text will be
     * printed out to the screen containing this information.
     */
    private void deleteButtonPressed() {
        ActionPopup ap = new ActionPopup(500,100);
        this.setupChoiceBox();
        
        ap.getDialogWindow().setTitle("Delete Album");
        ap.getDialogText().setText("Please select the album to be deleted.");
        Button deleteButton = new Button("Delete Album");
        Css.setButton(500,20,17,deleteButton);
        ap.getDialogHBox().getChildren().addAll(choiceBox, deleteButton);

        if(UserInfo.getUser().getAlbums().isEmpty()) {
            ap.getDialogText().setText("You don't have any albums");
            ap.getDialogVbox().getChildren().remove(ap.getDialogHBox());
            Button button = new Button("Ok");
            ap.getDialogVbox().getChildren().addAll(button);
            Css.setButton(500,20,17,button);
            button.setOnAction(event -> {
                ap.getDialogVbox().getChildren().remove(button);
                ap.getDialogVbox().getChildren().add(ap.getDialogHBox());
                ap.getDialogWindow().close();
            });
        }

        deleteButton.setOnAction(e -> {
            deleteAlbum();
            choiceBox.getItems().remove(choiceBox.getSelectionModel().getSelectedItem());
            ap.getDialogWindow().close();
        });
    }

    /**
     * Helping method to delete an album and the album button gets removed from the layout.
     */
    private void deleteAlbum() {
        String albumSelected = choiceBox.getSelectionModel().getSelectedItem();
        Album album = UserInfo.getUser().getAlbums().stream().filter(a -> a.getName().equals(albumSelected)).findAny().orElse(null);
        if (album != null) {
            album.getPhotos().forEach(photo -> {
                photo.getAlbums().remove(album);
            });
            UserInfo.getUser().getAlbums().remove(album);
            Hibernate.updateUser(UserInfo.getUser());
            albumButtons.removeIf(button -> {
                if (button.getText().equals(albumSelected)) {
                    scrollPaneVbox.getChildren().remove(button);
                    return true;
                }
                return false;
            });
        }
    }

    /**
     * Sets up the checkboxes and adds styling to it
     */
    private void setupChoiceBox(){
        choiceBox.getItems().clear();
        choiceBox.getStyleClass().add("choice-box");
        choiceBox.getStylesheets().add("file:src/main/App/Css/ChoiceBoxStyle.css");
        Css.setChoiceBoxAlbums(choiceBox);
        UserInfo.getUser().getAlbums().forEach(album -> {
            if(!choiceBox.getItems().contains(album.getName()))
                choiceBox.getItems().add(album.getName());
        });
    }
}
