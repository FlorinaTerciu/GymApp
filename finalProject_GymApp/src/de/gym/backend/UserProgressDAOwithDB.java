package de.gym.backend;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class implements the methods described in the interface. If the
 * connection to the database is successful, either all users progress are
 * returned or a new progress entry is added into the database.
 * 
 * @author florina
 *
 */
public class UserProgressDAOwithDB implements UserProgressDAO {

	/** URL for connecting to the database */
	public static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/gymapp_db";
	/** The database user on whose behalf the connection is being made */
	public static final String USER = "root";
	/** The user's password */
	public static final String PASSWORD = "";
	/** The table from/to which the data is retrieved/added */
	public static final String TABLE = "user_progress";
	/** user data source */
	private UserDAO userSource;
	/** All user progress */
	private List<UserProgress> allUserProgress = new ArrayList<UserProgress>();

	/**
	 * User data source initialization in the construct.
	 * 
	 * @param userSource
	 */
	public UserProgressDAOwithDB(UserDAO userSource) {
		this.userSource = userSource;
	}

	/**
	 * Retrieve all Users progress stored in the database
	 */
	@Override
	public List<UserProgress> getAllUserProgress() {

		try (Connection conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + TABLE);
			while (rs.next()) {
				int progressID = rs.getInt(1);
				int userID = rs.getInt(2);
				LocalDate date = rs.getDate(3).toLocalDate();
				double weight = rs.getDouble(4);
				User registeredUser = userSource.getAllUsers().stream().filter(user -> user.getUserID() == userID)
						.collect(Collectors.toList()).get(0);
				UserProgress userProgress = new UserProgress(registeredUser, date, weight);
				userProgress.setProgressID(progressID);
				allUserProgress.add(userProgress);
			}
		} catch (SQLException dbException) {
			dbException.printStackTrace();
		}

		return allUserProgress;
	}

	/**
	 * Add new entry regarding user progress
	 */
	@Override
	public void addProgressWeight(UserProgress userProgress) {
		try (Connection conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) {
			String sqlQuery = "INSERT INTO " + TABLE + " VALUES (NULL,?,?,?)";
			PreparedStatement prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setInt(1, userProgress.getUser().getUserID());
			prepStmt.setDate(2, Date.valueOf(userProgress.getDate()));
			prepStmt.setDouble(3, userProgress.getWeight());
			prepStmt.execute();
		} catch (SQLException dbException) {
			dbException.printStackTrace();
		}

	}

}
