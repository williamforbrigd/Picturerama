package Components;

import Css.Css;
import Main.ApplicationManager;
import javafx.scene.control.Button;

/**
 * Utility class that is used to create confirmations boxes
 */
public final class ConfirmationBox {

    private static boolean answer;

    /**
     * Private constructor to hinder the creation of the utility class
     */
    private ConfirmationBox() {
        throw new IllegalStateException("Utility class");
    }

	/**
	 * Creates the confirmation box and displays it
	 *
	 * @param title   the title of the conformation box
	 * @param message the massage that the confirmation box is going to display
	 * @return boolean answer of the confirmation box
	 */
	public static boolean display(String title, String message) {
		PopUpWindow dialogWindow = new PopUpWindow(ApplicationManager.getStage(), 250, 150);
		dialogWindow.getDialogWindow().close();

		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		yesButton.setOnAction(s -> {
			answer = true;
			dialogWindow.getDialogWindow().close();
		});
		noButton.setCancelButton(true);
		noButton.setOnAction(s -> {
			answer = false;
			dialogWindow.getDialogWindow().close();
		});

        Css.setButton(150,30,15,yesButton,noButton);

        dialogWindow.getDialogWindow().setTitle(title);
        dialogWindow.getDialogText().setText(message);
        dialogWindow.getDialogVBox().getChildren().addAll(yesButton,noButton);

        dialogWindow.getDialogWindow().showAndWait();

        return answer;
    }
}
