package Scenes;

import Components.Authentication;
import Css.Css;
import Main.Main;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LogIn extends SceneBuilder {

    private static Label usernameLabel;
    private static TextField usernameField;
    private static Label passwordLabel;
    private static PasswordField passwordField;
    private static Label logInLabel;
    private static Button logInButton;
    private static Button signUpButton;
    private static Button exitButton; //TODO Maybe remove this button

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
        exitButton = new Button("Exit application");
        this.setLayout();
    }


    /**
     * getScene gets the scene of the log in page, to be used in primaryStage.setScene()
     *
     * @return super.getScene(), the scene to be used in the primary stage
     */
    @Override
    public Scene getScene() {
        return super.getScene();
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
        super.setPageTitle("Log in screen");
        super.getGridPane().add(usernameLabel, 0, 0);
        super.getGridPane().add(usernameField, 0, 1);
        super.getGridPane().add(passwordLabel, 0, 2);
        super.getGridPane().add(passwordField, 0, 3);
        super.getGridPane().add(logInButton, 0, 4);
        super.getGridPane().add(signUpButton, 0, 5);
        super.getGridPane().add(logInLabel, 0, 6);
        super.getGridPane().add(exitButton, 0, 7);

        //Sets styling for the layout components
        Css.setButtons(logInButton, signUpButton, exitButton);

        //Sets functionality for the layout components
        logInButton.setOnAction(e -> {
            if (Authentication.logIn(usernameField.getText(), passwordField.getText())) {
                //TODO switch scene
                logInLabel.setText("Successful");
            } else {
                logInLabel.setText("Log in failed");
            }
        });
        signUpButton.setOnAction(e -> {
            StageInitializer.setSignUpScene();
        });
        exitButton.setOnAction(e -> {
            StageInitializer.closeProgram();
        });

    }


}
