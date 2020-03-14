package Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The connection pool used for the game. HikariCP
 */
public class HikariCP {

	private static HikariDataSource ds;

	/**
	 * Configuration of the connection pool
	 */
	static {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://mysql.stud.ntnu.no:3306/olafros_picturerama");
		config.setUsername(readUsername());
		config.setPassword(readPassword());
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		ds = new HikariDataSource(config);
	}

	/**
	 * Gets a connection from the HikariCP connection pool
	 *
	 * @return Connection to the database
	 * @throws SQLException
	 */
	public static Connection getCon() throws SQLException {
		return ds.getConnection();
	}

	/**
	 * Reads a database username from a config.properties file
	 *
	 * @return Username in form of a String
	 */
	private static String readUsername() {
		try (InputStream input = new FileInputStream("config.properties")) {
			Properties prop = new Properties();
			prop.load(input);
			return prop.getProperty("username");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Reads a database password from a config.properties file
	 *
	 * @return Password in form of a String
	 */
	private static String readPassword() {
		try (InputStream input = new FileInputStream("config.properties")) {
			Properties prop = new Properties();
			prop.load(input);
			return prop.getProperty("password");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}


}
