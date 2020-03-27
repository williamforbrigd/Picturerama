package Scenes;

import Components.UserInfo;
import Css.Css;
import Database.Hibernate;
import Database.HibernateClasses.Album;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Class for the scene albums
 */
public class Albums extends SceneBuilder {

    private ArrayList<Button> albumButtons = new ArrayList<>();
    private ScrollPane scrollPane = new ScrollPane();
    private VBox scrollPaneVbox = new VBox();
    private Button newAlbumButton = new Button("New Album");
    private Button deleteAlbumButton = new Button("Delete Album");
    private Stage dialogWindow;
    private VBox dialogVbox;
    private HBox dialogHBox;
    private Text dialogText;
    private ChoiceBox<String> choiceBox = new ChoiceBox();

    /**
     * Constructor that initializes the basic structure of the app and the structure of the album scene
     */
    public Albums() {
        super();
        this.setLayout();
    }

    /**
     * Overrides the setLayout in scenebuilder and adds the structure of the album scene
     */
    @Override
    public void setLayout() {
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
                Css.setAlbumButtons(albumButton);
                scrollPaneVbox.getChildren().add(albumButton);
            });
        } catch(NullPointerException e) {
            e.printStackTrace();
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
        Css.setButtonsAlbumScene(newAlbumButton, deleteAlbumButton);
    }

    /**
     * Opens opp a new scene where you can create a new album
     */
    private void createNewAlbumButtonPressed() {
        createPopupDialog();

        dialogWindow.setTitle("Add Album");
        dialogText.setText("Please enter the name of the album to be added:");

        TextField nameAlbumInput = new TextField();
        nameAlbumInput.setPromptText("Album name");
        Css.setTextFieldAlbums(nameAlbumInput);

        Button addAlbum = new Button("Add album");
        Css.setAddAlbumButton(addAlbum);
        dialogHBox.getChildren().addAll(nameAlbumInput, addAlbum);
        dialogVbox.getChildren().addAll(dialogText, dialogHBox);

        addAlbum.setOnAction(e -> {
            if(nameAlbumInput.getText().trim().equals("") || nameAlbumInput.getText() == null) {
                dialogText.setText("Please enter a valid name");
                Button tryAgain = new Button("Try again");
                Css.setAddAlbumButton(tryAgain);
                dialogVbox.getChildren().clear();
                dialogVbox.getChildren().addAll(dialogText, tryAgain);
                tryAgain.setOnAction(event -> {
                    dialogVbox.getChildren().clear();
                    dialogText.setText("Please enter the name of the album to be added:");
                    dialogVbox.getChildren().addAll(dialogText, dialogHBox);
                });
            } else {
                addAlbumButtonPressed(nameAlbumInput.getText().trim());
                nameAlbumInput.clear();
                dialogWindow.close();
            }
        });
    }

    /**
     * Creates a the popup that that is used when creating
     */
    private void createPopupDialog() {
        dialogWindow = new Stage();
        dialogWindow.initModality(Modality.APPLICATION_MODAL);
        dialogWindow.getIcons().add(new Image("file:src/main/App/Images/Logo.png"));

        dialogVbox = new VBox();
        dialogVbox.setAlignment(Pos.CENTER);

        dialogText = new Text();
        Css.setTextAlbums(dialogText);

        dialogHBox = new HBox();
        dialogHBox.setPadding(new Insets(10,10,10,10));
        dialogHBox.setSpacing(10);

        Scene dialogScene = new Scene(dialogVbox, 500, 100);
        dialogWindow.setScene(dialogScene);
        dialogWindow.show();
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
        Css.setAlbumButtons(albumButton);
        scrollPaneVbox.getChildren().add(albumButton);
    }

    /**
     * Creates a new scene for the user to delete an album, and the user can select the album to be deleted.
     * Calls the deleteAlbum()-method that deletes the specific album selected. If the user has no albums, a text will be
     * printed out to the screen containing this information.
     */
    private void deleteButtonPressed() {
        this.createPopupDialog();
        this.setupChoiceBox();
        
        dialogWindow.setTitle("Delete Album");
        dialogText.setText("Please select the album to be deleted.");
        Button deleteButton = new Button("Delete Album");
        Css.setAddAlbumButton(deleteButton);
        dialogHBox.getChildren().addAll(choiceBox, deleteButton);
        dialogVbox.getChildren().addAll(dialogText, dialogHBox);

        if(UserInfo.getUser().getAlbums().isEmpty()) {
            dialogText.setText("You don't have any albums");
            dialogVbox.getChildren().remove(dialogHBox);
            Button button = new Button("Ok");
            dialogVbox.getChildren().add(button);
            Css.setAddAlbumButton(button);
            button.setOnAction(event -> {
                dialogVbox.getChildren().remove(button);
                dialogVbox.getChildren().add(dialogHBox);
                dialogWindow.close();
            });
        }

        deleteButton.setOnAction(e -> {
            deleteAlbum();
            choiceBox.getItems().remove(choiceBox.getSelectionModel().getSelectedItem());
            dialogWindow.close();
        });
    }

    /**
     * Helping method to delete an album and the album button gets removed from the layout.
     */
    private void deleteAlbum() {
        String albumSelected = choiceBox.getSelectionModel().getSelectedItem();
        UserInfo.getUser().getAlbums().removeIf(album -> album.getName().equals(albumSelected));
        Hibernate.updateUser(UserInfo.getUser());
        albumButtons.removeIf(button -> {
            if(button.getText().equals(albumSelected)) {
                scrollPaneVbox.getChildren().remove(button);
                return true;
            }
            return false;
        });
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
