package de.gym.backend;

import java.util.List;
/**
 * The interface describes which methods will be implemented by the Employee class,
 * connected to the DB such as:
 * 		Get all the employees from the database
 * 		Add a new employee to the database
 * 
 * @author florina
 *
 */
public interface EmployeeDAO {

	List <Employee> getAllEmployees();
	void addEmployee(Employee employee);
}
