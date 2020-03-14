package Scenes;

import Css.Css;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MainMenu extends SceneBuilder {

    private static Button uploadButton = new Button("Upload");
    private static Button myPhotosButton = new Button("Photos");
    private static Button albumsButton = new Button("Albums");
    private static Button logOutButton = new Button("Log out");
    private static Button aboutButton = new Button("About");
    private static Alert infoDialog = new Alert(AlertType.INFORMATION);

    public MainMenu() {
        super();
        this.setLayout();
    }


    /**
     * Sets the layout of the main menu. The setLayout()-method from SceneBuilder is overridden, but also
     * called in the method in order to modify the method.
     */
    @Override
    public void setLayout() {
        super.setLayout();
        super.setPageTitle("Main Menu");

        Css.setButtonsMainMenu(uploadButton, myPhotosButton, albumsButton, logOutButton);

        uploadButton.setOnAction(e -> StageInitializer.setUploadScene());
        myPhotosButton.setOnAction(e -> StageInitializer.setSearchScene());
	    albumsButton.setOnAction(e -> StageInitializer.setAlbumsScene());
        logOutButton.setOnAction(e -> System.out.println("Log out pressed"));
        aboutButton.setOnAction(e -> infoDialog.showAndWait());

        super.getGridPane().add(uploadButton,0,0);
        super.getGridPane().add(myPhotosButton, 1,0);
        super.getGridPane().add(albumsButton, 0,1);
        super.getGridPane().add(logOutButton, 1,1);

        super.getBorderPane().setBottom(aboutButton);
        BorderPane.setAlignment(aboutButton, Pos.BOTTOM_RIGHT);

        infoDialog.setTitle("Information dialog - About");
        infoDialog.setHeaderText("About the application Picturerama");
        infoDialog.setContentText("Created by: William, Diderik, Rokas, Martin, Olaf and Hermann. \n 2020");
    }

}
