package Scenes;

import Components.Authentication;
import Css.Css;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;


public class LogIn extends SceneBuilder {

    private Label usernameLabel;
    private TextField usernameField;
    private Label passwordLabel;
    private PasswordField passwordField;
    private Label logInLabel;
    private Button logInButton;
    private Button signUpButton;

    /**
     * LogIn constructor, uses SceneBuilder constructor. To create an object of the LogIn class
     */
    public LogIn() {
        super();
        usernameLabel = new Label("Username:");
        usernameField = new TextField();
        passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        logInLabel = new Label();
        logInButton = new Button("Log in");
        signUpButton = new Button("Sign up");
        this.setLayout();
    }

    /**
     * setLayout method used for setting the layout for the log in page
     */
    @Override
    public void setLayout() {
        //Uses the super classes set layout method for setting the base of the layout
        super.setLayout();
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        super.setPageTitle("Log in");
        super.getGridPane().add(usernameLabel, 0, 0);
        super.getGridPane().add(usernameField, 0, 1);
        super.getGridPane().add(passwordLabel, 0, 2);
        super.getGridPane().add(passwordField, 0, 3);
        super.getGridPane().add(logInButton, 0, 4);
        super.getGridPane().add(signUpButton, 0, 5);
        super.getGridPane().add(logInLabel, 0, 6);

        //Sets styling for the layout components
        Css.setButtonsSignUpLogin(logInButton, signUpButton);
        Css.setTextField(usernameField,passwordField);
        Css.setLabel(usernameLabel,passwordLabel,logInLabel);

        //Sets functionality for the layout components
        logInButton.setOnAction(e -> login());
        signUpButton.setOnAction(e -> StageInitializer.setSignUpScene());

        super.getScene().setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                login();
            }
        });
    }

    private void login() {
        try {
            if (Authentication.logIn(usernameField.getText(), passwordField.getText())) {
                StageInitializer.setMainMenuScene();
            } else {
                logInLabel.setText("Log in failed");
            }
        } catch (ExceptionInInitializerError error) {
            logInLabel.setText("Could not connect to database");
        }
    }
}
