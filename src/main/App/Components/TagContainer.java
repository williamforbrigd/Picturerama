package Components;

import Css.Css;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Class that is used to display tags of photos
 */
public class TagContainer {
  private Label tag;
  private Button deleteTagButton;
  private HBox container;

  /**
   * Constructor that creates a tag container, containing a label with the tag, a button and the container itself
   * @param tag is the tag that is in the tag container
   */
  public TagContainer(String tag) {
    this.tag = new Label(tag);
    this.deleteTagButton = new Button( "", new ImageView(new Image("file:src/main/App/Images/Close.png")));
    this.container = new HBox();
    this.setLayout();
  }

  /**
   * Sets up the layout of the container
   */
  private void setLayout(){
    container.getChildren().addAll(this.tag, deleteTagButton);
    Css.setPane(container);
    container.setAlignment(Pos.CENTER);
    Css.setTagContainerButton(deleteTagButton);
  }

  public Button getDeleteTagButton() {
    return deleteTagButton;
  }

  public HBox getContainer(){
    return container;
  }

  public String getTagAsString(){
    return this.tag.getText();
  }
}
