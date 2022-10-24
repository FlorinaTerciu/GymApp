package de.gym.backend;

import java.util.List;

/**
 * The interface describes which methods will be implemented by the UserProgress class,
 * connected to the DB, such as:
 * 		Get all the user progress from the database
 * 		Add a new weight to the database
 * @author florina
 *
 */
public interface UserProgressDAO {

	List <UserProgress> getAllUserProgress ();
	void addProgressWeight(UserProgress userProgress);
	
}
