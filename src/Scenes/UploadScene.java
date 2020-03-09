package Scenes;

import Css.Css;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;


public class UploadScene extends SceneBuilder {
    private static Label titleLabel = new Label("Title: ");
    private static TextField titleField = new TextField();
    private static Label urllabel = new Label("URL: ");
    private static TextField urlfield = new TextField();
    private static Button uploadButton = new Button("Upload image");
    private static Label feedbackLabel = new Label();

    public UploadScene() {
        super();
        this.setLayout();
    }

    @Override
    public void setLayout() {
        super.setLayout();
        super.setPageTitle("Upload");
        titleField.setPromptText("Title here...");
        urlfield.setPromptText("URL here...");
        super.getGridPane().add(titleLabel, 0, 0);
        super.getGridPane().add(titleField, 0, 1);
        super.getGridPane().add(urllabel, 0, 2);
        super.getGridPane().add(urlfield, 0, 3);
        super.getGridPane().add(uploadButton, 0, 4);
        super.getGridPane().add(feedbackLabel, 0, 5);
        Css.setButtonsSignUpLogin(uploadButton);

        uploadButton.setOnAction(event -> {
            if (checkField()) {
                System.out.println("Uploaded");
            }
        });
    }


    private boolean checkField() {
        if (titleField.getText().length() == 0 || urlfield.getText().length() == 0) {
            feedbackLabel.setText("Title or URL are missing");
            Css.setErrorLabel(feedbackLabel);
            return false;
        }
        try {
            Image imageTest = new Image(urlfield.getText());
            if (imageTest.isError()) {
                Css.setErrorLabel(feedbackLabel);
                feedbackLabel.setText("URL is invalid");
                return false;
            }
        } catch (Exception ex) {
            Css.setErrorLabel(feedbackLabel);
            feedbackLabel.setText("URL is invalid");
            return false;
        }
        return true;
    }
}
