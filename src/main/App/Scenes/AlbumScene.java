package Scenes;

import Components.PhotoContainer;
import Components.PDFcreator;
import Css.Css;
import Database.HibernateClasses.Album;
import Database.HibernateClasses.Photo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

/**
 * Class for the album scene, which shows all the pictures in an album.
 */
public class AlbumScene extends SceneBuilder {
    private VBox scrollPaneVBox;
    private ScrollPane scrollPane;
    private Button PDFbtn;
    Set<Photo> albumPhotoList;
    String albumName;
    private TextField savelocation = new TextField();
    private Stage dialogWindow;
    private VBox dialogVbox;
    private HBox dialogHBox;
    private Text dialogText;
    private String selectedDirectory;

    /**
     * Album scene constructor, uses SceneBuilder constructor to create an object of the album scene class
     */
    public AlbumScene() {
        super();
        scrollPaneVBox = new VBox();
        scrollPane = new ScrollPane();
        PDFbtn = new Button("Generate PDF Album");
        this.setLayout();
    }

    /**
     * Sets up the layout of the album scene, overrides the setlayout method of scenebuilder
     */
    @Override
    public void setLayout() {
        super.setLayout();
        super.setGridPane();
        setupScrollPane();
        Css.setAlbumButtons(PDFbtn);
        PDFbtn.setOnAction(s ->generatePdfPressed());
        super.getGridPane().add(scrollPane, 0, 2);
        super.getGridPane().add(PDFbtn, 0, 4);
        super.getGridPane().setMaxWidth(700.0D);
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

        savelocation.setPromptText("Save location");
        Css.setTextFieldAlbums(savelocation);

        Button fileExplorer = new Button("Explore");
        Css.setAddAlbumButton(fileExplorer);
        fileExplorer.setOnAction(s ->{
            try {
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Explore");
                File defaultDirectory = new File("c:/");
                chooser.setInitialDirectory(defaultDirectory);
                selectedDirectory = chooser.showDialog(this.dialogWindow).getAbsolutePath();
                savelocation.setText(selectedDirectory);
            }catch (Exception e){
                savelocation.clear();
            }
        });

        Button downloadpdf = new Button("Download");
        Css.setAddAlbumButton(downloadpdf);
        downloadpdf.setMaxWidth(450);
        downloadpdf.setOnAction(e -> {
            generatePDF(savelocation.getText());
            savelocation.clear();
            dialogWindow.close();
        });

        dialogHBox.getChildren().addAll(savelocation, fileExplorer);
        dialogVbox.getChildren().addAll(dialogText, dialogHBox, downloadpdf);
    }

    /**
     * Creates the popup window that is shown when clicking the generate pdf button
     */
    private void createPopupDialog() {
        dialogWindow = new Stage();
        dialogWindow.initModality(Modality.APPLICATION_MODAL);

        dialogVbox = new VBox();
        dialogVbox.setAlignment(Pos.CENTER);

        dialogText = new Text();

        dialogHBox = new HBox();
        dialogHBox.setPadding(new Insets(10,10,10,10));
        dialogHBox.setSpacing(10);

        Scene dialogScene = new Scene(dialogVbox, 500, 150);
        dialogWindow.setScene(dialogScene);
        dialogWindow.show();
    }

    /**
     * Sets up the album scene with pagetitle and the album's photos in the scrollpane
     * @param album the album which will be shown with all the pictures in the album. If the album contains no pictures,
     *              a text will be shown in the scene to inform the user.
     */
    public void setup(Album album) {
        super.setPageTitle(album.getName());
       this.albumPhotoList = album.getAlbumPhotos();
       this.albumName = album.getName();

        if (albumPhotoList.size() > 0) {
            albumPhotoList.forEach(photo -> {
                PhotoContainer p = new PhotoContainer(photo);
                scrollPaneVBox.getChildren().add(p.getPhotoContainer());
            });
        } else {
            Text text = new Text("This album does not contain any photos yet. You can add more photos in \"Photos\"");
            Css.setTextAlbums(text);
            scrollPane.setContent(text);
        }
    }

    /**
     * Method to generate pdf and is ran when clicking download in generatepdf window
     * @param savelocation is the location that the user wanted the pdf saved to
     */
    public void generatePDF(String savelocation) {
        ArrayList<Photo> photos = new ArrayList<>();
        this.albumPhotoList.forEach(p -> photos.add(p));
        String savelink = savelocation + "/" + albumName + ".pdf";
        try {
            PDFcreator.createPDF(photos, savelink);
            File pdfFile = new File(savelink);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                }
            }
        } catch (Exception ignored){}
    }
}
