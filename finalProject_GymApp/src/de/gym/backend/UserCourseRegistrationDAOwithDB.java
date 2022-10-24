package de.gym.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * As a client of the gym (user role 3 - classic), I have the possibility to
 * attend the courses held by trainers. 
 * When the registration to the course is made, the attendance details are 
 * stored into the database.
 * 
 * @author florina
 *
 */
public class UserCourseRegistrationDAOwithDB implements UserCourseRegistrationDAO {

	/** URL for connecting to the database */
	public static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/gymapp_db";
	/** The database user on whose behalf the connection is being made */
	public static final String USER = "root";
	/** The user's password */
	public static final String PASSWORD = "";
	/** The table from/to which the data is retrieved/added */
	public static final String TABLE = "course_registration";
	/** User data source */
	private UserDAO userSource;
	/** Course data source */
	private CourseDAO courseSource;
	/** All courses and the users who attend these courses */
	private List<UserCourseRegistration> allUserCourseRegistrations = new ArrayList<UserCourseRegistration>();;

	/**
	 * Constructor initialization of user and course data sources
	 * 
	 * @param userSource
	 * @param courseSource
	 */
	public UserCourseRegistrationDAOwithDB(UserDAO userSource, CourseDAO courseSource) {
		this.userSource = userSource;
		this.courseSource = courseSource;
	}

	/**
	 * Retrieve information regarding all courses and the users who attend these
	 * courses.
	 */
	@Override
	public List<UserCourseRegistration> getAllCoursesRegistration() {

		try (Connection conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + TABLE);
			while (rs.next()) {
				int registrationID = rs.getInt(1);
				int courseID = rs.getInt(2);
				int userID = rs.getInt(3);
				// Get course object based on the courseID
				Course registeredCourse = courseSource.getAllCourses().stream()
						.filter(course -> course.getCourseID() == courseID).collect(Collectors.toList()).get(0);
				// Get the user object who registered for the course, based on his userID
				User registeredUser = userSource.getAllUsers().stream().filter(user -> user.getUserID() == userID)
						.collect(Collectors.toList()).get(0);
				UserCourseRegistration userCourse = new UserCourseRegistration(registeredCourse, registeredUser);
				userCourse.setRegistrationID(registrationID);
				allUserCourseRegistrations.add(userCourse);

			}
		} catch (SQLException dbException) {
			dbException.printStackTrace();
		}
		return allUserCourseRegistrations;
	}

	/**
	 * Insert new attendance to a specific course in the database
	 */
	@Override
	public void addCourseRegistration(UserCourseRegistration courseRegistration) {
		try (Connection conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) {
			String sqlQuery = "INSERT INTO " + TABLE + " VALUES (NULL,?,?)";
			PreparedStatement prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setInt(1, courseRegistration.getCourse().getCourseID());
			prepStmt.setInt(2, courseRegistration.getUser().getUserID());
			prepStmt.execute();
		} catch (SQLException dbException) {
			dbException.printStackTrace();
		}
	}

}
