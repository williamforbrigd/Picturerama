package Scenes;

import Components.PDFcreator;
import Components.PhotoContainer;
import Components.UserInfo;
import Css.Css;
import Database.Hibernate;
import Database.HibernateClasses.Album;
import Database.HibernateClasses.Photo;
import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Class for the album scene, which shows all the pictures in an album.
 */
public class AlbumScene extends SceneBuilder {
  private VBox scrollPaneVBox = new VBox();
  private ScrollPane scrollPane = new ScrollPane();
  private Button pdfButton = new Button("Generate PDF Album");
  private Button deleteAlbum = new Button("Delete album");
  private Button deletePhoto = new Button("Delete selected photos");
  private Set<Photo> albumPhotoList;
  private String albumName;
  private TextField saveLocation = new TextField();
  private Stage dialogWindow;
  private VBox dialogVBox;
  private HBox dialogHBox;
  private Text dialogText;
  private Label dialogFeedBackLabel;
  private ArrayList<PhotoContainer> containers = new ArrayList<>();

  /**
   * Album scene constructor, uses SceneBuilder constructor to create an object of the album scene class
   */
  public AlbumScene() {
    super();
    this.setLayout();
  }

  /**
   * Sets up the layout of the album scene, overrides the setlayout method of scenebuilder
   */
  @Override
  public void setLayout() {
    super.setLayout();
    super.setGridPane();
    super.getGridPane().add(scrollPane, 0, 2);
    super.getGridPane().add(pdfButton, 0, 3);
    super.getGridPane().add(deletePhoto, 0, 4);
    super.getGridPane().add(deleteAlbum, 0, 5);
    super.getGridPane().setMaxWidth(700.0D);
    pdfButton.setOnAction(s -> generatePdfPressed());
    Css.setAlbumButtons(pdfButton, deletePhoto, deleteAlbum);
    this.setupScrollPane();
  }

  /**
   * Sets up the scrollpane which will contain photos. The scrollpane will be the center layout of the scene.
   */
  private void setupScrollPane() {
    scrollPane.setContent(scrollPaneVBox);
    scrollPane.setStyle("-fx-background-color:transparent;");
    scrollPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
    scrollPaneVBox.setStyle("-fx-background-color: #FFFFFF");
    scrollPane.fitToWidthProperty().set(true);
    scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
  }

  /**
   * Method that is ran when clicking the generate pdf button
   */
  private void generatePdfPressed() {
    createPopupDialog();

    dialogWindow.getIcons().add(new Image("file:src/main/App/Images/Logo.png"));
    dialogWindow.setTitle("Download album");

    dialogText.setText("Save location: ");
    Css.setTextAlbums(dialogText);

    saveLocation.setPromptText("Save location");
    Css.setTextFieldAlbums(saveLocation);
    saveLocation.setDisable(true);

    Button fileExplorer = new Button("Explore");
    Css.setAddAlbumButton(fileExplorer);
    fileExplorer.setOnAction(s -> {
      try {
        this.setSaveLocation(System.getProperty("user.home"));
      } catch (Exception e) {
        dialogFeedBackLabel.setText("Could not retrieve image from file explorer");
        saveLocation.clear();
      }
    });

    Button downloadPdf = new Button("Download");
    Css.setAddAlbumButton(downloadPdf);
    downloadPdf.setMaxWidth(450);
    downloadPdf.setOnAction(e -> {
      generatePDF(saveLocation.getText());
      saveLocation.clear();
      dialogWindow.close();
    });

    dialogHBox.getChildren().addAll(saveLocation, fileExplorer);
    dialogVBox.getChildren().addAll(dialogText, dialogHBox, dialogFeedBackLabel, downloadPdf);
  }

  /**
   * Creates the popup window that is shown when clicking the generate pdf button
   */
  private void createPopupDialog() {
    dialogWindow = new Stage();
    dialogWindow.initModality(Modality.APPLICATION_MODAL);

    dialogVBox = new VBox();
    dialogVBox.setAlignment(Pos.CENTER);

    dialogText = new Text();

    dialogFeedBackLabel = new Label();
    Css.setErrorLabel(dialogFeedBackLabel);

    dialogHBox = new HBox();
    dialogHBox.setPadding(new Insets(10, 10, 10, 10));
    dialogHBox.setSpacing(10);

    Scene dialogScene = new Scene(dialogVBox, 500, 150);
    dialogWindow.setScene(dialogScene);
    dialogWindow.show();
  }

  /**
   * Sets up the album scene with pagetitle and the album's photos in the scrollpane
   *
   * @param album the album which will be shown with all the pictures in the album. If the album contains no pictures,
   *              a text will be shown in the scene to inform the user.
   */
  public void setup(Album album) {
    super.setPageTitle(album.getName());
    this.albumPhotoList = album.getAlbumPhotos();
    this.albumName = album.getName();

    if (!albumPhotoList.isEmpty()) {
      albumPhotoList.forEach(photo -> {
        PhotoContainer p = new PhotoContainer(photo);
        scrollPaneVBox.getChildren().add(p.getPhotoContainer());
        containers.add(p);
      });
    } else {
      showAlbumIsEmpty();
    }
    deleteAlbum.setOnAction(e -> {
      UserInfo.getUser().getAlbums().remove(album);
      Hibernate.updateUser(UserInfo.getUser());
      StageInitializer.setAlbumsScene();
    });
    deletePhoto.setOnAction(e -> deleteSelectedPhotos(album));
  }

  /**
   * Tell user that this album does not contain any photos
   */
  private void showAlbumIsEmpty() {
    Text text = new Text("This album does not contain any photos yet. You can add more photos in \"Photos\"");
    Css.setTextAlbums(text);
    scrollPane.setContent(text);
  }

  /**
   * Method which deletes the selected photos from the album and updates the scene
   *
   * @param album the album that the selected photos will be removed from
   */
  private void deleteSelectedPhotos(Album album) {
    ArrayList<Photo> selectedPhotos = getSelectedPhotos();
    selectedPhotos.forEach(photo -> {
      album.getAlbumPhotos().remove(photo);
      PhotoContainer photoContainer = containers.stream().filter(c -> c.getPhoto().equals(photo)).findAny().get();
      scrollPaneVBox.getChildren().remove(photoContainer.getPhotoContainer());
    });
    Hibernate.updateUser(UserInfo.getUser());
    if (albumPhotoList.isEmpty()) {
      showAlbumIsEmpty();
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

  public void setSaveLocation(String startDirectory) {
    dialogFeedBackLabel.setText("");
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("Explore");
    File defaultDirectory = new File(startDirectory);
    chooser.setInitialDirectory(defaultDirectory);
    String selectedDirectory = chooser.showDialog(this.dialogWindow).getAbsolutePath();
    saveLocation.setText(selectedDirectory);
  }

  /**
   * Method to generate pdf and is ran when clicking download in generatepdf window
   *
   * @param saveLocation is the location that the user wanted the pdf saved to
   */
  public void generatePDF(String saveLocation) {
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
