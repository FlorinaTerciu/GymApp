module finalProject_GymApp {
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires java.sql;
	requires mysql.connector.java;
	
	
	opens de.gym.backend to javafx.base;

	opens de.gym.frontend;
}