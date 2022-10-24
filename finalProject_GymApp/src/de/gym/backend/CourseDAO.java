package de.gym.backend;

import java.util.List;
/**
 * The interface describes which methods will be implemented by the Course class,
 * connected to the DB such as:
 * 		Get all the courses from the database
 * 		Add a new course to the database
 * 
 * @author florina
 *
 */
public interface CourseDAO {
	
	List <Course> getAllCourses();
	void addCourse(Course course);
}
