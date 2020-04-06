package Components;

import Components.PopupWindow;
import javafx.scene.control.Button;
import Css.Css;

/**
 * Utility class that is used to create confirmations boxes
 */
public class ConfirmationBox {
    private static boolean answer;

    /**
     * Private constructor to hinder the creation of the utility class
     */
    private ConfirmationBox() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates thes the confirmation box and displays it
     * @param title the title of the conformation box
     * @param message the massage that the confirmation box is going to display
     * @return the answer of the confirmation box
     */
    public static boolean display(String title, String message){
        PopupWindow dialogWindow = new PopupWindow(250,150);
        dialogWindow.getDialogWindow().close();

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(s -> {
            answer = true;
            dialogWindow.getDialogWindow().close();
        });

        noButton.setOnAction(s -> {
            answer = false;
            dialogWindow.getDialogWindow().close();
        });

        Css.setButton(150,30,15,yesButton,noButton);

        dialogWindow.getDialogWindow().setTitle(title);
        dialogWindow.getDialogText().setText(message);
        dialogWindow.getDialogVbox().getChildren().addAll(yesButton,noButton);

        dialogWindow.getDialogWindow().showAndWait();

        return answer;
    }
}
