package Css;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

    /**
     * Method to style lable buttons
     * @param args Is multiple buttons
     */
    public static void setLabelButton(Button... args){
        for(Button arg : args){
            arg.setMaxSize(10,10);
            arg.setStyle("-fx-background-color: none");
        }
    }

    /**
     * setButtons style for ImageMetaDataViewer class takes in an amount of buttons and sets their styling
     *
     * @param args an amount of buttons
     */
    public static void setButtonsImageMetaDataViewer(Button... args) {
        for (Button button : args) {
            String style = "-fx-cursor: hand;-fx-border-style:solid inside;-fx-border-width: 1px;-fx-border-radius: 15px; " +
                "-fx-border-color: #DDDDDD;-fx-background-color: white; " +
                "-fx-background-radius: 15px;-fx-background-insets: 0";
            button.setStyle(style);
            button.setFont(Font.font("Montserrat", 20));
            button.setPrefHeight(25);
            button.setPrefWidth(570);
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

    /**
     * setButtons style for tags in tag container takes in an amount of buttons and sets their styling
     *
     * @param args an amount of buttons
     */
    public static void setButtonsAddTag(Button... args) {
        for (Button button : args) {
            String style = "-fx-cursor: hand;-fx-border-style:solid inside;-fx-border-width: 1px;-fx-border-radius: 15px; " +
                "-fx-border-color: #DDDDDD;-fx-background-color: white; " +
                "-fx-background-radius: 15px;-fx-background-insets: 0";
            button.setStyle(style);
            button.setFont(Font.font("Montserrat", 15));
            button.setPrefHeight(25);
            button.setPrefWidth(200);
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

    /**
     * setButtons style on buttons for sign up and login scene takes in an amount of buttons and sets their styling
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

    /**
     * SetButtons style in image container class for a amount of buttons
     * @param args a amount of buttons
     */
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

    /**
     * sets Textfield syling for a amount o textfield
     * @param args A amount of textfields
     */
    public static void setTextField(TextField... args){
        for(TextField textField: args){
            textField.setStyle("-fx-background-color: white;-fx-border-color: #656565;" +
                    "-fx-border-radius: 15px;-fx-background-radius: 15px");
            textField.setPrefHeight(20);
            textField.setPrefWidth(700);
            textField.setFont(Font.font("Montserrat", 17));
        }
    }

    /**
     * Sets Text fields styling for add tags
     * @param args A amount of text fields
     */
    public static void setTextFieldAddTag(TextField... args){
        for(TextField textField: args){
            textField.setStyle("-fx-background-color: white;-fx-border-color: #656565;" +
                "-fx-border-radius: 15px;-fx-background-radius: 15px");
            textField.setPrefHeight(10);
            textField.setPrefWidth(150);
            textField.setMaxWidth(150);
            textField.setFont(Font.font("Montserrat", 14));
        }
    }

    /**
     * Sets styling for main menu buttons
     * @param args a amount of buttons
     */
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
            button.setPrefWidth(340);
        }
    }

    /**
     * Set styling for album buttons
     * @param button a amount of buttons
     */
    public static void setAddAlbumButton(Button button) {
        String style = "-fx-cursor: hand;-fx-border-style: solid inside;" +
                "-fx-border-width: 1px;-fx-border-radius: 15px;" +
                " -fx-border-color: #DDDDDD;-fx-background-color: white;" +
                " -fx-background-radius: 15px;-fx-background-insets: 0";
        button.setStyle(style);
        button.setFont(new Font("Montserrat", 17.0D));
        button.setOnMouseEntered((e) -> {
            button.setStyle("-fx-cursor: hand;-fx-border-style: solid inside;-fx-border-width: 1px;-fx-border-radius: 15px;-fx-border-color: #DDDDDD;-fx-background-color: #00000022;-fx-background-radius: 15px;-fx-background-insets: 0");
        });
        button.setOnMouseExited((e) -> {
            button.setStyle(style);
        });
        button.setPrefHeight(20);
        button.setPrefWidth(500);
    }

    /**
     * Sets styling for add album buttons
     * @param args A amount of buttons
     */
    public static void setButtonsAlbumScene(Button... args) {
        for (Button arg : args) {
            String style = "-fx-cursor: hand;-fx-border-style: solid inside;" +
                    "-fx-border-width: 1px;-fx-border-radius: 15px;" +
                    " -fx-border-color: #DDDDDD;-fx-background-color: white;" +
                    " -fx-background-radius: 15px;-fx-background-insets: 0";
            arg.setStyle(style);
            arg.setFont(new Font("Montserrat", 18.0D));
            arg.setOnMouseEntered((e) -> {
                arg.setStyle("-fx-cursor: hand;-fx-border-style: solid inside;-fx-border-width: 1px;-fx-border-radius: 15px;-fx-border-color: #DDDDDD;-fx-background-color: #00000022;-fx-background-radius: 15px;-fx-background-insets: 0");
            });
            arg.setOnMouseExited((e) -> {
                arg.setStyle(style);
            });
            arg.setPrefHeight(50);
            arg.setPrefWidth(200);
        }
    }

    /**
     * Sets styling for buttons in album scene
     * @param args A amount of buttons
     */
    public static void setAlbumButtons(Button... args) {
        for(Button arg : args) {
            String style = "-fx-cursor: hand;-fx-border-style: solid inside;" +
                    "-fx-border-width: 1px;-fx-border-radius: 15px;" +
                    " -fx-border-color: #DDDDDD;-fx-background-color: white;" +
                    " -fx-background-radius: 15px;-fx-background-insets: 0";
            arg.setStyle(style);
            arg.setFont(new Font("Montserrat", 18.0D));
            arg.setOnMouseEntered((e) -> {
                arg.setStyle("-fx-cursor: hand;-fx-border-style: solid inside;-fx-border-width: 1px;-fx-border-radius: 15px;-fx-border-color: #DDDDDD;-fx-background-color: #00000022;-fx-background-radius: 15px;-fx-background-insets: 0");
            });
            arg.setOnMouseExited((e) -> {
                arg.setStyle(style);
            });
            arg.setPrefHeight(50);
            arg.setPrefWidth(650);
        }
    }

    /**
     * Styles font of a text in albums
     * @param text A text
     */
    public static void setTextAlbums(Text text) {
        text.setFont(new Font("Montserrat", 17));
    }

    /**
     * Sets styling for the scroll pain in albums
     * @param scrollPane A scroll pane
     */
    public static void setAlbumScrollPaneBorder(ScrollPane scrollPane) {
        scrollPane.setStyle("-fx-background-color:transparent;");
    }

    /**
     * Sets styling for text fields in add album popup
     * @param textField A text field
     */
    public static void setTextFieldAlbums(TextField textField) {
        textField.setFont(new Font("Montserrat", 17));
        textField.setPrefHeight(20);
        textField.setPrefWidth(700);
        textField.setStyle("-fx-background-color: white;-fx-border-color: #656565;" +
                "-fx-border-radius: 15px;-fx-background-radius: 15px");
    }

    /**
     * Sets styling for choice box in add to album in search scene
     * @param choiceBox A choice box
     */
    public static void setChoiceBoxAlbums(ChoiceBox choiceBox) {
        choiceBox.setPrefHeight(20);
        choiceBox.setPrefWidth(700);
        choiceBox.setStyle("-fx-background-color: white;-fx-border-color: #656565;" +
                "-fx-border-radius: 15px;-fx-background-radius: 15px");
    }

    /**
     * Sets styling for a error label
     * @param args An amount of labels
     */
    public static void setErrorLabel(Label... args) {
        for (Label label : args) {
            label.setStyle("-fx-text-fill: red");
            label.setFont(Font.font("Montserrat", 13));
        }
    }
    /**
     * Styles font of a text in the Action popup class
     * @param text A text
     */
    public static void setActionPopupText(Text text) {
        text.setFont(new Font("Montserrat", 17));
    }

    /**
     * Sets styling for title labels
     * @param args An amount of Labels
     */
    public static void setTitleLabel(Label... args) {
        for (Label arg : args) {
            arg.setFont(Font.font("Montserrat", 30));
        }
    }

    /**
     * Sets styling for semi title labels
     * @param args An amount of labels
     */
    public static void setSemiTitleLabel(Label... args) {
        for (Label arg : args) {
            arg.setFont(Font.font("Montserrat", 15));
        }
    }

    /**
     * Sets styling for paragrah labels
     * @param args An amount of labels
     */
    public static void setParagraphLabel(Label... args) {
        for (Label arg : args) {
            arg.setFont(Font.font("Montserrat", 12));
        }
    }

    /**
     * Sets styling for success labels
     * @param args An amount of Labels
     */
    public static void setSuccessLabel(Label... args) {
        for (Label label : args) {
            label.setStyle("-fx-text-fill: green");
            label.setFont(Font.font("Montserrat",13));
        }
    }

    /**
     * Sets styling for labels
     * @param args An amount of labels
     */
    public static void setLabel(Label... args){
        for (Label label : args){
            label.setFont(Font.font("Montserrat", 13));
            label.setStyle("-fx-text-fill: #656565");
        }
    }

    /**
     * setLoadingAnimation styles all ProgressIndicator put as parameter
     * @param args
     */
    public static void setLoadingAnimation(ProgressIndicator... args){
        for(ProgressIndicator progressIndicator : args){
            progressIndicator.setStyle("-fx-progress-color: dimgrey; -fx-cursor: wait");
            progressIndicator.setMaxSize(30,30);
            progressIndicator.setVisible(false);
        }
    }

    /**
     * Sets home button styling in the header
     * @param button An button
     */
    public static void setHomeButton(Button button) {
        button.setStyle("-fx-background-color: none;");
        button.setOnMouseEntered((e) -> {
            button.setStyle("-fx-background-color: #00000022; -fx-cursor: hand");
        });
        button.setOnMouseExited((e) -> {
            button.setStyle("-fx-background-color: none");
        });
    }

    /**
     * Sets styling for the HBox in header
     * @param headerHBox A HBox
     */
    public static void setHeaderHBox(HBox headerHBox) {
        headerHBox.setStyle("-fx-padding: 10;-fx-border-style: solid inside;-fx-border-width: 1px;" +
                "-fx-border-radius: 15px;-fx-border-color: #DDDDDD;" +
                "-fx-background-color: white;-fx-background-radius: 15px");
    }

    /**
     * Sets styling for the stackpane in mapscene
     * @param stackPane a stackpane
     */
    public static void setMapPane(StackPane stackPane) {
        stackPane.setStyle("-fx-border-style: solid inside;-fx-border-radius: 15px;-fx-border-width: 1px;-fx-border-color: #DDDDDD;");
    }

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
