package de.gym.backend;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The course class provides the details of the course as well as the who is the trainer for this course.
 * @author florina
 *
 */
public class Course {

	/** the course's unique ID from the database */
	private int courseID;
	/** the course's name */
	private String name;
	/** the course's description */
	private String description;
	/** the course's time and date */
	private LocalDateTime courseDate;
	/** the course's trainer */
	private Employee trainer;
	
	/**
	 * Course constructor
	 * @param name
	 * @param description
	 * @param courseDate
	 * @param trainer
	 */
	public Course(String name, String description, LocalDateTime courseDate, Employee trainer) {
		this.name = name;
		this.description = description;
		this.courseDate = courseDate;
		this.trainer = trainer;
	}

	/**
	 * @return the courseID
	 */
	public int getCourseID() {
		return courseID;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the courseDate
	 */
	public LocalDateTime getCourseDate() {
		return courseDate;
	}

	/**
	 * @return the trainer
	 */
	public Employee getTrainer() {
		return trainer;
	}

	/**
	 * @param courseID the courseID to set
	 */
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param courseDate the courseDate to set
	 */
	public void setCourseDate(LocalDateTime courseDate) {
		this.courseDate = courseDate;
	}

	/**
	 * @param trainer the trainer to set
	 */
	public void setTrainer(Employee trainer) {
		this.trainer = trainer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseDate, description, name, trainer);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Course)) {
			return false;
		}
		Course other = (Course) obj;
		return Objects.equals(courseDate, other.courseDate) && Objects.equals(description, other.description)
				&& Objects.equals(name, other.name) && Objects.equals(trainer, other.trainer);
	}

	@Override
	public String toString() {
		return "Course [courseID=" + courseID + ", name=" + name + ", description=" + description + ", courseDate="
				+ courseDate + ", trainer=" + trainer + "]";
	}
	
	
	
}
