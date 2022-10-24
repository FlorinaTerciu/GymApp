package de.gym.backend;

import java.util.List;
/**
 * The interface describes which methods will be implemented by the UserCourseRegistration class,
 * connected to the DB such as:
 * 		Get all the courses from the database as well as the clients who attend this courses.
 * 		Add a new registration to a specific course into the database.
 * @author florina
 *
 */
public interface UserCourseRegistrationDAO {

	List <UserCourseRegistration> getAllCoursesRegistration();
	void addCourseRegistration(UserCourseRegistration courseRegistration);
}
