package de.gym.backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User maker class used for easier random generation of user data to be used
 * for database population.
 * 
 * @author florina
 *
 */
public class UserMaker {

	/** List containing all user data */
	private List<User> allUsers = new ArrayList<User>();
	/** Array of family names examples */
	private String[] surnames = { "Fisher", "Weber", "Wagner", "Schmidt", "Becker" };
	/** Array of forenames examples */
	private String[] forenames = { "Ben", "Emma", "Luca", "Mia", "Hanna" };
	/** Array of addresses examples */
	private String[] addresses = { "Dachauer str. ", "Sendlinger str. ", "Tal ", "Brienner str. ", "Petuelring " };
	/** Array of birthdays */
	private LocalDate[] birthdays = { LocalDate.of(1990, 12, 24), LocalDate.of(1986, 2, 12), LocalDate.of(1998, 4, 25),
			LocalDate.of(1970, 1, 1), LocalDate.of(2000, 8, 29) };
	/** Array of email providers */
	private String[] emailProviders = { "gmail", "yahoo", "gmx" };
	/** Array of weights examples */
	private double[] weights = { 50.7, 70.4, 66.9, 90.9, 89 };
	/** Array of heights */
	private int[] heights = { 155, 170, 185, 168, 179 };

	/**
	 * Randomly generated User objects based on the data examples stored in each
	 * Attribute.
	 * 
	 * @param limit
	 */
	public UserMaker(int limit) {
		Random dice = new Random();
		int index;
		for (int i = 0; i < limit; i++) {
			index = dice.nextInt(surnames.length);
			String surname = surnames[index];

			index = dice.nextInt(forenames.length);
			String forename = forenames[index];

			index = dice.nextInt(addresses.length);
			String address = addresses[index] + dice.nextInt(300) + ", " + (dice.nextInt(1000) + 90000) + " Muenchen";

			index = dice.nextInt(birthdays.length);
			LocalDate birthday = birthdays[index];

			String username = forename.toLowerCase() + "_" + surname.toLowerCase();
			String password = surname.toLowerCase() + "123#";

			index = dice.nextInt(emailProviders.length);
			String email = forename.toLowerCase() + "." + surname.toLowerCase() + "@" + emailProviders[index] + ".com";

			String IBAN = randomIBAN();

			index = dice.nextInt(weights.length);
			double weight = weights[index];

			index = dice.nextInt(heights.length);
			int height = heights[index];

			User user = new User(forename, surname, address, birthday, username, password, email, IBAN, weight, height);
			user.setRoleID(3);
			allUsers.add(user);

		}
	}

	/**
	 * Generate randomly IBAN examples for the User data.
	 * @return
	 */
	public String randomIBAN() {
		Random dice = new Random();
		String IBAN = "DE";
		for (int i = 0; i < 20; i++) {
			int n = dice.nextInt(10);
			IBAN += Integer.toString(n);
		}
		return IBAN;
	}
	
	/**
	 * Return the list of all users.
	 * @return
	 */
	public List<User> getAllUsers() {
		return allUsers;
	}
}
