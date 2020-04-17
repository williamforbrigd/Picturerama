package Roots;

import Components.PopupWindow;
import Components.FileLogger;
import Components.UserInfo;
import Css.Css;
import Database.Hibernate;
import Database.HibernateClasses.Album;
import Main.ApplicationManager;
import java.util.List;
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
 * Class for the albums root
 */
final class AlbumsRoot extends SceneRoot {

  private final List<Button> ALBUM_BUTTONS = new ArrayList<>();
  private final ScrollPane SCROLL_PANE = new ScrollPane();
  private final VBox SCROLL_PANE_VBOX = new VBox();
  private final Button NEW_ALBUM_BUTTON = new Button("New album");
  private final Button DELETE_ALBUM_BUTTON = new Button("Delete album");
  private final ChoiceBox<String> CHOICE_BOX = new ChoiceBox();

  /**
   * Constructor that initializes the albums root
   * Calls the set layout method
   */
  AlbumsRoot() {
    super();
    this.setLayout();
  }

  /**
   * Overrides the setLayout in SceneRoot and adds the structure of the albums root
   * Uses addAlbumsScrollPane
   * Uses addScrollPane
   * Uses addButtonsToBorderPane
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
   * Adds all the albums of the user in to the scroll pane of the root
   * Used in constructor
   */
  private void addAlbumsScrollPane() {
    try {
      UserInfo.getUser().getAlbums().forEach(album -> {
        Button albumButton = new Button(album.getName());
        albumButton.setOnAction(e -> ApplicationManager.setRoot(new AlbumDetailsRoot(album)));
        ALBUM_BUTTONS.add(albumButton);
        Css.setButton(650, 50, 18, albumButton);
        SCROLL_PANE_VBOX.getChildren().add(albumButton);
      });
    } catch (NullPointerException e) {
      FileLogger.getLogger().log(Level.FINE, e.getMessage());
      FileLogger.closeHandler();
    }
  }

  /**
   * Creates the scroll pane of the scene and adds it to the application
   * Used in constructor
   */
  private void addScrollPane() {
    SCROLL_PANE_VBOX.setPadding(new Insets(10, 10, 10, 10));
    SCROLL_PANE_VBOX.setSpacing(10);
    SCROLL_PANE.setContent(SCROLL_PANE_VBOX);
    SCROLL_PANE.setPrefSize(700, 700);
    SCROLL_PANE.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    SCROLL_PANE.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    Css.setAlbumScrollPaneBorder(SCROLL_PANE);
    super.getGridPane().add(SCROLL_PANE, 0, 0);
  }

  /**
   * Adds the buttons to the root
   * Used in constructor
   */
  private void addButtonsToBorderPane() {
    HBox hBox = new HBox();
    hBox.getChildren().addAll(NEW_ALBUM_BUTTON, DELETE_ALBUM_BUTTON);
    hBox.setAlignment(Pos.BASELINE_CENTER);
    hBox.setSpacing(20);
    hBox.setPadding(new Insets(10, 10, 10, 10));
    super.getBorderPane().setBottom(hBox);
    BorderPane.setAlignment(hBox, Pos.CENTER);

    NEW_ALBUM_BUTTON.setOnAction(e -> createNewAlbumButtonPressed());
    DELETE_ALBUM_BUTTON.setOnAction(e -> deleteButtonPressed());
    Css.setButton(340, 50, 18, NEW_ALBUM_BUTTON, DELETE_ALBUM_BUTTON);
  }

  /**
   * Opens up a new stage where you can create a new album
   * Used in addButtonsToBorderPan
   */
  private void createNewAlbumButtonPressed() {
    PopupWindow popupWindow = new PopupWindow(ApplicationManager.getStage(), 500, 100);

    popupWindow.getDialogWindow().setTitle("Add album");
    popupWindow.getDialogText().setText("Please enter the name of the album to be added:");

    TextField nameAlbumInput = new TextField();
    nameAlbumInput.setPromptText("Album name");
    Css.setTextField(700, 20, 17, nameAlbumInput);

    Button addAlbum = new Button("Add album");
    Css.setButton(500, 20, 17, addAlbum);
    popupWindow.getDialogHBox().getChildren().addAll(nameAlbumInput, addAlbum);

    addAlbum.setOnAction(e -> {
      if (nameAlbumInput.getText().trim().equals("") || nameAlbumInput.getText() == null) {
        popupWindow.getDialogText().setText("Please enter a valid name");
        Button tryAgain = new Button("Try again");
        Css.setButton(500, 20, 17, tryAgain);
        popupWindow.getDialogVBox().getChildren().clear();
        popupWindow.getDialogVBox().getChildren().addAll(popupWindow.getDialogText(), tryAgain);
        tryAgain.setOnAction(event -> {
          popupWindow.getDialogVBox().getChildren().clear();
          popupWindow.getDialogText().setText("Please enter the name of the album to be added:");
          popupWindow.getDialogVBox().getChildren().addAll(popupWindow.getDialogText(), popupWindow.getDialogHBox());
        });
      } else {
        addAlbumButtonPressed(nameAlbumInput.getText().trim());
        nameAlbumInput.clear();
        popupWindow.getDialogWindow().close();
      }
    });
  }

  /**
   * Uploads the new album that is created to the database
   * Used in createNewAlbumButtonPressed
   */
  private void addAlbumButtonPressed(String albumName) {
    Album album = new Album();
    album.setUserId(UserInfo.getUser().getId());
    album.setName(albumName);
    UserInfo.getUser().getAlbums().add(album);
    Hibernate.updateUser(UserInfo.getUser());
    Button albumButton = new Button(albumName);
    albumButton.setOnAction(e -> ApplicationManager.setRoot(new AlbumDetailsRoot(album)));
    ALBUM_BUTTONS.add(albumButton);
    Css.setButton(650, 50, 18, albumButton);
    SCROLL_PANE_VBOX.getChildren().add(albumButton);
  }

  /**
   * Creates a new pop up window for the user to delete an album, and the user can select the album to be deleted.
   * Calls the deleteAlbum()-method that deletes the specific album selected. If the user has no albums, a text will be
   * printed out to the screen containing this information.
   * Used in addButtonsToBorderPane
   */
  private void deleteButtonPressed() {
    PopupWindow popupWindow = new PopupWindow(ApplicationManager.getStage(), 500, 100);
    this.setupChoiceBox();

    popupWindow.getDialogWindow().setTitle("Delete Album");
    popupWindow.getDialogText().setText("Please select the album to be deleted.");
    Button deleteButton = new Button("Delete Album");
    Css.setButton(500, 20, 17, deleteButton);
    popupWindow.getDialogHBox().getChildren().addAll(CHOICE_BOX, deleteButton);

    if (UserInfo.getUser().getAlbums().isEmpty()) {
      popupWindow.getDialogText().setText("You don't have any albums");
      popupWindow.getDialogVBox().getChildren().remove(popupWindow.getDialogHBox());
      Button button = new Button("Ok");
      popupWindow.getDialogVBox().getChildren().addAll(button);
      Css.setButton(500, 20, 17, button);
      button.setOnAction(event -> {
        popupWindow.getDialogVBox().getChildren().remove(button);
        popupWindow.getDialogVBox().getChildren().add(popupWindow.getDialogHBox());
        popupWindow.getDialogWindow().close();
      });
    }

    deleteButton.setOnAction(e -> {
      deleteAlbum();
      CHOICE_BOX.getItems().remove(CHOICE_BOX.getSelectionModel().getSelectedItem());
      popupWindow.getDialogWindow().close();
    });
  }

  /**
   * Helping method to delete an album and the album button gets removed from the layout.
   * Used in deleteButtonPressed
   */
  private void deleteAlbum() {
    String albumSelected = CHOICE_BOX.getSelectionModel().getSelectedItem();
    Album album = UserInfo.getUser().getAlbums().stream().filter(a -> a.getName().equals(albumSelected)).findAny().orElse(null);
    if (album != null) {
      album.getPhotos().forEach(photo -> photo.getAlbums().remove(album));
      UserInfo.getUser().getAlbums().remove(album);
      Hibernate.updateUser(UserInfo.getUser());
      ALBUM_BUTTONS.removeIf(button -> {
        if (button.getText().equals(albumSelected)) {
          SCROLL_PANE_VBOX.getChildren().remove(button);
          return true;
        }
        return false;
      });
    }
  }

  /**
   * Sets up the checkboxes and adds styling to it
   * Used in deleteButtonPressed
   */
  private void setupChoiceBox() {
    CHOICE_BOX.getItems().clear();
    CHOICE_BOX.getStyleClass().add("choice-box");
    CHOICE_BOX.getStylesheets().add("file:src/main/App/Css/ChoiceBoxStyle.css");
    UserInfo.getUser().getAlbums().forEach(album -> {
      if (!CHOICE_BOX.getItems().contains(album.getName())) {
        CHOICE_BOX.getItems().add(album.getName());
      }
    });
  }
}
