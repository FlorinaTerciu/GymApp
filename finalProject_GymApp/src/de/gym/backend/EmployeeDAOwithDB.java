package de.gym.backend;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

/**
 * The class implements the methods described in the interface. If the
 * connection to the database is successful, either all employees are returned
 * or a new employee is added into the database.
 * 
 * @author florina
 *
 */
public class EmployeeDAOwithDB implements EmployeeDAO {

	/**URL for connecting to the database*/
	public static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/gymapp_db";
	/**The database user on whose behalf the connection is being made*/
	public static final String USER = "root";
	/**The user's password*/
	public static final String PASSWORD = "";
	/**The table from/to which the data is retrieved/added */
	public static final String TABLE = "user";
	/**Employee list */
	private List<Employee> allEmployees = new ArrayList<Employee>();

	/**
	 * Get all the employees from the database.
	 */
	@Override
	public List<Employee> getAllEmployees() {
		try (Connection conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + TABLE);
			while (rs.next()) {
				int userID = rs.getInt(1);
				String surname = rs.getString(2);
				String forename = rs.getString(3);
				String address = rs.getString(4);
				LocalDate birthday = rs.getDate(5).toLocalDate();
				String username = rs.getString(6);
				String password = rs.getString(7);
				String email = rs.getString(8);
				String IBAN = rs.getString(9);
				double salary = rs.getDouble(12);
				int vacationDays = rs.getInt(13);
				int workingHours = rs.getInt(14);
				int roleID = rs.getInt(15);
				Employee employee = new Employee(forename, surname, address, birthday, username, password, email, IBAN,
						salary, vacationDays, workingHours);
				employee.setUserID(userID);
				employee.setRoleID(roleID);
				allEmployees.add(employee);
			}
		} catch (SQLException dbException) {
			dbException.printStackTrace();
		}
		return allEmployees;
	}

	/**
	 * Add new employee to the database.
	 */
	@Override
	public void addEmployee(Employee employee) {
		try (Connection conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) {
			String sqlQuery = "INSERT INTO " + TABLE
					+ " (user_id, surname, forename, address, birthday, username, password, email, IBAN, salary, vacation_days, working_hours, role_id)"
					+ " VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, employee.getSurname());
			prepStmt.setString(2, employee.getForename());
			prepStmt.setString(3, employee.getAddress());
			prepStmt.setDate(4, Date.valueOf(employee.getBirthday()));
			prepStmt.setString(5, employee.getUsername());
			prepStmt.setString(6, passwordEncryptionSHA(employee.getPassword()));
			prepStmt.setString(7, employee.getEmail());
			prepStmt.setString(8, employee.getIBAN());
			prepStmt.setDouble(9, employee.getSalary());
			prepStmt.setInt(10, employee.getVacationDays());
			prepStmt.setInt(11, employee.getWorkingHours());
			prepStmt.setInt(12, employee.getRoleID());
			prepStmt.execute();

		} catch (SQLException dbException) {
			dbException.printStackTrace();
		}

	}
	/**
	 * SHA-256 password hash conversion
	 * 
	 * @param pass
	 * @return
	 */
	private String passwordEncryptionSHA(String pass) {
//		MessageDigest digest;
//		byte[] hash;
//		try {
//			//choose the message digest algorithm
//			digest = MessageDigest.getInstance("SHA-256");
//			//conversion of the String into bytes
//			hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
//			//return the hashed password as String
//			return new String(hash);
//		} catch (NoSuchAlgorithmException exception) {
//			exception.printStackTrace();
//		}
//		return null;
		
		 try{
		        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
		        final byte[] hash = digest.digest(pass.getBytes("UTF-8"));
		        final StringBuilder hexString = new StringBuilder();
		        for (int i = 0; i < hash.length; i++) {
		            final String hex = Integer.toHexString(0xff & hash[i]);
		            if(hex.length() == 1) 
		              hexString.append('0');
		            hexString.append(hex);
		        }
		        return hexString.toString();
		    } catch(Exception ex){
		       throw new RuntimeException(ex);
		    }
	}
}
