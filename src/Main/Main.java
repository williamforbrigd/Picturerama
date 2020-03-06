package Main;

import Components.Authentication;
import Components.Photo;
import Components.UserInfo;
import Database.DBConnection;
import Scenes.LogIn;
import Scenes.StageInitializer;
import Scenes.SignUp;
import Scenes.SceneBuilder;
import Scenes.Search;
import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StageInitializer.initialize(stage);
    }
}
