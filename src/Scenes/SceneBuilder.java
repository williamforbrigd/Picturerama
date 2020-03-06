package Scenes;

import Css.Css;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public abstract class SceneBuilder {
    //Making height and class constants to keep every scene the same size
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private Header header;

    //Creating class scene and grid pane
    private BorderPane borderPane;
    private GridPane gridPane;
    private Scene scene;

    /**
     * SceneBuilder constructor, used by it's subclasses
     */
    public SceneBuilder() {
        this.gridPane = new GridPane();
        this.setGridPane();
        this.borderPane = new BorderPane();
        this.scene = new Scene(borderPane, WIDTH, HEIGHT);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    /**
     * setGridPane void, sets padding and alignment for the grid pane
     */
    public void setGridPane() {
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
    }


    public void setLayout() {
        header = new Header();
        borderPane.setTop(Header.getGridPane());
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
