package Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Class that has the basic structure of the application and that all other scenes extends
 */
public abstract class SceneBuilder {
    //Making height and class constants to keep every scene the same size
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private Header header;

    //Creating class scene and grid pane
    private BorderPane borderPane = new BorderPane();
    private GridPane gridPane = new GridPane();
    private Scene scene;

    /**
     * SceneBuilder constructor, used by it's subclasses
     */
    public SceneBuilder() {
        this.setGridPane();
        this.scene = new Scene(borderPane, WIDTH, HEIGHT);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    /**
     * SetGridPane void, sets padding and alignment for the grid pane
     */
    public void setGridPane() {
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
    }

    /**
     * Sets the basic layout of the application
     */
    public void setLayout() {
        header = new Header();
        BorderPane.setAlignment(Header.getHBox(), Pos.CENTER);
        BorderPane.setMargin(Header.getHBox(), new Insets(10.0D, 10.0D, 10.0D, 10.0D));
        borderPane.setTop(Header.getHBox());
        borderPane.setCenter(gridPane);
        //Css.setBorderPane(borderPane);
        //Css.setGridPane(gridPane);
    }

    public void setPageTitle(String title) {
        header.setPageTitle(title);
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Scene getScene() {
        return scene;
    }
}
