package Scenes;

import Components.Authentication;
import Components.FileLogger;
import Css.Css;
import Css.FeedBackType;
import javafx.animation.PauseTransition;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.logging.Level;

/**
 * Class for the login scene
 */
public class LoginScene extends SceneBuilder {
  private Label usernameLabel = new Label("Username:");
  private TextField usernameField = new TextField();
  private Label passwordLabel = new Label("Password:");
  private PasswordField passwordField = new PasswordField();
  private Label logInLabel = new Label();
  private Button logInButton = new Button("Log in");
  private Button signUpButton = new Button("Sign up");
  private ProgressIndicator loadingAnimation = new ProgressIndicator();

  /**
   * Login constructor, uses SceneBuilder constructor. To create an object of the Login class
   */
  public LoginScene() {
    super();
    this.setLayout();
  }

  /**
   * SetLayout method used for setting the layout for the login page
   */
  @Override
  void setLayout() {
    //Uses the super classes set layout method for setting the base of the layout
    super.setLayout();
    usernameField.setPromptText("Username");
    passwordField.setPromptText("Password");
    super.setPageTitle("Log in");
    super.getGridPane().add(usernameLabel, 5, 0);
    super.getGridPane().add(usernameField, 5, 1);
    super.getGridPane().add(passwordLabel, 5, 2);
    super.getGridPane().add(passwordField, 5, 3);
    super.getGridPane().add(logInButton, 5, 4);
    super.getGridPane().add(loadingAnimation, 6, 4);
    super.getGridPane().add(signUpButton, 5, 5);
    super.getGridPane().add(logInLabel, 5, 6);

    //Sets styling for the layout components
    Css.setButton(700, 25, 20, logInButton, signUpButton);
    Css.setTextField(700, 20, 17, usernameField, passwordField);
    Css.setLabel(13, usernameLabel, passwordLabel);
    Css.setLoadingAnimation(loadingAnimation);

    //Sets functionality for the layout components
    logInButton.setOnAction(e -> login());
    signUpButton.setOnAction(e -> StageInitializer.setScene(new SignupScene()));

    super.getScene().setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        login();
      }
    });
  }

  /**
   * Method that is ran when logging in to the application
   */
  private void login() {
    loadingAnimation.setVisible(true);
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(e -> {
      try {
        if (Authentication.logIn(usernameField.getText(), passwordField.getText())) {
          StageInitializer.setScene(new MenuScene());
        } else {
          Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Log in failed", 13, logInLabel, 6);
          loadingAnimation.setVisible(false);
        }
      } catch (ExceptionInInitializerError error) {
        Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Could not connect to database", 13, logInLabel, 6);
        loadingAnimation.setVisible(false);
        FileLogger.getLogger().log(Level.FINE, error.getMessage());
        FileLogger.closeHandler();
      }
    });
    pause.play();
  }
}
