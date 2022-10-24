package de.gym.backend;

import java.time.LocalDate;
/**
 * The class represents the attributes used to described the progress made by a client in the gym.
 * The user keep track of his progress and store his new weight into the database.
 * @author florina
 *
 */
public class UserProgress {
	
	/**User progress unique ID from the database*/
	private int progressID;
	/**User's new weight*/
	private double weight;
	/**User object*/
	private User user;
	/**The date of reading the new weight*/
	private LocalDate date;
	
	/**
	 * Constructor creation
	 * @param weight
	 * @param user
	 */
	public UserProgress(User user, LocalDate date, double weight) {
		this.user = user;
		this.date = date;
		this.weight = weight;
	}

	/**
	 * @return the progressID
	 */
	public int getProgressID() {
		return progressID;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * @param progressID the progressID to set
	 */
	public void setProgressID(int progressID) {
		this.progressID = progressID;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "UserProgress [progressID=" + progressID + ", weight=" + weight + ", user=" + user + ", date=" + date
				+ "]";
	}


	
	

}
