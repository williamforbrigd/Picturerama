package Scenes;

import Components.PhotoContainer;
import Css.Css;
import Database.HibernateClasses.Album;
import Database.HibernateClasses.Photo;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.util.Set;

/**
 * Class for the album scene
 */
public class AlbumScene extends SceneBuilder {
    private VBox scrollPaneVBox;
    private ScrollPane scrollPane;

    /**
     * Album scene constructor, uses SceneBuilder constructor to create an object of the album scene class
     */
    public AlbumScene() {
        super();
        scrollPaneVBox = new VBox();
        scrollPane = new ScrollPane();
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
        super.getGridPane().add(scrollPane, 0, 2);
        super.getGridPane().setMaxWidth(700.0D);
    }

    /**
     * Sets up the scrollpane which will contain photos
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
     * Sets up the album scene with pagetitle and the album's photos in the scrollpane
     * @param album the album which should be shown
     */
    public void setup(Album album) {
        super.setPageTitle(album.getName());
        Set<Photo> albumPhotoList = album.getAlbumPhotos();

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
}
