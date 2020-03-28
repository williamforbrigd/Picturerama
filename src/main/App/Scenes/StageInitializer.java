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

    /**
     * Initializes the stage
     * @param primaryStage is the stage used as the main stage through the whole application
     */
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

    /**
     * Gets the main stage of the application
     * @return stage, the main stage of the application
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Sets the scene of the stage as the login scene
     */
    public static void setLoginScene() {
        LogIn logIn = new LogIn();
        stage.setScene(logIn.getScene());
    }

    /**
     * Sets the scene of the stage as the sign up scene
     */
    public static void setSignUpScene() {
        SignUp signUp = new SignUp();
        stage.setScene(signUp.getScene());
    }

    /**
     * Sets the scene of the stage as the main menu scene
     */
    public static void setMainMenuScene() {
        MainMenu mainMenu = new MainMenu();
        stage.setScene(mainMenu.getScene());
    }

    /**
     * Sets the scene of the stage as the upload scene where the user can upload a picture
     */
    public static void setUploadScene() {
        UploadScene uploadScene = new UploadScene();
        stage.setScene(uploadScene.getScene());
    }

    /**
     * Sets the scene of the stage as the search scene where the user can search through its pictures
     */
    public static void setSearchScene() {
	    Search search = new Search();
        stage.setScene(search.getScene());
    }

    /**
     * Sets the scene of the stage as the albums scene.
     * The scene where the user can view it's albums, and create new ones
     */
	public static void setAlbumsScene() {
		Albums albums = new Albums();
		stage.setScene(albums.getScene());
	}

    /**
     * Sets the scene of the stage as the album scene.
     * The scene where the user can view a album, and generate a pdf of it
     */
    public static void setAlbumScene(Album album){
        AlbumScene albumScene = new AlbumScene();
        albumScene.setup(album);
        stage.setScene(albumScene.getScene());
    }

    /**
     * Sets the scene of the stage as the map scene.
     * The scene where the user can view a map with pointers of where the uploaded photos was photographed
     */
    public static void setMapScene(){
        MapScene mapScene = new MapScene();
        stage.setScene(mapScene.getScene());
    }

    /**
     * Gives a confirmation box with the choices of exiting or not.
     * If the user exits the program the connection to the database will be closed
     */
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
