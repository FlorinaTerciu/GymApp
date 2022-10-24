package de.gym.frontend;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import de.gym.backend.Course;
import de.gym.backend.Employee;
import de.gym.middle.GymService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
/**
 * Trainer Scene Creator, this view can be accessed only by users with the role trainer.
 * As a trainer I have the possibility to add new courses.
 * @author florina
 *
 */
public class TrainerSceneCreator {

	/**Course object*/
	private Course course;
	/**Employee with the role trainer*/
	private Employee trainer;
	/**Gym service object*/
	private GymService gymService;

	/**
	 * Constructor initializing the gym service
	 * @param gymService
	 */
	public TrainerSceneCreator(GymService gymService) {
		this.gymService = gymService;
	}

	/**
	 * Create the course registration grid Pane
	 * @return
	 */
	public Scene start() {
		// Create the registration of a new course form grid pane
		GridPane newCourseGridPane = createAddCourseFormPane();
		// Add UI controls to the registration form grid pane
		addUIControls(newCourseGridPane);
		// Create a scene with registration form grid pane as the root node
		Scene scene = new Scene(newCourseGridPane, 900, 600);
		return scene;
	}

	private GridPane createAddCourseFormPane() {
		// Instantiate a new Grid Pane
		GridPane newCourseGridPane = new GridPane();

		// Position the pane at the center of the screen, both vertically and
		// horizontally
		newCourseGridPane.setAlignment(Pos.CENTER);

		// Set a padding of 20px on each side
		newCourseGridPane.setPadding(new Insets(40, 40, 40, 40));

		// Set the horizontal gap between columns
		newCourseGridPane.setHgap(10);

		// Set the vertical gap between rows
		newCourseGridPane.setVgap(10);

		// Add Column Constraints

		// columnOneConstraints will be applied to all the nodes placed in column one.
		ColumnConstraints columnOneConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);

		// columnTwoConstraints will be applied to all the nodes placed in column two.
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);

		newCourseGridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

		return newCourseGridPane;
	}

	private void addUIControls(GridPane newCourseGridPane) {
		// Add Header
		Label headerLabel = new Label("Add course");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		newCourseGridPane.add(headerLabel, 0, 0, 2, 1);
		
		//Logout Button
		Button logoutButton = new Button("Logout");
		logoutButton.setPrefHeight(40);
		logoutButton.setDefaultButton(true);
		logoutButton.setPrefWidth(100);
		newCourseGridPane.add(logoutButton, 0, 0, 2, 1);
		
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		// Add course name label
		Label nameCourseLabel = new Label("Course Name : ");
		newCourseGridPane.add(nameCourseLabel, 0, 1);

		// Add surname text field
		TextField nameCourseField = new TextField();
		nameCourseField.setPrefHeight(40);
		newCourseGridPane.add(nameCourseField, 1, 1);

		// Add course description label
		Label descriptionLabel = new Label("Description : ");
		newCourseGridPane.add(descriptionLabel, 0, 2);

		// Add course description text field
		TextField descriptionField = new TextField();
		descriptionField.setPrefHeight(40);
		newCourseGridPane.add(descriptionField, 1, 2);

		// Add date of course label
		Label dateLabel = new Label("Date of course : ");
		newCourseGridPane.add(dateLabel, 0, 3);

		// Add date of course text field
		DatePicker dateField = new DatePicker();
		dateField.setPrefHeight(40);
		dateField.setEditable(false);
		newCourseGridPane.add(dateField, 1, 3);

		// Add time of course label
		Label timeLabel = new Label("Time of course (HH:mm) : ");
		newCourseGridPane.add(timeLabel, 0, 4);

		// Add time text field
		TextField timeField = new TextField();
		timeField.setPrefHeight(40);
		newCourseGridPane.add(timeField, 1, 4);

		// Add course Button
		Button addButton = new Button("Add course");
		addButton.setPrefHeight(40);
		addButton.setDefaultButton(true);
		addButton.setPrefWidth(100);
		newCourseGridPane.add(addButton, 0, 5, 2, 1);
		GridPane.setHalignment(addButton, HPos.CENTER);
		GridPane.setMargin(addButton, new Insets(20, 0, 20, 0));

		addButton.setOnAction(new EventHandler<ActionEvent>() {
			String courseName;
			String description;
			LocalDate date;
			LocalTime time;
			LocalDateTime courseDateTime;

			@Override
			public void handle(ActionEvent event) {

				if (nameCourseField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, newCourseGridPane.getScene().getWindow(), "Form Error!",
							"Please enter the course name");
					return;
				} else {
					courseName = nameCourseField.getText();
				}

				if (descriptionField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, newCourseGridPane.getScene().getWindow(), "Form Error!",
							"Please enter the course description");
					return;
				} else {
					description = descriptionField.getText();
				}

				if (dateField.getValue() == null) {
					showAlert(Alert.AlertType.ERROR, newCourseGridPane.getScene().getWindow(), "Form Error!",
							"Please enter the date of course");
					return;
				} else {
					date = LocalDate.of(dateField.getValue().getYear(), dateField.getValue().getMonthValue(),
							dateField.getValue().getDayOfMonth());
				}

				if (timeField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, newCourseGridPane.getScene().getWindow(), "Form Error!",
							"Please enter the time of course");
					return;
				} else {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
					time = LocalTime.parse(timeField.getText(), formatter);
				}

				courseDateTime = LocalDateTime.of(date, time);
				//Find the logged in trainer from all employees, based on the userID.
				trainer = gymService
						.findEmployee(employee -> employee.getUserID() == gymService.getActualUser().getUserID())
						.get(0);
				course = new Course(courseName, description, courseDateTime, trainer);
				System.out.println("----Trainer Scene Creator --- Add course by trainer ----");
				System.out.println(course.toString());
				gymService.addCourse(course);
			}

		});
		
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LoginForm login = new LoginForm();
				try {
					newCourseGridPane.getScene().getWindow().hide();
					login.start(new Stage());
					
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				
			}
			
		});
	}

	/**
	 * Display alerts for users.
	 * @param alertType
	 * @param owner
	 * @param title
	 * @param message
	 */
	private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
}
