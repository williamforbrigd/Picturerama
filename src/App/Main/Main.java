package Main;

import Components.Album;
import Components.UserInfo;
import Database.DBConnection;
import Scenes.StageInitializer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StageInitializer.initialize(stage);
    }
}
