package Scenes;

import Components.UserInfo;
import Css.Css;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class that creates the header of the application
 */
public class Header {
    private static final Image logo = new Image("file:src/main/App/Images/Logo.png", 80, 80, true, true);
    private static final ImageView logoView = new ImageView(logo);
    private static final Label picturerama = new Label("Picturerama");
    private Label pageTitle = new Label();
    private static final Image homeIcon = new Image("file:src/main/App/Images/HomeIcon.png");
    private static Button homeButton = new Button("",new ImageView(homeIcon));
    private static HBox hBox = new HBox();

    /**
     * Constructor that sets up the header of the application
     */
    public Header() {
        if(UserInfo.getUser() != null) {
            homeButton.setOnAction(e -> Scenes.StageInitializer.setMainMenuScene());
        }else{
            homeButton.setOnAction(e -> Scenes.StageInitializer.setLoginScene());
        }
        hBox.getChildren().clear();
        this.setHBox();
    }

    public static Label getPicturerama() {
        return picturerama;
    }

    public static HBox getHBox() {
        return hBox;
    }

    public void setPageTitle(String newTitle) {
        this.pageTitle.setText("- "+newTitle);
    }

    /**
     * SetGridPane void, sets padding and alignment for the grid pane
     */
    private void setHBox() {
        hBox.setSpacing(10.0D);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(0, logoView);
        picturerama.setFont(Font.font("Montserrat", FontWeight.BOLD, 40.0D));
        this.pageTitle.setFont(Font.font("Montserrat", FontWeight.NORMAL, 40.0D));
        hBox.getChildren().add(picturerama);
        hBox.getChildren().add(this.pageTitle);
        Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);
        hBox.getChildren().add(spacing);
        Css.setHomeButton(homeButton);
        hBox.getChildren().add(homeButton);
        hBox.setMaxWidth(700.0D);
        hBox.setPrefHeight(100.0D);
        Css.setHeaderHBox(hBox);
    }
}
