package Scenes;

import Components.FileLogger;
import Components.ImageAnalyzer;
import Components.UserInfo;
import Css.Css;
import Css.FeedBackType;
import Database.Hibernate;
import Database.HibernateClasses.Photo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Class for the upload scene
 */
class UploadScene extends SceneBuilder {
    private Label titleLabel = new Label("Title: ");
    private TextField titleField = new TextField();
    private Label urlLabel = new Label("URL: ");
    private TextField urlField = new TextField();
    private Button uploadButton = new Button("Upload image");
    private Label feedbackLabel = new Label();
    private ProgressIndicator loadingAnimation = new ProgressIndicator();
    private Button fileExplorer = new Button("Select local image");
    private String selectedDirectory;

    /**
     * Constructor that sets up the layout of the upload scene
     */
    UploadScene() {
        super();
        this.setLayout();
    }

    /**
     * Method that gets cloudinary properties
     * @return returns a map with the properties
     */
    private static Map getProperties() {
        Map result = new HashMap();
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            result.put( "cloud_name", prop.getProperty("cloudinary_cloud_name"));
            result.put( "api_key", prop.getProperty("cloudinary_api_key"));
            result.put( "api_secret", prop.getProperty("cloudinary_api_secret"));
        } catch (IOException ex) {
            FileLogger.getLogger().log(Level.FINE, ex.getMessage());
            FileLogger.closeHandler();
        }
        return result;
    }


    /**
     * Overrides SceneBuilder method.
     * Assigns layout components to SceneBuilders GridPane
     * Sets styling to layout components
     * Sets functionality to button nodes
     */
    @Override
    void setLayout() {
        super.setLayout();
        super.setPageTitle("Upload");
        //Sets PromptText for TextFields
        titleField.setPromptText("Title here...");
        urlField.setPromptText("URL here...");
        super.getGridPane().add(titleLabel, 5, 0);
        super.getGridPane().add(titleField, 5, 1);
        super.getGridPane().add(urlLabel, 5, 2);
        super.getGridPane().add(urlField, 5, 3);
        super.getGridPane().add(fileExplorer, 5, 4);
        super.getGridPane().add(uploadButton, 5, 5);
        super.getGridPane().add(loadingAnimation,6,5);
        super.getGridPane().add(feedbackLabel, 5, 6);
        super.getGridPane().setAlignment(Pos.TOP_CENTER);

        //Sets styling on layout components
        Css.setButton(700,25,20,uploadButton,fileExplorer);
        Css.setLabel(13,titleLabel, urlLabel);
        Css.setTextField(700,20,17,titleField,urlField);
        Css.setLoadingAnimation(loadingAnimation);

        uploadButton.setOnAction(e -> upLoadComplete());
        fileExplorer.setOnAction(e ->{
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Upload local image");
            File defaultDirectory = new File(System.getProperty("user.home"));
            chooser.setInitialDirectory(defaultDirectory);
            selectedDirectory = chooser.showOpenDialog(StageInitializer.getStage()).getAbsolutePath();
            urlField.setText(selectedDirectory);
        }catch (Exception exp){
            urlField.clear();
        }
    });

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
            Css.setFeedBackLabel(FeedBackType.ERROR,13,feedbackLabel);
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
                    String photo_url;
                    if(!urlField.getText().contains("https")) {
                        Cloudinary cloudinary = new Cloudinary(getProperties());
                        File file = new File(urlField.getText());
                        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                        photo_url =  uploadResult.get("url").toString();
                    }else {
                        photo_url = urlField.getText();
                    }
                    Photo photo = ImageAnalyzer.analyze(titleField.getText(), photo_url);
                    UserInfo.getUser().getPhotos().add(photo);
                    Hibernate.updateUser(UserInfo.getUser());
                    titleField.clear();
                    urlField.clear();
                    Css.setFeedBackLabel(FeedBackType.SUCCESSFUL,13,feedbackLabel);
                    feedbackLabel.setText(photo.getTitle() + " was stored");
                } catch (IOException ex) {
                    Css.setFeedBackLabel(FeedBackType.ERROR,13,feedbackLabel);
                    feedbackLabel.setText("Something went wrong when retrieving the image from the url.");
                    FileLogger.getLogger().log(Level.FINE,ex.getMessage());
                    FileLogger.closeHandler();
                } catch (NullPointerException ex) {
                    Css.setFeedBackLabel(FeedBackType.ERROR,13,feedbackLabel);
                    feedbackLabel.setText("Something went wrong when analyzing the image.");
                    FileLogger.getLogger().log(Level.FINE,ex.getMessage());
                    FileLogger.closeHandler();
                }
                finally {
                    loadingAnimation.setVisible(false);
                }
            }
            else{
                loadingAnimation.setVisible(false);
            }
        });
        pause.play();
    }
}
