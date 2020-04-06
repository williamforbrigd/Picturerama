package Scenes;

import Components.ConfirmationBox;
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
        LoginScene loginScene = new LoginScene();
        stage.setScene(loginScene.getScene());
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
        LoginScene loginScene = new LoginScene();
        stage.setScene(loginScene.getScene());
    }

    /**
     * Sets the scene of the stage as the sign up scene
     */
    static void setSignupScene() {
        SignupScene signupScene = new SignupScene();
        stage.setScene(signupScene.getScene());
    }

    /**
     * Sets the scene of the stage as the main menu scene
     */
    public static void setMenuScene() {
        MenuScene menuScene = new MenuScene();
        stage.setScene(menuScene.getScene());
    }

    /**
     * Sets the scene of the stage as the upload scene where the user can upload a picture
     */
    static void setUploadScene() {
        UploadScene uploadScene = new UploadScene();
        stage.setScene(uploadScene.getScene());
    }

    /**
     * Sets the scene of the stage as the search scene where the user can search through its pictures
     */
    static void setPhotosScene() {
	    PhotosScene photosScene = new PhotosScene();
        stage.setScene(photosScene.getScene());
    }

    /**
     * Sets the scene of the stage as the albums scene.
     * The scene where the user can view it's albums, and create new ones
     */
	static void setAlbumsScene() {
		AlbumsScene albumsScene = new AlbumsScene();
		stage.setScene(albumsScene.getScene());
	}

    /**
     * Sets the scene of the stage as the album scene.
     * The scene where the user can view a album, and generate a pdf of it
     */
    static void setAlbumDetailsScene(Album album){
        AlbumDetailsScene albumDetailsScene = new AlbumDetailsScene();
        albumDetailsScene.setup(album);
        stage.setScene(albumDetailsScene.getScene());
    }

    /**
     * Sets the scene of the stage as the map scene.
     * The scene where the user can view a map with pointers of where the uploaded photos was photographed
     */
    static void setMapScene(){
        MapScene mapScene = new MapScene();
        stage.setScene(mapScene.getScene());
    }

    /**
     * Gives a confirmation box with the choices of exiting or not.
     * If the user exits the program the connection to the database will be closed
     */
    //No access modifier to make it available in the package
    private static void closeProgram(){
        boolean close = ConfirmationBox.display("Exit", "Are you sure you want to exit");
        if(close) {
            stage.close();
            Hibernate.getEm().clear();
            Hibernate.getEntityManagerFactory().close();
        }
    }

}
