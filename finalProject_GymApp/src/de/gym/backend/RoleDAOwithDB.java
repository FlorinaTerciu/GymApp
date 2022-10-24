package de.gym.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The class implements the methods described in the interface. If the
 * connection to the database is successful, all available roles are returned
 * from the database.
 * A user can have one of the following roles:
 * 			1 - admin
 * 			2 - trainer
 * 			3 - classic
 * @author florina
 *
 */
public class RoleDAOwithDB implements RoleDAO{

	/**URL for connecting to the database */
	public static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/gymapp_db";
	/**The database user on whose behalf the connection is being made */
	public static final String USER = "root";
	/**The user's password*/
	public static final String PASSWORD = "";
	/**The table from which the data is retrieved */
	public static final String TABLE = "role";
	/**All available user roles */
	private List <Role> allRoles = new ArrayList<Role>();
	
	/**
	 * All user roles are returned from the database.
	 */
	@Override
	public List <Role> getAllRoles() {
		try(Connection conn = DriverManager.getConnection(CONNECTION,USER,PASSWORD)){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM "+TABLE);
			while(rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				Role role = new Role(name);
				role.setRole_id(id);
				allRoles.add(role);
			}
		}catch (SQLException dbException) {
			dbException.printStackTrace();
		}
		return allRoles;
	}

}
