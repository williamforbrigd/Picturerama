package Components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import Css.Css;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActionPopup {
    private Stage dialogWindow;
    private VBox dialogVbox;
    private HBox dialogHBox;
    private Text dialogText;

    public ActionPopup(double width, double hight){

        dialogWindow = new Stage();
        dialogWindow.initModality(Modality.APPLICATION_MODAL);
        dialogWindow.getIcons().add(new Image("file:src/main/App/Images/Logo.png"));

        dialogVbox = new VBox();
        dialogVbox.setAlignment(Pos.CENTER);

        dialogText = new Text();
        Css.setText(17,dialogText);

        dialogHBox = new HBox();
        dialogHBox.setPadding(new Insets(10,10,10,10));
        dialogHBox.setSpacing(10);

        dialogVbox.getChildren().addAll(dialogText, dialogHBox);
        Scene dialogScene = new Scene(dialogVbox, width, hight);
        dialogWindow.setScene(dialogScene);
        dialogWindow.show();
    }

    public Stage getDialogWindow() {
        return dialogWindow;
    }

    public VBox getDialogVbox() {
        return dialogVbox;
    }

    public HBox getDialogHBox() {
        return dialogHBox;
    }

    public Text getDialogText() {
        return dialogText;
    }

}
