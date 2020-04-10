package Css;

public enum FeedBackType {
  ERROR("red"), SUCCESSFUL("green");

  private String color;

  FeedBackType(String color){
    this.color = color;
  }

  public String getColor(){
    return this.color;
  }
}
