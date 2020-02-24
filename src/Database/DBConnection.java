/* This class has static methods so we wont have to make objects of this class to use its methods */
package Database;

import Components.UserInfo;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Class containing all the different methods which involves connection to the database
 */
public class DBConnection {
	/**
	 * Method which registers a new user
	 *
	 * @param userName username of the user
	 * @param hash     hashing of password
	 * @param salt     salting of password
	 */
	public static void registerUser(String userName, String hash, String salt) {
		Connection con = null;
		PreparedStatement prepStmt = null;
		try {
			con = HikariCP.getCon();

			String query = "INSERT INTO USERS VALUES (0, ?, ?, ?)";
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, userName);
			prepStmt.setString(2, hash);
			prepStmt.setString(3, salt);
			prepStmt.executeUpdate();
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, prepStmt, null);
		}
	}

	/**
	 * Gets the salt of a users password. Used for comparing passwords
	 *
	 * @param username of the user
	 * @return the salt of the password to the user
	 */
	public static String getSalt(String username) {
		Connection con = null;
		PreparedStatement prepStmt = null;
		ResultSet res = null;
		try {
			con = HikariCP.getCon();
			String query = "SELECT salt FROM USERS WHERE username=?;";
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, username);
			res = prepStmt.executeQuery();
			if (res.next()) {
				String salt = res.getString("salt");
				return salt;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, prepStmt, res);
		}
		return null;
	}

	/**
	 * General method for closing a connection
	 *
	 * @param con  Connection to ble closed
	 * @param stmt Statement to be closed
	 * @param res  ResultSet to be closed
	 */
	public static void closeConnection(Connection con, Statement stmt, ResultSet res) {
		try {
			if (res != null) {
				res.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method which checks if it exists a user with the given username and hashed password
	 *
	 * @param username the username
	 * @param hash     the hashed password
	 * @return boolean depending on the outcome
	 */
	public static boolean login(String username, String hash) {
		Connection con = null;
		PreparedStatement prepStmt = null;
		ResultSet res = null;
		try {
			con = HikariCP.getCon();
			String query = "SELECT username FROM USERS WHERE username=? AND password=?;";
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, username);
			prepStmt.setString(2, hash);
			res = prepStmt.executeQuery();
			return res.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, prepStmt, res);
		}
		return false;
	}

	/**
	 * Gets the userID of user
	 *
	 * @param username username of the user
	 * @return the userID of the user
	 */
	public static int getUserID(String username) {
		Connection con = null;
		PreparedStatement prepStmt = null;
		ResultSet res = null;
		try {
			con = HikariCP.getCon();
			String query = "SELECT id FROM USERS WHERE userName=?;";
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, username);
			res = prepStmt.executeQuery();
			if (res.next()) {
				return res.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, prepStmt, res);
		}
		return 0;
	}

	/**
	 * Register photo.
	 *
	 * @param title the title
	 * @param path  the path
	 */
	public static void registerPhoto(String title, String path) {
		Connection con = null;
		PreparedStatement prepStmt = null;
		try {
			con = HikariCP.getCon();

			String query = "INSERT INTO PHOTOS (title, path, user_id) VALUES (?, ?, ?)";
			prepStmt = con.prepareStatement(query);
			prepStmt.setString(1, title);
			prepStmt.setString(2, path);
			prepStmt.setInt(3, UserInfo.getUserID());
			prepStmt.executeUpdate();
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, prepStmt, null);
		}
	}

	/**
	 * Gets all photos of logged in user
	 *
	 * @return all photos as arraylist
	 */
	public static ArrayList<String> getPhotos() {
		Connection con = null;
		PreparedStatement prepStmt = null;
		ResultSet res = null;
		ArrayList<String> photos = new ArrayList<String>();
		try {
			con = HikariCP.getCon();
			String query = "SELECT * FROM PHOTOS WHERE user_id = ?;";
			prepStmt = con.prepareStatement(query);
			prepStmt.setInt(1, UserInfo.getUserID());
			res = prepStmt.executeQuery();
			while (res.next()) {
				int id = res.getInt("id");
				String title = res.getString("title");
				String path = res.getString("path");
				photos.add(id + ", " + title + ", " + path);
			}
			return photos;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, prepStmt, res);
		}
		return null;
	}

	/**
	 * Gets specific photo by photo id
	 *
	 * @param photo_id the photo id
	 * @return the photo
	 */
	public static String getPhoto(int photo_id) {
		Connection con = null;
		PreparedStatement prepStmt = null;
		ResultSet res = null;
		try {
			con = HikariCP.getCon();
			String query = "SELECT * FROM PHOTOS WHERE user_id = ? AND id = ?;";
			prepStmt = con.prepareStatement(query);
			prepStmt.setInt(1, UserInfo.getUserID());
			prepStmt.setInt(2, photo_id);
			res = prepStmt.executeQuery();
			if (res.next()) {
				int id = res.getInt("id");
				String title = res.getString("title");
				String path = res.getString("path");
				return id + ", " + title + ", " + path;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(con, prepStmt, res);
		}
		return null;
	}
}
