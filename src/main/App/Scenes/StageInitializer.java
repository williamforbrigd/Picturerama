package Scenes;

import Database.Hibernate;
import Database.HibernateClasses.Album;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class that initializes all the scenes in the application
 */
public class StageInitializer {
    private static Stage stage;

    /**
     * Private constructor to hinder creation of utility class
     */
    private StageInitializer(){
        throw new IllegalStateException("Can not make instance of utility class");
    }

    public static void initialize(Stage primaryStage) {
        stage = primaryStage;
        stage.setOnCloseRequest(s -> {
            //Makes is so that the program doesnt automatically close, gives the developer control of the exit mechanic
            s.consume();
            //Runs our close program instead
            closeProgram();
        });
        stage.getIcons().add(new Image("file:src/main/App/Images/Logo.png"));
        stage.setTitle("Picturerama");
        LogIn logIn = new LogIn();
        stage.setScene(logIn.getScene());
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setLoginScene() {
        LogIn logIn = new LogIn();
        stage.setScene(logIn.getScene());
    }

    public static void setSignUpScene() {
        SignUp signUp = new SignUp();
        stage.setScene(signUp.getScene());
    }

    public static void setMainMenuScene() {
        MainMenu mainMenu = new MainMenu();
        stage.setScene(mainMenu.getScene());
    }

    public static void setUploadScene() {
        UploadScene uploadScene = new UploadScene();
        stage.setScene(uploadScene.getScene());
    }

    public static void setSearchScene() {
	    Search search = new Search();
        stage.setScene(search.getScene());
    }

	public static void setAlbumsScene() {
		Albums albums = new Albums();
		stage.setScene(albums.getScene());
	}

    public static void setAlbumScene(Album album){
        AlbumScene albumScene = new AlbumScene();
        albumScene.setup(album);
        stage.setScene(albumScene.getScene());
    }

    //No access modifier to make it available in the package
    static void closeProgram(){
        boolean close = ConfirmationBox.display("Exit", "Are you sure you want to exit");
        if(close) {
            stage.close();
            Hibernate.getEm().clear();
            Hibernate.getEntityManagerFactory().close();
        }
    }

}
