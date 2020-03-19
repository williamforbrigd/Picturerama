package Scenes;

import Components.ImageAnalyzer;
import Components.UserInfo;
import Css.Css;
import Database.Hibernate;
import Database.HibernateClasses.Photo;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;


public class UploadScene extends SceneBuilder {
    private Label titleLabel = new Label("Title: ");
    private TextField titleField = new TextField();
    private Label urlLabel = new Label("URL: ");
    private TextField urlField = new TextField();
    private Button uploadButton = new Button("Upload image");
    private Label feedbackLabel = new Label();
    private ProgressIndicator loadingAnimation = new ProgressIndicator();

    /**
     * Constructor that sets up the layout of the upload scene
     */
    public UploadScene() {
        super();
        this.setLayout();
    }

    /**
     * Overrides SceneBuilder method.
     * Assigns layout components to SceneBuilders GridPane
     * Sets styling to layout components
     * Sets functionality to button nodes
     */
    @Override
    public void setLayout() {
        super.setLayout();
        super.setPageTitle("Upload");
        //Sets PromptText for TextFields
        titleField.setPromptText("Title here...");
        urlField.setPromptText("URL here...");
        super.getGridPane().add(titleLabel, 0, 0);
        super.getGridPane().add(titleField, 0, 1);
        super.getGridPane().add(urlLabel, 0, 2);
        super.getGridPane().add(urlField, 0, 3);
        super.getGridPane().add(uploadButton, 0, 4);
        super.getGridPane().add(loadingAnimation,1,4);
        super.getGridPane().add(feedbackLabel, 0, 5);
        super.getGridPane().setAlignment(Pos.TOP_CENTER);

        //Sets styling on layout components
        Css.setButtonsSignUpLogin(uploadButton);
        Css.setLabel(titleLabel, urlLabel);
        Css.setTextField(titleField,urlField);
        Css.setLoadingAnimation(loadingAnimation);

        uploadButton.setOnAction(e -> upLoadComplete());

        super.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                upLoadComplete();
            }

        });
    }

    /**
     * Checks if title or url are missing
     * @return boolean value, true if trimmed TextFields are equal to 0
     */
    private boolean checkField() {
        if (titleField.getText().trim().length() == 0 || urlField.getText().trim().length() == 0) {
            feedbackLabel.setText("Title or URL are missing");
            Css.setErrorLabel(feedbackLabel);
            return false;
        }
        return true;
    }

    /**
     * Upload the image path to the database
     * Sets feedbackLabel to error message if something went wrong
     */
    private void upLoadComplete() {
        loadingAnimation.setVisible(true);
        PauseTransition pause = new PauseTransition();
        pause.setOnFinished(e -> {
            if (checkField()) {
                try {
                    Photo photo = ImageAnalyzer.analyze(titleField.getText(), urlField.getText());
                    UserInfo.getUser().getPhotos().add(photo);
                    Hibernate.updateUser(UserInfo.getUser());
                    StageInitializer.setMainMenuScene();
                } catch (IOException ex) {
                    Css.setErrorLabel(feedbackLabel);
                    feedbackLabel.setText("Something went wrong when retrieving image from url.");
                } catch (NullPointerException ex) {
                    Css.setErrorLabel(feedbackLabel);
                    feedbackLabel.setText("Something went wrong when analyzing image.");
                }
            }
            else{
                loadingAnimation.setVisible(false);
            }
        });
        pause.play();
    }
}
