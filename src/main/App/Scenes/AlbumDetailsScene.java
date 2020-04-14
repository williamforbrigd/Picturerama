package Scenes;

import Components.FileLogger;
import Components.PopupWindow;
import Components.PDFcreator;
import Components.PhotoContainer;
import Components.UserInfo;
import Css.Css;
import Css.FeedBackType;
import Database.Hibernate;
import Database.HibernateClasses.Album;
import Database.HibernateClasses.Photo;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;

import com.itextpdf.text.DocumentException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Class for the album details scene, which shows all the pictures in an album.
 */
final class AlbumDetailsScene extends SceneBuilder {
  private final VBox SCROLL_PANE_VBOX = new VBox();
  private final ScrollPane SCROLL_PANE = new ScrollPane();
  private final Button PDF_BUTTON = new Button("Generate PDF Album");
  private final Button DELETE_ALBUM = new Button("Delete album");
  private final Button DELETE_PHOTOS = new Button("Remove selected photos");
  private Set<Photo> albumPhotoList;
  private String albumName;
  private final TextField SAVE_LOCATION = new TextField();
  private final Label DIALOG_FEEDBACK_LABEL = new Label();
  private final List<PhotoContainer> CONTAINERS = new ArrayList<>();

  /**
   * Album details scene constructor, uses SceneBuilder constructor to create an object of the album details scene class
   */
  AlbumDetailsScene(Album album) {
    super();
    this.setLayout();
    this.setup(album);
  }

  /**
   * Sets up the layout of the album details scene, overrides the setlayout method of scenebuilder
   */
  @Override
  void setLayout() {
    super.setLayout();
    super.setGridPane();
    super.getGridPane().add(SCROLL_PANE, 0, 2);
    super.getGridPane().add(PDF_BUTTON, 0, 3);
    super.getGridPane().add(DELETE_PHOTOS, 0, 4);
    super.getGridPane().add(DELETE_ALBUM, 0, 5);
    super.getGridPane().setMaxWidth(700.0D);
    PDF_BUTTON.setOnAction(s -> generatePdfPressed());
    Css.setButton(700, 50, 18, PDF_BUTTON, DELETE_PHOTOS, DELETE_ALBUM);
    this.setupScrollPane();
  }

  /**
   * Sets up the scrollpane which will contain photos. The scrollpane will be the center layout of the scene.
   */
  private void setupScrollPane() {
    SCROLL_PANE.setContent(SCROLL_PANE_VBOX);
    SCROLL_PANE.setStyle("-fx-background-color:transparent;");
    SCROLL_PANE.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
    SCROLL_PANE_VBOX.setStyle("-fx-background-color: transparent;");
    SCROLL_PANE.fitToWidthProperty().set(true);
    SCROLL_PANE.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
  }

  /**
   * Method that is ran when clicking the generate pdf button and sets up the action popup
   */
  private void generatePdfPressed() {
    PopupWindow popupWindow = new PopupWindow(StageInitializer.getStage(), 500, 200);

    popupWindow.getDialogWindow().setTitle("Download album");

    popupWindow.getDialogText().setText("File location: ");

    SAVE_LOCATION.setPromptText("File location");
    Css.setTextField(350, 20, 17, SAVE_LOCATION);
    SAVE_LOCATION.setDisable(true);

    Button fileExplorer = new Button("Browse");
    Button downloadPdf = new Button("Download");
    Css.setButton(480, 20, 17, downloadPdf);
    Css.setButton(150, 20, 17, fileExplorer);


    fileExplorer.setOnAction(s -> {
      try {
        this.setSaveLocation(System.getProperty("user.home"), popupWindow.getDialogWindow());
      } catch (Exception e) {
        SAVE_LOCATION.clear();
      }
    });

    downloadPdf.setMaxWidth(450);
    downloadPdf.setOnAction(e -> {
      if (SAVE_LOCATION.getText().trim().length() != 0) {
        generatePDF(SAVE_LOCATION.getText());
        SAVE_LOCATION.clear();
        popupWindow.getDialogWindow().close();
      } else {
        Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Choose file location before downloading", 13, DIALOG_FEEDBACK_LABEL, 6);
      }
    });

    popupWindow.getDialogHBox().getChildren().addAll(SAVE_LOCATION, fileExplorer);
    popupWindow.getDialogVBox().getChildren().addAll(DIALOG_FEEDBACK_LABEL, downloadPdf);
  }

  /**
   * Sets up the album scene with pagetitle and the album's photos in the scrollpane
   *
   * @param album the album which will be shown with all the pictures in the album. If the album contains no pictures,
   *              a text will be shown in the scene to inform the user.
   */
  private void setup(Album album) {
    super.setPageTitle(album.getName());
    this.albumPhotoList = album.getPhotos();
    this.albumName = album.getName();

    if (!albumPhotoList.isEmpty()) {
      albumPhotoList.forEach(photo -> {
        PhotoContainer p = new PhotoContainer(photo);
        SCROLL_PANE_VBOX.getChildren().add(p.getPhotoContainerHBox());
        CONTAINERS.add(p);
      });
    } else {
      showAlbumIsEmpty();
    }
    DELETE_ALBUM.setOnAction(e -> {
      UserInfo.getUser().getAlbums().remove(album);
      Hibernate.updateUser(UserInfo.getUser());
      StageInitializer.setScene(new AlbumsScene());
    });
    DELETE_PHOTOS.setOnAction(e -> deleteSelectedPhotos(album));
  }

  /**
   * Tell user that this album does not contain any photos
   */
  private void showAlbumIsEmpty() {
    Text text = new Text("This album does not contain any photos yet. You can add more photos in \"Photos\"");
    Css.setText(17, text);
    SCROLL_PANE.setContent(text);
  }

  /**
   * Method which deletes the selected photos from the album and updates the scene
   *
   * @param album the album that the selected photos will be removed from
   */
  private void deleteSelectedPhotos(Album album) {
    if (albumPhotoList.isEmpty()) {
      showAlbumIsEmpty();
    } else {
      ArrayList<Photo> selectedPhotos = getSelectedPhotos();
      selectedPhotos.forEach(photo -> {
        Optional<PhotoContainer> optionalPhotoContainer = CONTAINERS.stream().filter(c -> c.getPhoto().equals(photo)).findAny();
        if (optionalPhotoContainer.isPresent()) {
          album.getPhotos().remove(photo);
          PhotoContainer photoContainer = optionalPhotoContainer.get();
          SCROLL_PANE_VBOX.getChildren().remove(photoContainer.getPhotoContainerHBox());
        } else {
          FileLogger.getLogger().log(Level.FINE, "Photo: {0} is not present in the list containers", photo);
          FileLogger.closeHandler();
        }
      });
      Hibernate.updateUser(UserInfo.getUser());


    }
  }

  /**
   * Helping method to retrieve the photos that are selected by the user. These photos are collected in a list.
   *
   * @return photos which is a list of the photos that are selected.
   */
  private ArrayList<Photo> getSelectedPhotos() {
    ArrayList<Photo> photos = new ArrayList<>();
    CONTAINERS.forEach(container -> {
      if (container.getCheckBox().isSelected()) {
        photos.add(container.getPhoto());
      }
    });
    return photos;
  }

  private void setSaveLocation(String startDirectory, Stage stage) {
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("Explore");
    File defaultDirectory = new File(startDirectory);
    chooser.setInitialDirectory(defaultDirectory);
    String selectedDirectory = chooser.showDialog(stage).getAbsolutePath();
    SAVE_LOCATION.setText(selectedDirectory);
  }

  /**
   * Method to generate pdf and is ran when clicking download in generatepdf window
   *
   * @param saveLocation is the location that the user wanted the pdf saved to
   */
  private void generatePDF(String saveLocation) {
    List<Photo> photos = new ArrayList<>();
    photos.addAll(this.albumPhotoList);
    String saveLink = saveLocation + "/" + albumName + ".pdf";
    try {
      PDFcreator.createPDF(photos, saveLink);
      File pdfFile = new File(saveLink);
      if (pdfFile.exists() && Desktop.isDesktopSupported()) {
        Desktop.getDesktop().open(pdfFile);
      }
    } catch (DocumentException e) {
      FileLogger.getLogger().log(Level.FINE, e.getMessage());
      FileLogger.closeHandler();
      Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Could not create a PDF", 13, DIALOG_FEEDBACK_LABEL, 6);

    } catch (IOException e) {
      FileLogger.getLogger().log(Level.FINE, e.getMessage());
      FileLogger.closeHandler();
      Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Could not retrieve images", 13, DIALOG_FEEDBACK_LABEL, 6);
    }
  }
}
