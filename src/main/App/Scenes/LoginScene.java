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
public final class LoginScene extends SceneBuilder {
  private final Label USERNAME_LABEL = new Label("Username:");
  private final TextField USERNAME_FIELD = new TextField();
  private final Label PASSWORD_LABEL = new Label("Password:");
  private final PasswordField PASSWORD_FIELD = new PasswordField();
  private final Label LOG_IN_LABEL = new Label();
  private final Button LOG_IN_BUTTON = new Button("Log in");
  private final Button SIGN_UP_BUTTON = new Button("Sign up");
  private final ProgressIndicator LOADING_ANIMATION = new ProgressIndicator();

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
    USERNAME_FIELD.setPromptText("Username");
    PASSWORD_FIELD.setPromptText("Password");
    super.setPageTitle("Log in");
    super.getGridPane().add(USERNAME_LABEL, 5, 0);
    super.getGridPane().add(USERNAME_FIELD, 5, 1);
    super.getGridPane().add(PASSWORD_LABEL, 5, 2);
    super.getGridPane().add(PASSWORD_FIELD, 5, 3);
    super.getGridPane().add(LOG_IN_BUTTON, 5, 4);
    super.getGridPane().add(LOADING_ANIMATION, 6, 4);
    super.getGridPane().add(SIGN_UP_BUTTON, 5, 5);
    super.getGridPane().add(LOG_IN_LABEL, 5, 6);

    //Sets styling for the layout components
    Css.setButton(700, 25, 20, LOG_IN_BUTTON, SIGN_UP_BUTTON);
    Css.setTextField(700, 20, 17, USERNAME_FIELD, PASSWORD_FIELD);
    Css.setLabel(13, USERNAME_LABEL, PASSWORD_LABEL);
    Css.setLoadingAnimation(LOADING_ANIMATION);

    //Sets functionality for the layout components
    LOG_IN_BUTTON.setOnAction(e -> login());
    SIGN_UP_BUTTON.setOnAction(e -> StageInitializer.setScene(new SignupScene()));

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
    LOADING_ANIMATION.setVisible(true);
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(e -> {
      try {
        if (Authentication.logIn(USERNAME_FIELD.getText(), PASSWORD_FIELD.getText())) {
          StageInitializer.setScene(new MenuScene());
        } else {
          Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Log in failed", 13, LOG_IN_LABEL, 6);
          LOADING_ANIMATION.setVisible(false);
        }
      } catch (ExceptionInInitializerError error) {
        Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Could not connect to database", 13, LOG_IN_LABEL, 6);
        LOADING_ANIMATION.setVisible(false);
        FileLogger.getLogger().log(Level.FINE, error.getMessage());
        FileLogger.closeHandler();
      }
    });
    pause.play();
  }
}
