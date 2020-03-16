package Scenes;

import Components.UserInfo;
import Css.Css;
import Database.Hibernate;
import Database.HibernateClasses.Photo;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;


public class UploadScene extends SceneBuilder {
    private Label titleLabel = new Label("Title: ");
    private TextField titleField = new TextField();
    private Label urlLabel = new Label("URL: ");
    private TextField urlField = new TextField();
    private Button uploadButton = new Button("Upload image");
    private Label feedbackLabel = new Label();

    public UploadScene() {
        super();
        this.setLayout();
    }

    @Override
    public void setLayout() {
        super.setLayout();
        super.setPageTitle("Upload");
        titleField.setPromptText("Title here...");
        urlField.setPromptText("URL here...");
        super.getGridPane().add(titleLabel, 0, 0);
        super.getGridPane().add(titleField, 0, 1);
        super.getGridPane().add(urlLabel, 0, 2);
        super.getGridPane().add(urlField, 0, 3);
        super.getGridPane().add(uploadButton, 0, 4);
        super.getGridPane().add(feedbackLabel, 0, 5);
        super.getGridPane().setAlignment(Pos.TOP_CENTER);
        Css.setButtonsSignUpLogin(uploadButton);
        Css.setLabel(titleLabel, urlLabel);
        Css.setTextField(titleField,urlField);

        uploadButton.setOnAction(event -> {
            if (checkField()) {
                upLoadComplete();
            }
        });

        super.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && checkField()) {
                upLoadComplete();
            }

        });
    }


    private boolean checkField() {
        feedbackLabel.setVisible(true);
        if (titleField.getText().length() == 0 || urlField.getText().length() == 0) {
            feedbackLabel.setText("Title or URL are missing");
            Css.setErrorLabel(feedbackLabel);
            return false;
        }
        try {
            Image imageTest = new Image(urlField.getText());
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
        Css.setSuccessLabel(feedbackLabel);
        feedbackLabel.setText("Image uploaded successfully");
        return true;
    }

    private void upLoadComplete(){
        Photo photo = new Photo();
        photo.setTitle(titleField.getText());
        photo.setUrl(urlField.getText());
        photo.setUserId(UserInfo.getUser().getId());
        UserInfo.getUser().getPhotos().add(photo);
        Hibernate.updateUser(UserInfo.getUser());
        StageInitializer.setMainMenuScene();
        titleField.clear();
        urlField.clear();
        feedbackLabel.setVisible(false);
    }
}
