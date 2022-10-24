package de.gym.backend;

public class UserCourseRegistration {

	/**Course registration's unique ID*/
	private int registrationID;
	/**The course where a user has registered to attend*/
	private Course course;
	/**The user who attends this course*/
	private User user;
	
	/**
	 * @param course
	 * @param user
	 */
	public UserCourseRegistration(Course course, User user) {
		this.course = course;
		this.user = user;
	}

	/**
	 * @return the registrationID
	 */
	public int getRegistrationID() {
		return registrationID;
	}

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param registrationID the registrationID to set
	 */
	public void setRegistrationID(int registrationID) {
		this.registrationID = registrationID;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserCourseRegistration [registrationID=" + registrationID + ", course=" + course + ", user=" + user
				+ "]";
	}
	
	
	
}
