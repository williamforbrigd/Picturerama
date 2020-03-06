package Scenes;

import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageInitializer {
    private static Stage window;

    public static void initialize(Stage stage) {
        window = stage;
        window.setOnCloseRequest(s -> {
            //Makes is so that the program doesnt automatically close, gives the developer control of the exit mechanic
            s.consume();
            //Runs our close program instead
            closeProgram();
        });
        window.getIcons().add(new Image("file:src/Images/Logo.png"));
        window.setTitle("Picturerama");
        LogIn logIn = new LogIn();
        window.setScene(logIn.getScene());
        window.show();
    }

    public static Stage getWindow() {
        return window;
    }

    public static void setLoginScene() {
        LogIn logIn = new LogIn();
        window.setScene(logIn.getScene());
    }

    public static void setSignUpScene() {
        SignUp signUp = new SignUp();
        window.setScene(signUp.getScene());
    }

    //No access modifier to make it available in the package
    static void closeProgram(){
        boolean close = ConfirmationBox.display("Exit", "Are you sure you want to exit");
        if(close) window.close();
    }

}
