package Scenes;

import Components.Authentication;
import Css.Css;
import javafx.scene.control.Button;

/**
 * Class for the menu scene
 */
public final class MenuScene extends SceneBuilder {

    private final Button UPLOAD_BUTTON = new Button("Upload");
    private final Button PHOTOS_BUTTON = new Button("Photos");
    private final Button ALBUMS_BUTTON = new Button("Albums");
    private final Button MAP_BUTTON = new Button("Map");
    private final Button LOG_OUT_BUTTON = new Button("Log out");

    public MenuScene() {
        super();
        this.setLayout();
    }

    /**
     * Sets the layout of the menu. The setLayout()-method from SceneBuilder is overridden, but also
     * called in the method in order to modify the method.
     */
    @Override
    void setLayout() {
        super.setLayout();
        super.setPageTitle("Menu");

        Css.setButton(340,100,40, UPLOAD_BUTTON, PHOTOS_BUTTON, ALBUMS_BUTTON, MAP_BUTTON, LOG_OUT_BUTTON);

        UPLOAD_BUTTON.setOnAction(e -> StageInitializer.setScene(new UploadScene()));
        PHOTOS_BUTTON.setOnAction(e -> StageInitializer.setScene(new PhotosScene()));
	    ALBUMS_BUTTON.setOnAction(e -> StageInitializer.setScene(new AlbumsScene()));
	    MAP_BUTTON.setOnAction(e -> StageInitializer.setScene(new MapScene()));
        LOG_OUT_BUTTON.setOnAction(e -> Authentication.logout());

        super.getGridPane().add(UPLOAD_BUTTON,0,0);
        super.getGridPane().add(PHOTOS_BUTTON, 1,0);
        super.getGridPane().add(ALBUMS_BUTTON, 0,1);
        super.getGridPane().add(MAP_BUTTON, 1,1);
        super.getGridPane().add(LOG_OUT_BUTTON, 0,2);
    }
}
