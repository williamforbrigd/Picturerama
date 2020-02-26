package Main;

import Components.Authentication;
import Components.Photo;
import Components.UserInfo;
import Database.DBConnection;
import Scenes.LogIn;
import Scenes.StageInitializer;
import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {


    public static void main(String[] args) {
        /*System.out.println("Start");
//        Authentication.register("olaftest", "passord");
		System.out.println(Authentication.logIn("olaftest", "passord"));
		System.out.println(UserInfo.getUserID());
//	    DBConnection.registerPhoto("Superbilde", "https://nrk.no");
		ArrayList<Photo> photos = DBConnection.getPhotos();
		photos.stream().forEach(x -> System.out.println(x));
		System.out.println(DBConnection.getPhoto(5));*/
		launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		StageInitializer.initialize(stage);
	}
}
