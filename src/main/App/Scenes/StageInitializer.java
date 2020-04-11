package Scenes;

import Components.ConfirmationBox;
import Database.Hibernate;
import Database.HibernateClasses.Album;
import javafx.geometry.Pos;
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
  private StageInitializer() {
    throw new IllegalStateException("Can not make instance of utility class");
  }

  /**
   * Initializes the stage
   *
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
    stage.setWidth(900);
    stage.setHeight(600);

    LoginScene loginScene = new LoginScene();
    stage.setScene(loginScene.getScene());
    stage.show();
  }

  /**
   * Gets the main stage of the application
   *
   * @return stage, the main stage of the application
   */
  public static Stage getStage() {
    return stage;
  }

  /**
   * Sets the scene of the stage
   *
   * @param scene an object of a subclass of the SceneBuilder class
   */
  public static void setScene(SceneBuilder scene) {
    stage.setScene(scene.getScene());
  }

  /**
   * Gives a confirmation box with the choices of exiting or not.
   * If the user exits the program the connection to the database will be closed
   */
  //No access modifier to make it available in the package
  private static void closeProgram() {
    boolean close = ConfirmationBox.display("Exit", "Are you sure you want to exit?");
    if (close) {
      stage.close();
      Hibernate.getEm().clear();
      Hibernate.getEntityManagerFactory().close();
    }
  }

}
