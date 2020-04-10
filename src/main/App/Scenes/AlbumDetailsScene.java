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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
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
class AlbumDetailsScene extends SceneBuilder {
  private VBox scrollPaneVBox = new VBox();
  private ScrollPane scrollPane = new ScrollPane();
  private Button pdfButton = new Button("Generate PDF Album");
  private Button deleteAlbum = new Button("Delete album");
  private Button deletePhoto = new Button("Remove selected photos");
  private Set<Photo> albumPhotoList;
  private String albumName;
  private TextField saveLocation = new TextField();
  private Label dialogFeedBackLabel;
  private List<PhotoContainer> containers = new ArrayList<>();

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
    super.getGridPane().add(scrollPane, 0, 2);
    super.getGridPane().add(pdfButton, 0, 3);
    super.getGridPane().add(deletePhoto, 0, 4);
    super.getGridPane().add(deleteAlbum, 0, 5);
    super.getGridPane().setMaxWidth(700.0D);
    pdfButton.setOnAction(s -> generatePdfPressed());
    Css.setButton(700,50,18,pdfButton,deletePhoto,deleteAlbum);
    this.setupScrollPane();
  }

  /**
   * Sets up the scrollpane which will contain photos. The scrollpane will be the center layout of the scene.
   */
  private void setupScrollPane() {
    scrollPane.setContent(scrollPaneVBox);
    scrollPane.setStyle("-fx-background-color:transparent;");
    scrollPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
    scrollPaneVBox.setStyle("-fx-background-color: transparent;");
    scrollPane.fitToWidthProperty().set(true);
    scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
  }

  /**
   * Method that is ran when clicking the generate pdf button and sets up the action popup
   */
  private void generatePdfPressed() {
    PopupWindow popupWindow = new PopupWindow(StageInitializer.getStage(),500,200);

    popupWindow.getDialogWindow().setTitle("Download album");

    dialogFeedBackLabel = new Label();
    Css.setFeedBackLabel(FeedBackType.ERROR, 13, dialogFeedBackLabel);

    popupWindow.getDialogText().setText("File location: ");

    saveLocation.setPromptText("File location");
    Css.setTextField(350,20,17,saveLocation);
    saveLocation.setDisable(true);

    Button fileExplorer = new Button("Browse");
    Button downloadPdf = new Button("Download");
    Css.setButton(480,20,17, downloadPdf);
    Css.setButton(150,20,17,fileExplorer);

    popupWindow.getDialogHBox().getChildren().addAll(saveLocation, fileExplorer);
    popupWindow.getDialogVBox().getChildren().addAll(dialogFeedBackLabel, downloadPdf);

    fileExplorer.setOnAction(s -> {
      try {
        this.setSaveLocation(System.getProperty("user.home"), popupWindow.getDialogWindow());
      } catch (Exception e) {
        dialogFeedBackLabel.setText("Could not retrieve image from file explorer");
        saveLocation.clear();
      }
    });

    downloadPdf.setMaxWidth(450);
    downloadPdf.setOnAction(e -> {
      if (saveLocation.getText().trim().length() != 0) {
        generatePDF(saveLocation.getText());
        saveLocation.clear();
        popupWindow.getDialogWindow().close();
      } else {
        dialogFeedBackLabel.setText("Choose file location before downloading");
      }
    });

    popupWindow.getDialogHBox().getChildren().addAll(saveLocation, fileExplorer);
    popupWindow.getDialogVBox().getChildren().addAll(dialogFeedBackLabel, downloadPdf);
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
        scrollPaneVBox.getChildren().add(p.getPhotoContainerHBox());
        containers.add(p);
      });
    } else {
      showAlbumIsEmpty();
    }
    deleteAlbum.setOnAction(e -> {
      UserInfo.getUser().getAlbums().remove(album);
      Hibernate.updateUser(UserInfo.getUser());
      StageInitializer.setScene(new AlbumsScene());
    });
    deletePhoto.setOnAction(e -> deleteSelectedPhotos(album));
  }

  /**
   * Tell user that this album does not contain any photos
   */
  private void showAlbumIsEmpty() {
    Text text = new Text("This album does not contain any photos yet. You can add more photos in \"Photos\"");
    Css.setText(17, text);
    scrollPane.setContent(text);
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
        Optional<PhotoContainer> optionalPhotoContainer = containers.stream().filter(c -> c.getPhoto().equals(photo)).findAny();
        if (optionalPhotoContainer.isPresent()) {
          album.getPhotos().remove(photo);
          PhotoContainer photoContainer = optionalPhotoContainer.get();
          scrollPaneVBox.getChildren().remove(photoContainer.getPhotoContainerHBox());
        } else {
          FileLogger.getLogger().log(Level.FINE, "Photo: " + photo + " is not present in the list containers");
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
    containers.forEach(container -> {
      if (container.getCheckBox().isSelected()) {
        photos.add(container.getPhoto());
      }
    });
    return photos;
  }

  private void setSaveLocation(String startDirectory, Stage stage) {
    dialogFeedBackLabel.setText("");
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("Explore");
    File defaultDirectory = new File(startDirectory);
    chooser.setInitialDirectory(defaultDirectory);
    String selectedDirectory = chooser.showDialog(stage).getAbsolutePath();
    saveLocation.setText(selectedDirectory);
  }

  /**
   * Method to generate pdf and is ran when clicking download in generatepdf window
   *
   * @param saveLocation is the location that the user wanted the pdf saved to
   */
  private void generatePDF(String saveLocation) {
    ArrayList<Photo> photos = new ArrayList<>();
    photos.addAll(this.albumPhotoList);
    String saveLink = saveLocation + "/" + albumName + ".pdf";
    try {
      PDFcreator.createPDF(photos, saveLink);
      File pdfFile = new File(saveLink);
      if (pdfFile.exists() && Desktop.isDesktopSupported()) {
        Desktop.getDesktop().open(pdfFile);
      }
    } catch (Exception ignored) {
      //TODO handle this exception
    }
  }
}
