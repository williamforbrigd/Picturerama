package Css;

/**
 * Enum FeedBackType is the type of feed back the user receives
 * The class is used inn css methods to obtain a color based on the feedback type.
 * ERROR gives a red color
 * SUCCESSFUL gives a green color
 */
public enum FeedBackType {
  ERROR("red"), SUCCESSFUL("green");

  private String color;

  FeedBackType(String color){
    this.color = color;
  }

  String getColor(){
    return this.color;
  }
}
