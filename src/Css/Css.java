package Css;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * Css class used for styling different JavaFX elements
 */
public class Css {

    /**
     * set pane takes in an amount of panes or its subclasses (border pane, grid pane)
     *
     * @param args an amount of panes
     */
    public static void setPane(Pane... args) {
        for (Pane arg : args) {
            arg.setStyle("-fx-background-color: white");
        }
    }

    public static void setLabel(Label label) {
        label.setStyle("-fx-text-fill: aliceblue");
    }

    /**
     * setButtons takes in an amount of buttons and sets their styling
     *
     * @param args an amount of buttons
     */
    public static void setButtonsSignUpLogin(Button... args) {
        for (Button arg : args) {
            arg.setStyle("-fx-position:absolute;");
            arg.setPrefHeight(20);
            arg.setPrefWidth(400);
        }
    }

    public static void setImageContainerButtons(Button... args) {
        for (Button arg : args) {
            arg.setStyle("-fx-background-color: #FFFFFF;");
            arg.setOnMouseExited(action -> arg.setStyle("-fx-background-color: #FFFFFF;"));
            arg.setOnMouseEntered(action -> arg.setStyle("-fx-background-color: #82CBFF; -fx-border-color: #6BC1FF; -fx-border-width: 1.2;"));
            arg.setAlignment(Pos.CENTER_LEFT);
            arg.setMinWidth(1000);
            arg.setMinHeight(160);
            arg.setMaxHeight(160);
        }
    }

    public static void setButtonsMainMenu(Button... args) {
        for (Button arg : args) {
            arg.setStyle("-fx-position:absolute;");
            arg.setFont(new Font("Cambria", 30));
            arg.setPrefHeight(100);
            arg.setPrefWidth(466);
        }
    }

    public static void setErrorLabel(Label... args) {
        for (Label arg : args) {
            arg.setStyle("-fx-text-fill: red");
        }
    }

    public static void setSuccessLabel(Label... args) {
        for (Label label : args) {
            label.setStyle("-fx-text-fill: green");
        }
    }

    //TODO add more functionality to method

    /**
     * Sets the dark mode for the application
     *
     * @param args a number of objects that are of or extends the Pane class
     */
    public static void setDarkMode(Pane... args) {
        for (Pane arg : args) {
            arg.setStyle("-fx-background-color: #2d2d2d");
        }
    }
}
