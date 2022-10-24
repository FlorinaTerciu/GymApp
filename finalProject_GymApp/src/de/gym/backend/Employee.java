package de.gym.backend;

import java.time.LocalDate;
import java.util.Objects;
/**
 * An Employee of the gym, has more rights in the gym app.
 * He can access the client list and make changes to the users.
 *
 * @author florina
 *
 */
public class Employee extends User{

	/**Employee's monthly salary */
	protected double salary;
	/** Employee's number of vacation days */
	protected int vacationDays;
	/** Employee's working hours */
	protected int workingHours;
	
	public Employee(String forename, String surname, String address, LocalDate birthday, String username,
			String password, String email, String iBAN, double salary, int vacationDays, int workingHours) {
		super(forename, surname, address, birthday, username, password, email, iBAN);
		this.salary = salary;
		this.vacationDays = vacationDays;
		this.workingHours = workingHours;
	}

	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * @return the vacationDays
	 */
	public int getVacationDays() {
		return vacationDays;
	}

	/**
	 * @return the workingHours
	 */
	public int getWorkingHours() {
		return workingHours;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * @param vacationDays the vacationDays to set
	 */
	public void setVacationDays(int vacationDays) {
		this.vacationDays = vacationDays;
	}

	/**
	 * @param workingHours the workingHours to set
	 */
	public void setWorkingHours(int workingHours) {
		this.workingHours = workingHours;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(IBAN, address, birthday, email, forename, password, surname, username,salary, vacationDays, workingHours);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Employee)) {
			return false;
		}
		Employee other = (Employee) obj;
		return Objects.equals(IBAN, other.IBAN) && Objects.equals(address, other.address)
				&& Objects.equals(birthday, other.birthday) && Objects.equals(email, other.email)
				&& Objects.equals(forename, other.forename) && Objects.equals(password, other.password)
				&& Objects.equals(surname, other.surname) && Objects.equals(username, other.username)
				&&Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary)
				&& vacationDays == other.vacationDays && workingHours == other.workingHours;
	}

	@Override
	public String toString() {
		return "Employee [salary=" + salary + ", vacationDays=" + vacationDays + ", workingHours=" + workingHours
				+ ", userID=" + userID + ", forename=" + forename + ", surname=" + surname + ", address=" + address
				+ ", birthday=" + birthday + ", roleID=" + roleID + ", username=" + username + ", password=" + password
				+ ", email=" + email + ", IBAN=" + IBAN + "]";
	}
	
	

}
