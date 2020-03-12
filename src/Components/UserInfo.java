package Components;

import Database.DBConnection;

/**
 * UserInfo class includes information regarding the user running the program
 */
public class UserInfo {
  // Information about user - gets updated once a user logs in
  private static String username = null;
  private static int userID = 0;


  /**
   * Private constructor to hinder the creation of the utility class
   */
  private UserInfo() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Gets the username of the user
   *
   * @return the username of the user
   */
  public static String getUserName() {
    return username;
  }

  /**
   * Gets the userID of the user
   *
   * @return the userID of the user
   */
  public static int getUserID() {
    return userID;
  }


  /**
   * Method that runs on login, updates UserInfo.userID variable
   * and fetches avatarID from given user
   *
   * @param userId takes the userID in order to recognize the correct user
   */
  public static void initializeUser(int userId, String userName) {
    userID = userId;
    username = userName;
  }
}
