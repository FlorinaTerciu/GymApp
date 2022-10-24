package de.gym.backend;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The User class represents the attributes a normal user has, when he logges in
 * the gymApp. By normal user, it is meant the person who attends the gym, the
 * client. A user can have different roles besides client, such as employee of
 * the gym or trainer.
 * 
 * @author florina
 *
 */
public class User {

	/** the user's unique ID from the database */
	protected int userID;
	/** the user's forename */
	protected String forename;
	/** the user's family name */
	protected String surname;
	/** the user's address */
	protected String address;
	/** the user's birthday */
	protected LocalDate birthday;
	/**
	 * the user's role ID, which shows if the user is only a gym member, an
	 * employee, a trainer or an administrator
	 */
	protected int roleID;
	/** the user's username, credential used for login */
	protected String username;
	/** the user's encrypted password, credential used for login */
	protected String password;
	/** the user's email address */
	protected String email;
	/** the user's bank account IBAN */
	protected String IBAN;
	/** the user's initial weight */
	protected double weight;
	/** the user's height */
	protected int height;

	/**
	 * User Constructor using fields, the primary keys userID and roleID will be set
	 * up via setter method. This Constructor it is used for the clients
	 */
	public User(String forename, String surname, String address, LocalDate birthday, String username, String password,
			String email, String iBAN, double weight, int height) {
		this.forename = forename;
		this.surname = surname;
		this.address = address;
		this.birthday = birthday;
		this.username = username;
		this.password = password;
		this.email = email;
		this.IBAN = iBAN;
		this.weight = weight;
		this.height = height;
	}

	/**
	 * User Constructor used for the employees and trainers.
	 */
	public User(String forename, String surname, String address, LocalDate birthday, String username, String password,
			String email, String iBAN) {
		this.forename = forename;
		this.surname = surname;
		this.address = address;
		this.birthday = birthday;
		this.username = username;
		this.password = password;
		this.email = email;
		this.IBAN = iBAN;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @return the forename
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the birthday
	 */
	public LocalDate getBirthday() {
		return birthday;
	}

	/**
	 * @return the roleID
	 */
	public int getRoleID() {
		return roleID;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the iBAN
	 */
	public String getIBAN() {
		return IBAN;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @param forename the forename to set
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	/**
	 * @param roleID the roleID to set
	 */
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param iBAN the iBAN to set
	 */
	public void setIBAN(String iBAN) {
		this.IBAN = iBAN;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int hashCode() {
		return Objects.hash(IBAN, address, birthday, email, forename, password, surname, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(IBAN, other.IBAN) && Objects.equals(address, other.address)
				&& Objects.equals(birthday, other.birthday) && Objects.equals(email, other.email)
				&& Objects.equals(forename, other.forename) && Objects.equals(password, other.password)
				&& Objects.equals(surname, other.surname) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", forename=" + forename + ", surname=" + surname + ", address=" + address
				+ ", birthday=" + birthday + ", roleID=" + roleID + ", username=" + username + ", password=" + password
				+ ", email=" + email + ", IBAN=" + IBAN + ", weight=" + weight + ", height=" + height + "]";
	}

}
