package Components;

import Database.DBConnection;

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
		String hash;
		String salt;

		if (password != null) {
			//Encrypt password
			String encryptor = Encryptor.Encryptor(password, null);
			//get salt and hash
			hash = Encryptor.getHash(encryptor);
			salt = Encryptor.getSalt(encryptor);
		} else {
			hash = null;
			salt = null;
		}

		if ((username != null) && (hash != null) && (salt != null)) {
			return DBConnection.registerUser(username, email, hash, salt);
		}
		return false;
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
		//Getting salt from db using username
		String salt = DBConnection.getSalt(username);
		//generating hash using salt
		String encryptor = Encryptor.Encryptor(password, salt);
		String hash = Encryptor.getHash(encryptor);


		// Try to login
		if (DBConnection.login(username, hash)) {
			UserInfo.initializeUser(DBConnection.getUserID(username), username);
			return true;
		} else {
			// Wrong username or password error
			return false;
		}
	}
}
