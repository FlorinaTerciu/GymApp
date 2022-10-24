package de.gym.backend;

import java.time.LocalDate;

/**
 * Compared to an employee (user having the role "admin"), a trainer receives
 * extra benefits for further development such as additional salary budget for
 * specializations and extra vacation days for participation to the courses.
 * 
 * @author florina
 *
 */
public class Trainer extends Employee {

	public Trainer(String forename, String surname, String address, LocalDate birthday, String username,
			String password, String email, String iBAN, double salary, int vacationDays, int workingHours) {
		super(forename, surname, address, birthday, username, password, email, iBAN, salary, vacationDays,
				workingHours);

	}

	/**
	 * For each trainer, there is an extra budget per year allocated for further
	 * development.
	 * 
	 * @param salary the salary to set
	 */
	@Override
	public void setSalary(double salary) {
		this.salary = salary + 100;
	}

	/**
	 * For each trainer there are 10 extra vacation days allocated for further
	 * development training.
	 * 
	 * @param vacationDays the vacationDays to set
	 */
	@Override
	public void setVacationDays(int vacationDays) {
		this.vacationDays = vacationDays + 10;
	}

}
