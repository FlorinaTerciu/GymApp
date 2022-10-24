package de.gym.backend;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * The class implements the methods described in the interface. If the
 * connection to the database is successful, either all users are returned or a
 * new user is added into the database.
 * 
 * @author florina
 *
 */
public class UserDAOwithDB implements UserDAO {

	/** URL for connecting to the database */
	public static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/gymapp_db";
	/** The database user on whose behalf the connection is being made */
	public static final String USER = "root";
	/** The user's password */
	public static final String PASSWORD = "";
	/** The table from/to which the data is retrieved/added */
	public static final String TABLE = "user";
	/** All users stored into the database */
	private List<User> allUsers = new ArrayList<User>();

	/**
	 * Retrieve all the users stored into the database.
	 */
	@Override
	public List<User> getAllUsers() {
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
				double weight = rs.getDouble(10);
				int height = rs.getInt(11);
				int roleID = rs.getInt(15);
				User user = new User(forename, surname, address, birthday, username, password, email, IBAN, weight,
						height);
				user.setUserID(userID);
				user.setRoleID(roleID);
				allUsers.add(user);
			}
		} catch (SQLException dbException) {
			dbException.printStackTrace();
		}
		return allUsers;
	}

	/**
	 * Add new user to the database.
	 */
	@Override
	public void addUser(User user) {
		try (Connection conn = DriverManager.getConnection(CONNECTION, USER, PASSWORD)) {
			String sqlQuery = "INSERT INTO " + TABLE
					+ " (user_id, surname, forename, address, birthday, username, password, email, IBAN, weight, height, role_id)"
					+ " VALUES (NULL,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, user.getSurname());
			prepStmt.setString(2, user.getForename());
			prepStmt.setString(3, user.getAddress());
			prepStmt.setDate(4, Date.valueOf(user.getBirthday()));
			prepStmt.setString(5, user.getUsername());
			prepStmt.setString(6, passwordEncryptionSHA(user.getPassword()));
			prepStmt.setString(7, user.getEmail());
			prepStmt.setString(8, user.getIBAN());
			prepStmt.setDouble(9, user.getWeight());
			prepStmt.setInt(10, user.getHeight());
			prepStmt.setInt(11, user.getRoleID());
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
	public String passwordEncryptionSHA(String pass) {
		
// 		Not working properly -> Future testing
//		MessageDigest digest;
//		byte[] hash;
//		try {
//			// choose the message digest algorithm
//			digest = MessageDigest.getInstance("SHA-256");
//			// conversion of the String into bytes
//			hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
//			// return the hashed password as String
//			 return new String(hash);
//			
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
