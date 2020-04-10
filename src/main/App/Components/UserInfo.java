package Components;

import Database.HibernateClasses.User;

/**
 * UserInfo class includes information regarding the user running the program
 */
public class UserInfo {
  // Information about user - gets updated once a user logs in
  private static User user = null;

  /**
   * Private constructor to hinder the creation of the utility class
   */
  private UserInfo() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Gets the user object of the user that is logged inn
   *
   * @return the user object
   */
  public static User getUser() {
    return user;
  }

  /**
   * Method that runs on login, updates UserInfo.user variable
   *
   * @param dbUser takes the user object from the database in order to recognize the correct user
   */
  public static void initializeUser(User dbUser) {
      user = dbUser;
  }

  /**
   * Method that logs out the user from the program, by setting the userId to 0 and userName to null
   */
  public static void logOut() {
    user = null;
  }
}
