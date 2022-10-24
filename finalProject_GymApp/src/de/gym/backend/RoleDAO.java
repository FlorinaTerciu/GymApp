package de.gym.backend;

import java.util.List;
/**
 * The interface describes which methods will be implemented by the Role class,
 * connected to the DB such as:
 * 		Get all roles from the database
 * @author florina
 *
 */
public interface RoleDAO {
	
	List<Role> getAllRoles();

}
