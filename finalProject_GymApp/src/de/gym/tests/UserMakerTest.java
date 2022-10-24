package de.gym.tests;

import java.util.List;

import de.gym.backend.User;
import de.gym.backend.UserDAO;
import de.gym.backend.UserDAOwithMaker;
import de.gym.middle.GymService;

/**
 * Main class for randomly generating of 20 users and populating the user table
 * with the random data.
 * 
 * @author florina
 *
 */
public class UserMakerTest {

	public static void main(String[] args) {

		/** User data source */
		UserDAO dataSource = new UserDAOwithMaker();
		/** Initialization of gym service */
		GymService gymService = new GymService(dataSource);
		/** List of all users */
		List<User> allUsers = gymService.getAllUsersMaker();

		System.out.println("------ Add clients into database----");
		// Add all users into the database
		for (User user : allUsers) {
			System.out.println(user.toString());
			//gymService.addUser(user);
		}

	}

}
