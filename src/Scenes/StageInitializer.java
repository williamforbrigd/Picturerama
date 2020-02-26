package Scenes;

import javafx.stage.Stage;

public class StageInitializer {
    public static Stage window;

    public static void initialize(Stage stage){
        window = stage;
        window.setOnCloseRequest(s -> {
            //Makes is so that the program doesnt automatically close, gives the developer control of the exit mechanic
            s.consume();
            //Runs our close program instead
            closeProgram();
        });

        LogIn logIn = new LogIn();
        window.setScene(logIn.getScene());
        window.show();
    }


    //No access modifier to make it available in the package
    static void closeProgram(){
        Boolean close = ConfirmationBox.display("Exit", "Are you sure you want to exit");
        if(close) window.close();
    }

}
