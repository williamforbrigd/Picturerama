package Scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LogIn extends SceneBuilder {
    private static TextField username;
    private static PasswordField password;
    private static Label logInFeedBack;
    private static Button logInButton;

    public LogIn() {
        super();
        username = new TextField();
        password = new PasswordField();
        logInFeedBack = new Label();
        logInButton = new Button("Log in");
        this.setLayout();
    }

    @Override
    public Scene getScene(){
       return super.getScene();
    }

    @Override
    public void setLayout(){
        super.setLayout();
        username.setPromptText("Username");
        password.setPromptText("Password");
        super.setPageTitle("Log in screen");
        super.getGridPane().add(username,0,0);
        super.getGridPane().add(password,0,1);
        super.getGridPane().add(logInButton,0,2);
        super.getGridPane().add(logInFeedBack,0,3);
        logInButton.setOnAction(e -> {
            //Fill in function
        });

    }


}
