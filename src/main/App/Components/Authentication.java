package Components;

import Database.Hibernate;
import Scenes.StageInitializer;

/**
 * Authentication class for authenticating username and password for each user
 */
public class Authentication {

	/**
	 * Register a new user
	 * It will encrypt the password before inserting into database
	 *
	 * @param username the username
	 * @param password the password
	 * @return if method was successful
	 */
	public static boolean register(String username, String password, String email) {
		try {
			//Encrypt password
			String encryptor = Encryptor.Encryptor(password, null);
			//get salt and hash
			String hash = Encryptor.getHash(encryptor);
			String salt = Encryptor.getSalt(encryptor);

			return Hibernate.registerUser(username, email, hash, salt);
		} catch (ExceptionInInitializerError | NoClassDefFoundError e) {
			throw e;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * logIn method is used when the user is trying to log in
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
			String encryptor = Encryptor.Encryptor(password, salt);
			String hash = Encryptor.getHash(encryptor);

			// Try to login
			if (Hibernate.login(username, hash)) {
				UserInfo.initializeUser(Hibernate.getUser(username));
				return true;
			} else {
				return false;
			}
		} catch (ExceptionInInitializerError | NoClassDefFoundError e) {
			throw e;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Log out the user and redirect to login scene
	 */
	public static void logout() {
		UserInfo.logOut();
		Hibernate.getEm().clear();
		StageInitializer.setLoginScene();
	}
}
