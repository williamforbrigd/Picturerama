package Css;

public enum FeedBackType {
  Error("red"), Successful("green");

  private String color;

  FeedBackType(String color){
    this.color = color;
  }

  public String getColor(){
    return this.color;
  }
}
