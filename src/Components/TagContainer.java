package Components;

import Css.Css;
import java.util.Objects;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TagContainer {
  private Label tag;
  private Button deleteTag;
  private HBox container;

  public TagContainer(String tag) {
    this.tag = new Label(tag);
    this.deleteTag = new Button( "", new ImageView(new Image("file:src/Images/Close.png")));
    this.container = new HBox();
    this.setLayout();
  }

  private void setLayout(){
    container.getChildren().addAll(this.tag, deleteTag);
    Css.setPane(container);
    Css.setLabelButton(deleteTag);
  }

  public Button getDeleteTag() {
    return deleteTag;
  }

  public HBox getContainer(){
    return container;
  }

  public String getTagAsString(){
    return this.tag.getText();
  }
}
