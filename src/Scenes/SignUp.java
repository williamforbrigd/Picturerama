package Scenes;

import Components.Authentication;
import Css.Css;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SignUp extends SceneBuilder {
    public static PasswordField passwordField;
    public static PasswordField confirmPasswordField;
    public static TextField usernameField;
    public static Text signupFeedbackText;
    public static Button signUpButton;
    public static Button logInButton;
    public static ProgressBar passwordStrengthBar;

    /**
     * Creates a object of the class SignUp
     * Extends from SceneBuilder
     */
    public SignUp() {
        super();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();
        usernameField = new TextField();
        signupFeedbackText = new Text();
        signUpButton = new Button("Sign up");
        logInButton = new Button("Log in");
        passwordStrengthBar = new ProgressBar(0);
        this.setLayout();
    }

    /**
     * Called from the superclass
     *
     * @return returns the scene to use in main
     */
    @Override
    public Scene getScene() {
        return super.getScene();
    }

    /**
     * Overrides the superclass' setLayout method. Also initialises all functionalities in the application
     */
    @Override
    public void setLayout() {
        super.setLayout();
        super.setPageTitle("Sign Up");
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        confirmPasswordField.setPromptText("Password");
        super.getGridPane().add(new Label("Username: "), 10, 0);
        super.getGridPane().add(new Label("Password"), 10, 2);
        super.getGridPane().add(new Label("Confirm Password"), 10, 4);
        super.getGridPane().add(usernameField, 10, 1);
        super.getGridPane().add(passwordField, 10, 3);
        super.getGridPane().add(confirmPasswordField, 10, 5);
        super.getGridPane().add(signUpButton, 10, 6);
        super.getGridPane().add(passwordStrengthBar, 11, 3);
        super.getGridPane().add(signupFeedbackText, 10, 8);
        super.getGridPane().add(logInButton, 10, 7);
        passwordStrengthBar.setTooltip(new Tooltip("Password Stength: \n " +
                "Use 10 or more characters \n Use numbers \n Use capital letters"));
        passwordStrengthBar.setVisible(false);
        Css.setButtons(signUpButton, logInButton);
        //Todo Use CSS class from GIT
        signUpButton.setOnAction(e -> {
            feedback();
            if (feedback()) {
                if (Authentication.register(usernameField.getText(), passwordField.getText())) {
                    StageInitializer.setLoginScene();
                }
            }
        });
        passwordField.setOnKeyTyped(e -> passwordStrengthBarEventHandling());
    }


    /**
     * A method that returns a percent for the password strength. Password strength is defined by length, if it contains a capital letter and digit.
     *
     * @return double that contains the percent of how strong the written password is
     */
    private double setPasswordStrength() {
        double passwordStrength = 0;
        boolean containsCapital = false;
        boolean containsDigit = false;
        if (passwordField.getText().length() >= 10) {
            passwordStrength += 0.25;
        }
        if (passwordField.getText().length() >= 12) {
            passwordStrength += 0.25;
        }
        for (int i = 0; i < passwordField.getText().length(); i++) {
            if (Character.isUpperCase(passwordField.getText().charAt(i))) {
                containsCapital = true;
            }
            if (Character.isDigit(passwordField.getText().charAt(i))) {
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
        if (passwordField.getText().length() == 0) {
            passwordStrengthBar.setVisible(false);
        } else {
            passwordStrengthBar.setVisible(true);
        }

        passwordStrengthBar.setProgress(setPasswordStrength());
        if (setPasswordStrength() == 0.25) {
            passwordStrengthBar.setStyle("-fx-accent: #E74C3C ");
        }

        if (setPasswordStrength() == 0.5) {
            passwordStrengthBar.setStyle("-fx-accent: #F4D03F");
        }

        if (setPasswordStrength() == 0.75) {
            passwordStrengthBar.setStyle("-fx-accent: #2ECC71 ");
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
        if (passwordField.getText().length() == 0 || usernameField.getText().length() == 0) {
            signupFeedbackText.setText("Error: Password or username are missing");
            signupFeedbackText.setFill(Color.RED);
            return false;
        }
        if (passwordField.getText().length() < 8) {
            signupFeedbackText.setText("Error: Password needs to contain 8 characters");
            signupFeedbackText.setFill(Color.RED);
            return false;
        }
        if (usernameField.getText().length() != 0) {
            for (int i = 0; i < usernameField.getText().length(); i++) {
                if (!Character.isLetter(usernameField.getText().charAt(i)) &&
                        !Character.isDigit(usernameField.getText().charAt(i))) {
                    signupFeedbackText.setText("Error: Username contains illegal character");
                    signupFeedbackText.setFill(Color.RED);
                    return false;
                }
            }
        }
        if (passwordField.getText().length() != 0) {
            for (int i = 0; i < passwordField.getText().length(); i++) {
                if (!Character.isLetter(passwordField.getText().charAt(i)) &&
                        !Character.isDigit(passwordField.getText().charAt(i))) {
                    signupFeedbackText.setText("Error: Password contains illegal character");
                    signupFeedbackText.setFill(Color.RED);
                    return false;
                }
            }
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            signupFeedbackText.setText("Error: Your passwords don't match");
            signupFeedbackText.setFill(Color.RED);
            return false;
        }

        signupFeedbackText.setText("You have been signed up!");
        signupFeedbackText.setFill(Color.GREEN);
        return true;
    }

}
