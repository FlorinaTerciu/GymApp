package de.gym.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class implements the methods described in the interface. If the
 * connection to the database is successful, either all courses are returned or
 * a new course is added into the database.
 * 
 * @author florina
 *
 */

public class CourseDAOwithDB implements CourseDAO {

	public static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/gymapp_db";
	public static final String USER = "root";
	public static final String PASSWORD = "";

	public static final String TABLE = "course";
	private List<Course> allCourses = new ArrayList<Course>();;
	private EmployeeDAO employeeSource;

	/**
	 * Constructor initializing the data source for the user having the type trainer.
	 * @param employeeSource
	 */
	public CourseDAOwithDB(EmployeeDAO employeeSource) {
		this.employeeSource = employeeSource;
	}

	/**
	 * Get all available courses and the information 
	 * regarding the trainer which organizes them.
	 */
	@Override
	public List<Course> getAllCourses() {

		try (Connection conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + TABLE);
			while (rs.next()) {
				int courseID = rs.getInt(1);
				String name = rs.getString(2);
				String description = rs.getString(3);
				LocalDateTime courseDate = rs.getTimestamp(4).toLocalDateTime();
				int trainerID = rs.getInt(5);
				//find the trainer who organizes the course from all employees list.
				Employee trainer = employeeSource.getAllEmployees().stream()
						.filter(employee -> employee.getUserID() == trainerID).collect(Collectors.toList()).get(0);
				Course course = new Course(name, description, courseDate, trainer);
				course.setCourseID(courseID);
				allCourses.add(course);
			}
		} catch (SQLException dbException) {
			dbException.printStackTrace();
		}
		return allCourses;
	}

	/**
	 * A new course it is added to the database by trainer.
	 */
	@Override
	public void addCourse(Course course) {
		try (Connection conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) {
			String sqlQuery = "INSERT INTO " + TABLE + " VALUES (NULL,?,?,?,?)";
			PreparedStatement prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, course.getName());
			prepStmt.setString(2, course.getDescription());
			prepStmt.setTimestamp(3, Timestamp.valueOf(course.getCourseDate()));
			prepStmt.setInt(4, course.getTrainer().getUserID());
			prepStmt.execute();
		} catch (SQLException dbException) {
			dbException.printStackTrace();
		}
	}

}
