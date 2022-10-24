package de.gym.main;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import javafx.application.Application;

/**
 * Launch the Login form, the primary stage of the gym App. 
 * As a user with the admin role, I am redirected to registration page and user overview.
 * As a user with the trainer role, I am redirected to the page where I have the possibility to
 * add new courses. 
 * As a user with the classic role, I am redirected to the page where I can register to available
 * courses or give a new entry regarding my progress.
 * 
 * Test Data:
 * 		Admin: ella_watson / gympass123
 * 		Trainer: john_meyer / training89#
 * 		Client: ana_muller / muller123
 * 		
 * 		Admin: lea_beck / admin123
 * 		Trainer: james_watson / trining90#
 * 		Client: donna_jason / jason123#
 * @author florina
 *
 */
public class Main {

	public static void main(String[] args) {
		
		Application.launch(de.gym.frontend.LoginForm.class);

		
	}

}
