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
 * Class for the signup scene
 */
final class SignupScene extends SceneBuilder {
  private final Label USERNAME_LABEL = new Label("Username: ");
  private final Label EMAIL_LABEL = new Label("Email: ");
  private final Label PASSWORD_LABEL = new Label("Password: ");
  private final Label CONFIRM_PASSWORD_LABEL = new Label("Confirm Password: ");
  private final PasswordField PASSWORD_FIELD = new PasswordField();
  private final PasswordField CONFIRM_PASSWORD_FIELD = new PasswordField();
  private final TextField USERNAME_FIELD = new TextField();
  private final TextField EMAIL_FIELD = new TextField();
  private final Label SIGN_UP_FEEDBACK_LABEL = new Label();
  private final Button SIGN_UP_BUTTON = new Button("Sign up");
  private final Button LOG_IN_BUTTON = new Button("Log in");
  private final ProgressBar PASSWORD_STRENGTH_BAR = new ProgressBar(0);
  private final ProgressIndicator LOADING_ANIMATION = new ProgressIndicator();

  /**
   * Creates a object of the class Signup
   */
  SignupScene() {
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
  void setLayout() {
    super.setLayout();
    super.setPageTitle("Sign up");
    //Sets PromptText to TextFields
    USERNAME_FIELD.setPromptText("Username here...");
    EMAIL_FIELD.setPromptText("Email here...");
    PASSWORD_FIELD.setPromptText("Password here...");
    PASSWORD_FIELD.setTooltip(new Tooltip("Password has to be at least 8 characters long"));
    CONFIRM_PASSWORD_FIELD.setPromptText("Password here...");
    super.getGridPane().add(USERNAME_LABEL, 10, 0);
    super.getGridPane().add(EMAIL_LABEL, 10, 2);
    super.getGridPane().add(PASSWORD_LABEL, 10, 4);
    super.getGridPane().add(CONFIRM_PASSWORD_LABEL, 10, 6);
    super.getGridPane().add(USERNAME_FIELD, 10, 1);
    super.getGridPane().add(EMAIL_FIELD, 10, 3);
    super.getGridPane().add(PASSWORD_FIELD, 10, 5);
    super.getGridPane().add(CONFIRM_PASSWORD_FIELD, 10, 7);
    super.getGridPane().add(SIGN_UP_BUTTON, 10, 8);
    super.getGridPane().add(PASSWORD_STRENGTH_BAR, 11, 5);
    super.getGridPane().add(SIGN_UP_FEEDBACK_LABEL, 10, 11);
    super.getGridPane().add(LOG_IN_BUTTON, 10, 9);
    super.getGridPane().add(LOADING_ANIMATION, 11, 8);
    //Sets ToolTip to passwordStrengthBar, used when its being hovered
    PASSWORD_STRENGTH_BAR.setTooltip(new Tooltip("Password Stength: \n " +
        "Use 10 or more characters \n Use numbers \n Use capital letters"));
    PASSWORD_STRENGTH_BAR.setVisible(false);

    //Set styling on the layout components
    Css.setButton(700, 25, 20, SIGN_UP_BUTTON, LOG_IN_BUTTON);
    Css.setTextField(700, 20, 17, USERNAME_FIELD, EMAIL_FIELD, PASSWORD_FIELD, CONFIRM_PASSWORD_FIELD);
    Css.setLabel(13, USERNAME_LABEL, EMAIL_LABEL, PASSWORD_LABEL, CONFIRM_PASSWORD_LABEL);
    Css.setLoadingAnimation(LOADING_ANIMATION);

    SIGN_UP_BUTTON.setOnAction(e -> signup());
    PASSWORD_FIELD.setOnKeyTyped(e -> passwordStrengthBarEventHandling());

    LOG_IN_BUTTON.setOnAction(e -> StageInitializer.setScene(new LoginScene()));

    super.getScene().setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        signup();
      }
    });
  }

  private void signup() {
    LOADING_ANIMATION.setVisible(true);
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(e -> {
      try {
        if (feedback()) {
          if (Authentication.register(USERNAME_FIELD.getText(), PASSWORD_FIELD.getText(), EMAIL_FIELD.getText())) {
            StageInitializer.setScene(new LoginScene());
          } else {
            Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Error: This username is already taken", 13, SIGN_UP_FEEDBACK_LABEL, 6);
            LOADING_ANIMATION.setVisible(false);
          }
        } else {
          LOADING_ANIMATION.setVisible(false);
        }
      } catch (ExceptionInInitializerError error) {
        Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Error: Could not connect to database", 13, SIGN_UP_FEEDBACK_LABEL, 6);
        LOADING_ANIMATION.setVisible(false);
        FileLogger.getLogger().log(Level.FINE, error.getMessage());
        FileLogger.closeHandler();
      }
    });
    pause.play();
  }


  /**
   * A method that returns a percent for the password strength.
   * Password strength is defined by length, and if it contains a capital letter and digit.
   *
   * @return double that contains the percent of how strong the written password is
   */
  private double setPasswordStrength() {
    double passwordStrength = 0;
    boolean containsCapital = false;
    boolean containsDigit = false;
    //Checks if password length is greater than or equal to 10
    if (PASSWORD_FIELD.getText().length() >= 10) {
      passwordStrength += 0.25;
    }
    //Checks if password length is greater than or equal to 10
    if (PASSWORD_FIELD.getText().length() >= 12) {
      passwordStrength += 0.25;
    }
    for (int i = 0; i < PASSWORD_FIELD.getText().length(); i++) {
      //Checks if password contains a capital letter
      if (Character.isUpperCase(PASSWORD_FIELD.getText().charAt(i))) {
        containsCapital = true;
      }
      //Checks if password contains a digit
      if (Character.isDigit(PASSWORD_FIELD.getText().charAt(i))) {
        containsDigit = true;
      }
    }
    if (containsCapital) {
      passwordStrength += 0.25;
    }
    if (containsDigit) {
      passwordStrength += 0.25;
    }
    return passwordStrength;
  }

  /**
   * A method that controls the password strength bar. Uses CSS for changing color of the bar.
   * Changes from red to yellow to green, which define the strength.
   */
  private void passwordStrengthBarEventHandling() {
    if (PASSWORD_FIELD.getText().length() == 0) {
      PASSWORD_STRENGTH_BAR.setVisible(false);
    } else {
      PASSWORD_STRENGTH_BAR.setVisible(true);
    }

    PASSWORD_STRENGTH_BAR.setProgress(setPasswordStrength());
    //Sets color of passwordStrengthBar to red if strength is equal to 25%
    if (setPasswordStrength() == 0.25) {
      PASSWORD_STRENGTH_BAR.setStyle("-fx-accent: #E74C3C ");
    }
    //Sets color of passwordStrengthBar to yellow if strength is equal to 50%
    if (setPasswordStrength() == 0.5) {
      PASSWORD_STRENGTH_BAR.setStyle("-fx-accent: #F4D03F");
    }
    //Sets color of passwordStrengthBar to green if strength is equal to 75%
    if (setPasswordStrength() == 0.75) {
      PASSWORD_STRENGTH_BAR.setStyle("-fx-accent: #2ECC71 ");
    }
  }


  /**
   * Gives feedback on password and username and different error messages.
   * Password requirements: 8 characters or more, only digits and letters
   * Username requirements: Only digits and letters
   *
   * @return a boolean value, true for no errors, boolean for error
   */
  private boolean feedback() {
    //Checks if password, username or email trimmed is equal to 0
    if (PASSWORD_FIELD.getText().trim().length() == 0 || USERNAME_FIELD.getText().trim().length() == 0 || EMAIL_FIELD.getText().trim().length() == 0) {
      Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Error: Password, username or email are missing", 13, SIGN_UP_FEEDBACK_LABEL, 6);
      return false;
    }
    //Checks if password is shorter than 8 characters
    if (PASSWORD_FIELD.getText().length() < 8) {
      Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Error: Password needs to contain 8 characters", 13, SIGN_UP_FEEDBACK_LABEL, 6);
      return false;
    }
    //Checks username for illegal characters
    if (USERNAME_FIELD.getText().length() != 0) {
      for (int i = 0; i < USERNAME_FIELD.getText().length(); i++) {
        if (!Character.isLetter(USERNAME_FIELD.getText().charAt(i)) &&
            !Character.isDigit(USERNAME_FIELD.getText().charAt(i))) {
          Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Error: Username contains illegal character", 13, SIGN_UP_FEEDBACK_LABEL, 6);
          return false;
        }
      }
    }
    //Checks password for illegal
    if (PASSWORD_FIELD.getText().length() != 0) {
      for (int i = 0; i < PASSWORD_FIELD.getText().length(); i++) {
        if (!Character.isLetter(PASSWORD_FIELD.getText().charAt(i)) &&
            !Character.isDigit(PASSWORD_FIELD.getText().charAt(i))) {
          Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Error: Password contains illegal character", 13, SIGN_UP_FEEDBACK_LABEL, 6);
          return false;
        }
      }
    }
    //Checks confirmpassword and password match
    if (!PASSWORD_FIELD.getText().equals(CONFIRM_PASSWORD_FIELD.getText())) {
      Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Error: Your passwords don't match", 13, SIGN_UP_FEEDBACK_LABEL, 6);
      return false;
    }
    //Checks if email contains "@" and "."
    if (!EMAIL_FIELD.getText().contains("@") || !EMAIL_FIELD.getText().contains(".")) {
      Css.playFeedBackLabelTransition(FeedBackType.ERROR, "Error: This email is not valid", 13, SIGN_UP_FEEDBACK_LABEL, 6);
      return false;
    }

    Css.playFeedBackLabelTransition(FeedBackType.SUCCESSFUL, "You have been signed up!", 13, SIGN_UP_FEEDBACK_LABEL, 6);
    return true;
  }

}
