package Css;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

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
            arg.setStyle("-fx-padding: 3;-fx-border-style: solid inside;-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;-fx-border-color: black;" +
                "-fx-background-color: white;-fx-background-radius: 10px");
        }
    }

    public static void setLabelButton(Button... args){
        for(Button arg : args){
            arg.setMaxSize(10,10);
            arg.setStyle("-fx-background-color: none");
        }
    }

    /**
     * setButtons takes in an amount of buttons and sets their styling
     *
     * @param args an amount of buttons
     */
    public static void setButtonsSignUpLogin(Button... args) {
        for (Button button : args) {
            String style = "-fx-cursor: hand;-fx-border-style:solid inside;-fx-border-width: 1px;-fx-border-radius: 15px; " +
                    "-fx-border-color: #DDDDDD;-fx-background-color: white; " +
                    "-fx-background-radius: 15px;-fx-background-insets: 0";
            button.setStyle(style);
            button.setFont(Font.font("Montserrat", 20));
            button.setPrefHeight(25);
            button.setPrefWidth(700);
            button.setOnMouseEntered((e) -> {
                button.setStyle("-fx-cursor: hand;-fx-border-style: solid inside;-fx-border-width: 1px;" +
                        "-fx-border-radius: 15px;-fx-border-color: #DDDDDD;-fx-background-color: #00000022;" +
                        "-fx-background-radius: 15px;-fx-background-insets: 0");
            });
            button.setOnMouseExited((e) -> {
                button.setStyle(style);
            });
        }
    }

    public static void setImageContainerButtons(Button... args) {
        for (Button arg : args) {
            arg.setStyle("-fx-background-color: #FFFFFF;");
            arg.setOnMouseExited(action -> arg.setStyle("-fx-background-color: #FFFFFF;"));
            arg.setOnMouseEntered(action -> arg.setStyle("-fx-background-color: #82CBFF; -fx-border-color: #6BC1FF; -fx-border-width: 1.2;"));
            arg.setAlignment(Pos.CENTER_LEFT);
            arg.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
            arg.setMinHeight(160);
            arg.setMaxHeight(160);
        }
    }

    public static void setTextField(TextField... args){
        for(TextField textField: args){
            textField.setStyle("-fx-background-color: white;-fx-border-color: #656565;" +
                    "-fx-border-radius: 15px;-fx-background-radius: 15px");
            textField.setPrefHeight(20);
            textField.setPrefWidth(700);
            textField.setFont(Font.font("Montserrat", 17));
        }
    }

    public static void setButtonsMainMenu(Button... args) {
        for (Button button : args) {
            String style = "-fx-cursor: hand;-fx-border-style: solid inside;" +
                    "-fx-border-width: 1px;-fx-border-radius: 15px;" +
                    " -fx-border-color: #DDDDDD;-fx-background-color: white;" +
                    " -fx-background-radius: 15px;-fx-background-insets: 0";
            button.setStyle(style);
            button.setFont(new Font("Montserrat", 40.0D));
            button.setOnMouseEntered((e) -> {
                button.setStyle("-fx-cursor: hand;-fx-border-style: solid inside;-fx-border-width: 1px;-fx-border-radius: 15px;-fx-border-color: #DDDDDD;-fx-background-color: #00000022;-fx-background-radius: 15px;-fx-background-insets: 0");
            });
            button.setOnMouseExited((e) -> {
                button.setStyle(style);
            });
            button.setPrefHeight(100);
            button.setPrefWidth(466);
        }
    }

    public static void setErrorLabel(Label... args) {
        for (Label label : args) {
            label.setStyle("-fx-text-fill: red");
            label.setFont(Font.font("Montserrat", 13));
        }
    }

    public static void setTitleLabel(Label... args) {
        for (Label arg : args) {
            arg.setFont(Font.font("Montserrat", 30));
        }
    }

    public static void setParagraphLabel(Label... args) {
        for (Label arg : args) {
            arg.setFont(Font.font("Montserrat", 12));
        }
    }

    public static void setSuccessLabel(Label... args) {
        for (Label label : args) {
            label.setStyle("-fx-text-fill: green");
            label.setFont(Font.font("Montserrat",13));
        }
    }

    public static void setLabel(Label... args){
        for (Label label : args){
            label.setFont(Font.font("Montserrat", 13));
            label.setStyle("-fx-text-fill: #656565");
        }
    }

    public static void setHomeButton(Button button) {
        button.setStyle("-fx-background-color: none;");
        button.setOnMouseEntered((e) -> {
            button.setStyle("-fx-background-color: #00000022; -fx-cursor: hand");
        });
        button.setOnMouseExited((e) -> {
            button.setStyle("-fx-background-color: none");
        });
    }

    public static void setHeaderHBox(HBox headerHBox) {
        headerHBox.setStyle("-fx-padding: 10;-fx-border-style: solid inside;-fx-border-width: 1px;" +
                "-fx-border-radius: 15px;-fx-border-color: #DDDDDD;" +
                "-fx-background-color: white;-fx-background-radius: 15px");
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
