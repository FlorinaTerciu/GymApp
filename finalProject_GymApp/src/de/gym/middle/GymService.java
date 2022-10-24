package de.gym.middle;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import de.gym.backend.Course;
import de.gym.backend.CourseDAO;
import de.gym.backend.CourseDAOwithDB;
import de.gym.backend.Employee;
import de.gym.backend.EmployeeDAO;
import de.gym.backend.EmployeeDAOwithDB;
import de.gym.backend.Role;
import de.gym.backend.RoleDAO;
import de.gym.backend.RoleDAOwithDB;
import de.gym.backend.User;
import de.gym.backend.UserCourseRegistration;
import de.gym.backend.UserCourseRegistrationDAO;
import de.gym.backend.UserCourseRegistrationDAOwithDB;
import de.gym.backend.UserDAO;
import de.gym.backend.UserDAOwithDB;
import de.gym.backend.UserProgress;
import de.gym.backend.UserProgressDAOwithDB;
import de.gym.frontend.AdminSceneCreator;
import de.gym.frontend.ClientSceneCreator;
import de.gym.frontend.TrainerSceneCreator;
import javafx.scene.Scene;
/**
 * The gym Service takes care of the communication between 
 * the frontend and backend via the interfaces.
 * @author florina
 *
 */
public class GymService {

	/**Logged in user*/
	private User actualUser;
	/**Employee data source*/
	private EmployeeDAO employeeDB = new EmployeeDAOwithDB();
	/**User data source*/
	private UserDAO userDB = new UserDAOwithDB();
	/** Course data source*/
	private CourseDAO courseDB;
	/**Course registration data source */
	private UserCourseRegistrationDAO userCourseDB;
	/**Role data source*/
	private RoleDAO roleDB = new RoleDAOwithDB();
	/**User progress data source*/
	private UserProgressDAOwithDB progressDB;
	/**User data source*/
	private UserDAO dataSource;
	/**List of all employees */
	private List<Employee> allEmployees;
	/**List of all users*/
	private List<User> allUsers;
	/**List of all courses */
	private List<Course> allCourses;
	/**List of all roles*/
	private List<Role> allRoles;
	/**All courses with the participants*/
	private List <UserCourseRegistration> allUserCourses;
	/**List of all user's progress*/
	private List <UserProgress> allUserProgress;
	
	/**
	 * Gym service constructor initializing the data sources.
	 */
	public GymService() {
		allEmployees = getAllEmployees();
		courseDB = new CourseDAOwithDB(employeeDB);
		allCourses = courseDB.getAllCourses();
		userCourseDB = new UserCourseRegistrationDAOwithDB(userDB, courseDB);
		progressDB = new UserProgressDAOwithDB(userDB);
	}
	
	/**
	 * Constructor used for the initialization of data source, needed for the UserMaker class.
	 * @param dataSource
	 */
	public GymService(UserDAO dataSource) {
		this.dataSource = dataSource;
		allUsers = dataSource.getAllUsers();
	}
	
	/**
	 * Based on the user's role, the next scene is returned.
	 * @return
	 */
	public Scene getNextScene() {
		//Admin
		if(actualUser.getRoleID()==1) {
			AdminSceneCreator registration = new AdminSceneCreator(this);
			return registration.start();
		}
		//Trainer
		if(actualUser.getRoleID()==2) {
			TrainerSceneCreator addCourse = new TrainerSceneCreator(this);
			return addCourse.start();
		}
		//Classic - gym client
		if(actualUser.getRoleID()==3) {
			ClientSceneCreator registerForCourse = new ClientSceneCreator(this);
			return registerForCourse.start();
		} 
		
			
		return new Scene(null);
	}


	/**
	 * @return the actualUser
	 */
	public User getActualUser() {
		return actualUser;
	}

	/**
	 * @param actualUser the actualUser to set
	 */
	public void setActualUser(User actualUser) {
		this.actualUser = actualUser;
	}
	
	/**
	 * Find a specific employee based on a defined criteria.
	 * @param criteria
	 * @return
	 */
	public List<Employee> findEmployee(Predicate <Employee> criteria){
		return allEmployees.stream().filter(criteria).collect(Collectors.toList());
	}
	
	/**
	 * Get all users
	 * @return
	 */
	public List<User> getAllUsers() {
		allUsers = userDB.getAllUsers();
		return allUsers;
	}
	
	/**
	 * Get all users from the UserMaker
	 * @return
	 */
	public List<User> getAllUsersMaker() {
		allUsers = dataSource.getAllUsers();
		return allUsers;
	}
	
	/**
	 * Add user
	 * @param user
	 */
	public void addUser(User user) {
		userDB.addUser(user);
	}
	
	/**
	 * Return all employees
	 * @return
	 */
	public List<Employee> getAllEmployees() {
		allEmployees = employeeDB.getAllEmployees();
		return allEmployees;
	}

	/**
	 * Add new employee
	 * @param employee
	 */
	public void addEmployee(Employee employee) {
		employeeDB.addEmployee(employee);
	}
	
	/**
	 * Return all courses
	 * @return
	 */
	public List<Course> getAllCourses() {
		return allCourses;
	}
	
	/**
	 * Add new course
	 * @param course
	 */
	public void addCourse(Course course) {
		courseDB.addCourse(course);
	}
	
	/**
	 * Return all roles
	 * @return
	 */
	public List<Role> getAllRoles(){
		allRoles = roleDB.getAllRoles();
		return allRoles;
	}
	
	/**
	 * Return all courses and its participants.
	 * @return
	 */
	public List <UserCourseRegistration> getAllUserCourses(){
		allUserCourses = userCourseDB.getAllCoursesRegistration();
		return allUserCourses;
	}
	
	/**
	 * Add new participant to the course.
	 * @param userCourse
	 */
	public void addUserCourse (UserCourseRegistration userCourse) {
		userCourseDB.addCourseRegistration(userCourse);
	}
	
	/**
	 * Return all progress for users.
	 * @return
	 */
	public List <UserProgress> getAllUserProgress() {
		allUserProgress =progressDB.getAllUserProgress();
		return allUserProgress;
	}
	
	/**
	 * Add new progress of the user.
	 * @param userProgress
	 */
	public void addProgressWeight(UserProgress userProgress) {
		progressDB.addProgressWeight(userProgress);
	}
}
