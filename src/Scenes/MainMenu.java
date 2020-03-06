package Scenes;

import Css.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MainMenu extends SceneBuilder {

    private static BorderPane mainMenuBorderPane;
    private static GridPane mainMenuGridPane;

    private static Button uploadButton;
    private static Button myPhotosButton;
    private static Button mapButton;
    private static Button logOutButton;
    private static Button aboutButton;

    private static Alert infoDialog;

    public MainMenu() {
        super();

        uploadButton = new Button("Upload");
        myPhotosButton = new Button("My photos");
        mapButton = new Button("Map");
        logOutButton = new Button("Log out");
        aboutButton = new Button("About");

        mainMenuBorderPane = super.getBorderPane();
        mainMenuGridPane = super.getGridPane();

        infoDialog = new Alert(AlertType.INFORMATION);

        this.setLayout();
    }

    /**
     * getScene gets the scene of the main menu page, to be used in primaryStage.setScene()
     *
     * @return super.getScene(), the scene to be used in the primary stage
     */
    @Override
    public Scene getScene() {
        return super.getScene();
    }


    /**
     * Sets the layout of the main menu. The setLayout()-method from SceneBuilder is overridden, but also
     * called in the method in order to modify the method.
     */
    @Override
    public void setLayout() {
        super.setLayout();
        super.setPageTitle("- Main Menu");

        Css.setButtonsMainMenu(uploadButton, myPhotosButton, mapButton, logOutButton);

        uploadButton.setOnAction(e -> System.out.println("Upload pressed.."));
        myPhotosButton.setOnAction(e -> StageInitializer.setSearchScene());
        mapButton.setOnAction(e -> System.out.println("Edit pressed"));
        logOutButton.setOnAction(e -> System.out.println("Log out pressed"));
        aboutButton.setOnAction(e -> infoDialog.showAndWait());

        mainMenuGridPane.add(uploadButton,0,0);
        mainMenuGridPane.add(myPhotosButton, 1,0);
        mainMenuGridPane.add(mapButton, 0,1);
        mainMenuGridPane.add(logOutButton, 1,1);

        mainMenuBorderPane.setBottom(aboutButton);
        BorderPane.setAlignment(aboutButton, Pos.BOTTOM_RIGHT);

        infoDialog.setTitle("Information dialog - About");
        infoDialog.setHeaderText("About the application Picturerama");
        infoDialog.setContentText("Created by: William, Diderik, Rokas, Martin, Olaf and Herman. \n 2020");
    }

}
