package de.gym.frontend;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import de.gym.backend.Course;
import de.gym.backend.User;
import de.gym.backend.UserCourseRegistration;
import de.gym.backend.UserProgress;
import de.gym.middle.GymService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * As a user with the role "classic", therefore as a client of the gym I can
 * register to the available courses or keep track of my progress by adding new
 * entry of my weight.
 * 
 * @author florina
 *
 */
public class ClientSceneCreator {

	/** Logged user object */
	private User loggedUser;
	/** Selected course for registration */
	private Course selectedCourse;
	/** Course registration object */
	private UserCourseRegistration userCourse;
	/** User progress object */
	private UserProgress userProgress;
	/** Gym service object */
	private GymService gymService;
	/** List containing all available courses */
	private List<Course> allCourses = new ArrayList<Course>();

	/**
	 * Initialization of the service in constructor.
	 * 
	 * @param gymService
	 */
	public ClientSceneCreator(GymService gymService) {
		this.gymService = gymService;
	}

	/**
	 * Method used to create and design the the client scene.
	 * 
	 * @return
	 */
	public Scene start() {
		// Tab Pane creation
		TabPane clientTabs = new TabPane();

		// Create the registration form grid pane
		GridPane registrationPane = createClientFormPane();
		GridPane progressPane = createClientFormPane();

		// Create the client tabs
		Tab registrationTab = new Tab("Course Registration", registrationPane);
		Tab progressTab = new Tab("Weight Progress", progressPane);
		clientTabs.getTabs().addAll(registrationTab, progressTab);

		// Add UI controls to the course registration form grid pane
		addUIControlsRegistration(registrationPane);
		// Add UI controls to the weight progress form grid pane
		addUIControlsProgress(progressPane);
		// Create a scene with registration form grid pane as the root node
		Scene scene = new Scene(clientTabs, 900, 600);
		return scene;
	}

	/**
	 * Creation of the client grid pane.
	 * 
	 * @return
	 */
	private GridPane createClientFormPane() {
		// Instantiate a new Grid Pane
		GridPane clientGridPane = new GridPane();

		// Position the pane at the center of the screen, both vertically and
		// horizontally
		clientGridPane.setAlignment(Pos.CENTER);

		// Set a padding of 20px on each side
		clientGridPane.setPadding(new Insets(40, 40, 40, 40));

		// Set the horizontal gap between columns
		clientGridPane.setHgap(10);

		// Set the vertical gap between rows
		clientGridPane.setVgap(10);

		// Add Column Constraints

		// columnOneConstraints will be applied to all the nodes placed in column one.
		ColumnConstraints columnOneConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);

		// columnTwoConstraints will be applied to all the nodes placed in column two.
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);

		clientGridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

		return clientGridPane;
	}

	/**
	 * Add UI controls to the course registration grid pane.
	 * 
	 * @param registrationPane
	 */
	private void addUIControlsRegistration(GridPane registrationPane) {
		// Add Header
		Label headerLabel = new Label("Course Registration Form");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		registrationPane.add(headerLabel, 0, 0, 2, 1);

		// Logout Button
		Button logoutButton = new Button("Logout");
		logoutButton.setPrefHeight(40);
		logoutButton.setDefaultButton(true);
		logoutButton.setPrefWidth(100);
		registrationPane.add(logoutButton, 0, 0, 2, 1);

		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		// Add available courses label
		Label courseLabel = new Label("Available courses : ");
		registrationPane.add(courseLabel, 0, 1);

		// Add available courses comboBox
		ComboBox<String> coursesCombo = new ComboBox<String>();
		allCourses = gymService.getAllCourses();
		for (Course course : allCourses) {
			coursesCombo.getItems().add(course.getName());
		}
		registrationPane.add(coursesCombo, 1, 1);

		// Add description label
		Label descriptionLabel = new Label("Description : ");
		registrationPane.add(descriptionLabel, 0, 2);

		// Add description text field
		TextField descriptionField = new TextField();
		descriptionField.setPrefHeight(40);
		registrationPane.add(descriptionField, 1, 2);

		// Add date label
		Label dateLabel = new Label("Date : ");
		registrationPane.add(dateLabel, 0, 3);

		// Add date text field
		TextField dateField = new TextField();
		dateField.setPrefHeight(40);
		registrationPane.add(dateField, 1, 3);

		// Add time label
		Label timeLabel = new Label("Time : ");
		registrationPane.add(timeLabel, 0, 4);

		// Add time text field
		TextField timeField = new TextField();
		timeField.setPrefHeight(40);
		registrationPane.add(timeField, 1, 4);

		// Add register Button
		Button registerButton = new Button("Register to course");
		registerButton.setPrefHeight(40);
		registerButton.setDefaultButton(true);
		registerButton.setPrefWidth(150);
		registrationPane.add(registerButton, 0, 5, 2, 1);
		GridPane.setHalignment(registerButton, HPos.CENTER);
		GridPane.setMargin(registerButton, new Insets(20, 0, 20, 0));

		coursesCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				for (Course course : allCourses) {
					if (course.getName().equals(coursesCombo.getSelectionModel().getSelectedItem())) {
						descriptionField.setText(course.getDescription());
						dateField.setText(
								LocalDate.of(course.getCourseDate().getYear(), course.getCourseDate().getMonthValue(),
										course.getCourseDate().getDayOfMonth()).toString());
						timeField
								.setText(LocalTime.parse(
										LocalTime.of(course.getCourseDate().getHour(),
												course.getCourseDate().getMinute()).toString(),
										DateTimeFormatter.ofPattern("HH:mm")).toString());
						selectedCourse = course;
						break;
					}
				}
			}
		});

		registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (coursesCombo.getSelectionModel().getSelectedItem() == null) {
					showAlert(Alert.AlertType.ERROR, registrationPane.getScene().getWindow(), "Form Error!",
							"Please choose your course");
					return;
				} else {

					loggedUser = gymService.getActualUser();
					userCourse = new UserCourseRegistration(selectedCourse, loggedUser);
					System.out.println("----Client Scene Creator--- register client to course----");
					System.out.println(userCourse.toString());
					gymService.addUserCourse(userCourse);
				}
			}

		});

		logoutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LoginForm login = new LoginForm();
				try {
					registrationPane.getScene().getWindow().hide();
					login.start(new Stage());

				} catch (Exception exception) {
					exception.printStackTrace();
				}

			}

		});
	}

	/**
	 * Add UI Controls to the client progress pane.
	 * 
	 * @param progressPane
	 */
	private void addUIControlsProgress(GridPane progressPane) {
		// Add Header
		Label headerLabel = new Label("Weight Progress");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		progressPane.add(headerLabel, 0, 0, 2, 1);

		// Logout Button
		Button logoutButton = new Button("Logout");
		logoutButton.setPrefHeight(40);
		logoutButton.setDefaultButton(true);
		logoutButton.setPrefWidth(100);
		progressPane.add(logoutButton, 0, 0, 2, 1);

		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		// Add date label
		Label dateLabel = new Label("Date : ");
		progressPane.add(dateLabel, 0, 1);

		// Add date text field
		DatePicker dateField = new DatePicker();
		dateField.setPrefHeight(40);
		dateField.setEditable(false);
		progressPane.add(dateField, 1, 1);

		// Add new weight label
		Label weightLabel = new Label("Weight : ");
		progressPane.add(weightLabel, 0, 2);

		// Add new weight text field
		TextField weightField = new TextField();
		weightField.setPrefHeight(40);
		progressPane.add(weightField, 1, 2);

		// Add new weight Button
		Button addWeightButton = new Button("Add new weight");
		addWeightButton.setPrefHeight(40);
		addWeightButton.setDefaultButton(true);
		addWeightButton.setPrefWidth(150);
		progressPane.add(addWeightButton, 0, 3, 2, 1);
		GridPane.setHalignment(addWeightButton, HPos.CENTER);
		GridPane.setMargin(addWeightButton, new Insets(20, 0, 20, 0));

		addWeightButton.setOnAction(new EventHandler<ActionEvent>() {
			LocalDate date;
			double weight;

			@Override
			public void handle(ActionEvent event) {
				if (dateField.getValue() == null) {
					showAlert(Alert.AlertType.ERROR, progressPane.getScene().getWindow(), "Form Error!",
							"Please choose the date");
					return;
				} else {
					date = LocalDate.of(dateField.getValue().getYear(), dateField.getValue().getMonthValue(),
							dateField.getValue().getDayOfMonth());
				}
				if (weightField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, progressPane.getScene().getWindow(), "Form Error!",
							"Please enter your new weight");
					return;
				} else {
					weight = Double.parseDouble(weightField.getText().replace(",", "."));
				}

				loggedUser = gymService.getActualUser();
				userProgress = new UserProgress(loggedUser, date, weight);
				System.out.println("---- Client Scene Creator --- Add new weight----");
				System.out.println(userProgress.toString());
				gymService.addProgressWeight(userProgress);

			}

		});

		logoutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LoginForm login = new LoginForm();
				try {
					progressPane.getScene().getWindow().hide();
					login.start(new Stage());

				} catch (Exception exception) {
					exception.printStackTrace();
				}

			}

		});

	}

	/**
	 * Display alerts for users.
	 * 
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
