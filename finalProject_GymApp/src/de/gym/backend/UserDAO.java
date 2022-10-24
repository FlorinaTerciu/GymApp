package de.gym.backend;

import java.util.List;

/**
 * The interface describes which methods will be implemented by the User class,
 * connected either to the DB or as a Maker, such as:
 * 		Get all the employees from the database/maker
 * 		Add a new employee to the database/maker
 * @author florina
 *
 */
public interface UserDAO {

	List <User> getAllUsers();
	void addUser(User user);
	String passwordEncryptionSHA(String pass);
}
