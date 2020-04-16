package Components;

import Database.Hibernate;
import Roots.LoginRoot;
import Main.ApplicationManager;

import java.util.logging.Level;

/**
 * Authentication class for authenticating username and password for each user
 */
public final class Authentication {

  /**
   * Private constructor to hinder creation of utility class
   */
  private Authentication() {
    throw new IllegalStateException("Can not make instance of utility class");
  }

  /**
   * Register a new user
   * It will encrypt the password before inserting into database
   *
   * @param username the username
   * @param password the password
   * @return if method was successful
   */
  public static boolean register(String username, String password) {
    try {
      //Encrypt password
      String encrypter = Encrypter.encrypt(password, null);
      //get salt and hash
      String hash = Encrypter.getHash(encrypter);
      String salt = Encrypter.getSalt(encrypter);

      return Hibernate.registerUser(username, hash, salt);
    } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
      FileLogger.getLogger().log(Level.FINE, e.getMessage());
      FileLogger.closeHandler();
      throw e;
    } catch (IllegalArgumentException e) {
      FileLogger.getLogger().log(Level.FINE, e.getMessage());
      FileLogger.closeHandler();
      return false;
    }
  }

  /**
   * LogIn method is used when the user is trying to log in
   * will compare password from db to the entered password
   *
   * @param username of user
   * @param password of user
   * @return if login was successful
   */
  public static boolean logIn(String username, String password) {
    try {
      //Getting salt from db using username
      String salt = Hibernate.getSalt(username);
      //generating hash using salt
      String encrypter = Encrypter.encrypt(password, salt);
      String hash = Encrypter.getHash(encrypter);

      // Try to login
      if (Hibernate.login(username, hash)) {
        UserInfo.initializeUser(Hibernate.getUser(username));
        return true;
      } else {
        return false;
      }
    } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
      FileLogger.getLogger().log(Level.FINE, e.getMessage());
      FileLogger.closeHandler();
      throw e;
    } catch (Exception e) {
      FileLogger.getLogger().log(Level.FINE, e.getMessage());
      FileLogger.closeHandler();
      return false;
    }
  }

  /**
   * Log out the user and redirect to login scene
   */
  public static void logout() {
    UserInfo.logOut();
    Hibernate.getEm().clear();
    ApplicationManager.setRoot(new LoginRoot());
  }
}
