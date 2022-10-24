package de.gym.frontend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import de.gym.backend.Employee;
import de.gym.backend.Role;
import de.gym.backend.Trainer;
import de.gym.backend.User;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Registration form, usually used for registering users, by an admin. Based on
 * the user role, the registration form requires additional information to be
 * filled in. In addition to the registration form, a user having the admin role
 * can obtain a full overview of the registered users.
 * 
 * @author florina
 *
 */
public class AdminSceneCreator {

	/** List containing all user roles */
	private List<Role> allRoles;
	/** List containing all user data */
	private List<User> allUsers;
	/** User object */
	private User user;
	/** Employee object */
	private Employee employee;
	/** Trainer object */
	private Trainer trainer;
	/** Gym service object */
	private GymService gymService;

	/**
	 * Initialization of the service in constructor.
	 * 
	 * @param gymService
	 */
	public AdminSceneCreator(GymService gymService) {
		this.gymService = gymService;
	}

	/**
	 * Method used to create and design the Admin Scene.
	 * 
	 * @return
	 */
	public Scene start() {
		// Creation of the admin tabs
		TabPane adminTabs = new TabPane();

		// Create the registration form grid pane
		GridPane registrationPane = createRegistrationFormPane();
		GridPane overviewPane = createOverviewFormPane();

		Tab registrationTab = new Tab("Registration", registrationPane);
		Tab allUsersTab = new Tab("Users Overview", overviewPane);
		adminTabs.getTabs().addAll(registrationTab, allUsersTab);

		// Add UI controls to the registration form grid pane
		addUIControlsRegistration(registrationPane);
		// Add UI controls to the user overview grid pane
		addUIControlsOverview(overviewPane);

		// Create a scene with registration form grid pane as the root node
		Scene scene = new Scene(adminTabs, 900, 600);
		return scene;
	}

	/**
	 * The method adds the UI controls for the user overview grid pane.
	 * 
	 * @param overviewPane
	 */
	private void addUIControlsOverview(GridPane overviewPane) {
		// Add Header
		Label headerLabel = new Label("Users Overview");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		overviewPane.add(headerLabel, 0, 0, 2, 1);

		// Logout Button
		Button logoutButton = new Button("Logout");
		logoutButton.setPrefHeight(40);
		logoutButton.setDefaultButton(true);
		logoutButton.setPrefWidth(100);
		overviewPane.add(logoutButton, 0, 0, 2, 1);

		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		// VBox list where specific user information is displayed from the table view
		VBox allUsersPane = createAllUsersView();
		overviewPane.add(allUsersPane, 1, 1);

		// Logout button set action
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LoginForm login = new LoginForm();
				try {
					overviewPane.getScene().getWindow().hide();
					login.start(new Stage());

				} catch (Exception exception) {
					exception.printStackTrace();
				}

			}

		});
	}

	/**
	 * Define the columns and populate with user data, the tableView
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private VBox createAllUsersView() {
		TableView<User> usersTableView = new TableView<User>();

		// Set user's first name column details
		TableColumn<User, String> forenameColumn = new TableColumn<>("Forename");
		forenameColumn.setCellValueFactory(new PropertyValueFactory<>("forename"));
		// Set user's last name column details
		TableColumn<User, String> surnameColumn = new TableColumn<>("Surname");
		surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
		// Set user's address column details
		TableColumn<User, String> addressColumn = new TableColumn<>("Address");
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		// Set user's birthday column details
		TableColumn<User, LocalDate> birthdayColumn = new TableColumn<>("Birthday");
		birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
		// Set user's email column details
		TableColumn<User, String> emailColumn = new TableColumn<User, String>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		// Add all column headers to the user tableView
		usersTableView.getColumns().addAll(forenameColumn, surnameColumn, addressColumn, birthdayColumn, emailColumn);
		// Populate user tableView with all user's data
		allUsers = gymService.getAllUsers();
		for (User user : allUsers) {
			usersTableView.getItems().add(user);
		}

		// Remove right empty column
		usersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox allUsersPane = new VBox(usersTableView);
		allUsersPane.setPadding(new Insets(20, 20, 20, 20));
		VBox.setVgrow(usersTableView, Priority.ALWAYS);
		return allUsersPane;
	}

	/**
	 * Creation of the registration form pane.
	 * 
	 * @return
	 */
	private GridPane createRegistrationFormPane() {
		// Instantiate a new Grid Pane
		GridPane registrationGridPane = new GridPane();

		// Position the pane at the center of the screen, both vertically and
		// horizontally
		registrationGridPane.setAlignment(Pos.CENTER);

		// Set a padding of 20px on each side
		registrationGridPane.setPadding(new Insets(40, 40, 40, 40));

		// Set the horizontal gap between columns
		registrationGridPane.setHgap(10);

		// Set the vertical gap between rows
		registrationGridPane.setVgap(10);

		// Add Column Constraints

		// columnOneConstraints will be applied to all the nodes placed in column one.
		ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);

		// columnTwoConstraints will be applied to all the nodes placed in column two.
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);

		registrationGridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

		return registrationGridPane;
	}

	/**
	 * Creation of the overview form pane.
	 * 
	 * @return
	 */
	private GridPane createOverviewFormPane() {
		// Instantiate a new Grid Pane
		GridPane overviewGridPane = new GridPane();

		// Position the pane at the center of the screen, both vertically and
		// horizontally
		overviewGridPane.setAlignment(Pos.CENTER);

		// Set a padding of 20px on each side
		overviewGridPane.setPadding(new Insets(40, 40, 40, 40));

		// Set the horizontal gap between columns
		overviewGridPane.setHgap(10);

		// Set the vertical gap between rows
		overviewGridPane.setVgap(10);

		// Add Column Constraints

		// columnOneConstraints will be applied to all the nodes placed in column one.
		ColumnConstraints columnOneConstraints = new ColumnConstraints(10, 10, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);

		// columnTwoConstraints will be applied to all the nodes placed in column two.
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);

		overviewGridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

		return overviewGridPane;
	}

	/**
	 * The method adds the UI controls for the user registration grid pane.
	 * 
	 * @param gridPane
	 */
	private void addUIControlsRegistration(GridPane gridPane) {
		// Add Header
		Label headerLabel = new Label("Registration Form");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		gridPane.add(headerLabel, 0, 0, 2, 1);

		// Logout Button
		Button logoutButton = new Button("Logout");
		logoutButton.setPrefHeight(40);
		logoutButton.setDefaultButton(true);
		logoutButton.setPrefWidth(100);
		gridPane.add(logoutButton, 0, 0, 2, 1);

		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		// Add user role label
		Label roleLabel = new Label("User role : ");
		gridPane.add(roleLabel, 0, 1);

		// Add user role comboBox
		ComboBox<String> roleCombo = new ComboBox<String>();

		allRoles = new ArrayList<Role>();
		allRoles = gymService.getAllRoles();
		for (Role role : allRoles) {
			roleCombo.getItems().add(role.getRoleName());
		}
		// Set default value
		roleCombo.setValue("classic");
		gridPane.add(roleCombo, 1, 1);

		// Add surname label
		Label surnameLabel = new Label("Surname : ");
		gridPane.add(surnameLabel, 0, 2);

		// Add surname text field
		TextField surnameField = new TextField();
		surnameField.setPrefHeight(40);
		gridPane.add(surnameField, 1, 2);

		// Add forename label
		Label forenameLabel = new Label("Forename : ");
		gridPane.add(forenameLabel, 0, 3);

		// Add forename text field
		TextField forenameField = new TextField();
		forenameField.setPrefHeight(40);
		gridPane.add(forenameField, 1, 3);

		// Add address label
		Label addressLabel = new Label("Address : ");
		gridPane.add(addressLabel, 0, 4);

		// Add address text field
		TextField addressField = new TextField();
		addressField.setPrefHeight(40);
		gridPane.add(addressField, 1, 4);

		// Add birthday label
		Label birthdayLabel = new Label("Birthday : ");
		gridPane.add(birthdayLabel, 0, 5);

		// Add birthday text field
		DatePicker birthdayField = new DatePicker();
		birthdayField.setPrefHeight(40);
		birthdayField.setEditable(false);
		gridPane.add(birthdayField, 1, 5);

		// Add username label
		Label usernameLabel = new Label("Username : ");
		gridPane.add(usernameLabel, 0, 6);

		// Add username text field
		TextField usernameField = new TextField();
		usernameField.setPrefHeight(40);
		gridPane.add(usernameField, 1, 6);

		// Add password label
		Label passwordLabel = new Label("Password : ");
		gridPane.add(passwordLabel, 0, 7);

		// Add Password Field
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefHeight(40);
		gridPane.add(passwordField, 1, 7);

		// Add Email label
		Label emailLabel = new Label("Email : ");
		gridPane.add(emailLabel, 0, 8);

		// Add Email text field
		TextField emailField = new TextField();
		emailField.setPrefHeight(40);
		gridPane.add(emailField, 1, 8);

		// Add IBAN label
		Label ibanLabel = new Label("IBAN : ");
		gridPane.add(ibanLabel, 0, 9);

		// Add IBAN text field
		TextField ibanField = new TextField();
		ibanField.setPrefHeight(40);
		gridPane.add(ibanField, 1, 9);

		// Add Submit Button
		Button submitButton = new Button("Submit");
		submitButton.setPrefHeight(40);
		submitButton.setDefaultButton(true);
		submitButton.setPrefWidth(100);
		gridPane.add(submitButton, 0, 10, 2, 1);
		GridPane.setHalignment(submitButton, HPos.CENTER);
		GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			String surname;
			String forename;
			String address;
			LocalDate birthday;
			String username;
			String password;
			String email;
			String IBAN;
			int roleID;
			double weight = 0;
			int height = 0;
			double salary = 0;
			int vacationDays = 0;
			int workingHours = 0;

			@Override
			public void handle(ActionEvent event) {

				if (surnameField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Please enter your surname");
					return;
				} else {
					surname = surnameField.getText();
				}

				if (forenameField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Please enter your forename");
					return;
				} else {
					forename = forenameField.getText();
				}

				if (addressField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Please enter your address");
					return;
				} else {
					address = addressField.getText();
				}

				if (birthdayField.getValue() == null) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Please enter your birthday");
					return;
				} else {
					birthday = LocalDate.of(birthdayField.getValue().getYear(),
							birthdayField.getValue().getMonthValue(), birthdayField.getValue().getDayOfMonth());
				}

				if (usernameField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Please enter your username");
					return;
				} else {
					username = usernameField.getText();
				}

				if (passwordField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Please enter your password");
					return;
				} else {
					password = passwordField.getText();
				}

				if (emailField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Please enter your email");
					return;
				} else {
					email = emailField.getText();
				}

				if (ibanField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!",
							"Please enter your IBAN");
					return;
				} else {
					IBAN = ibanField.getText();
				}

				roleID = allRoles.stream().filter(role -> roleCombo.getValue().equals(role.getRoleName())).findAny()
						.orElse(null).getRole_id();
				System.out.println(roleID);

				// user classic role, add weight, height
				if (roleCombo.getValue().equals("classic")) {
					Stage classicStage = new Stage();
					classicStage.setTitle("User additional information");
					GridPane classicPane = new GridPane();

					// Position the pane at the center of the screen, both vertically and
					// horizontally
					classicPane.setAlignment(Pos.CENTER);

					// Set a padding of 20px on each side
					classicPane.setPadding(new Insets(40, 40, 40, 40));

					// Set the horizontal gap between columns
					classicPane.setHgap(10);

					// Set the vertical gap between rows
					classicPane.setVgap(10);

					// Add Header
					Label headerLabel = new Label("User additional information");
					headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
					classicPane.add(headerLabel, 0, 0, 2, 1);
					GridPane.setHalignment(headerLabel, HPos.CENTER);
					GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

					HBox weightHeight = new HBox();
					// Add height label
					Label heightLabel = new Label("Height in cm : ");
					// gridPane.add(heightLabel, 0, 10);

					// Add height text field
					TextField heightField = new TextField();
					heightField.setPrefHeight(40);

					// Add weight label
					Label weightLabel = new Label("Weight in kg : ");

					// Add weight text field
					TextField weightField = new TextField();
					weightField.setPrefHeight(40);
					weightHeight.getChildren().addAll(heightLabel, heightField, weightLabel, weightField);
					weightHeight.setSpacing(15);
					classicPane.add(weightHeight, 1, 1);

					// Add Register Button
					Button registerButton = new Button("Register");
					registerButton.setPrefHeight(40);
					registerButton.setDefaultButton(true);
					registerButton.setPrefWidth(100);
					classicPane.add(registerButton, 0, 10, 2, 1);
					GridPane.setHalignment(registerButton, HPos.CENTER);
					GridPane.setMargin(registerButton, new Insets(20, 0, 20, 0));

					Scene classicScene = new Scene(classicPane, 600, 300);
					classicStage.setScene(classicScene);
					classicStage.show();

					registerButton.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent arg0) {

							if (weightField.getText().isEmpty()) {
								showAlert(Alert.AlertType.ERROR, classicPane.getScene().getWindow(), "Form Error!",
										"Please enter your weight");
								return;
							} else {
								weight = Double.parseDouble(weightField.getText().replace(",", "."));
							}

							if (heightField.getText().isEmpty()) {
								showAlert(Alert.AlertType.ERROR, classicPane.getScene().getWindow(), "Form Error!",
										"Please enter your height");
								return;
							} else {
								height = Integer.parseInt(heightField.getText());
							}

							user = new User(forename, surname, address, birthday, username, password, email, IBAN,
									weight, height);
							user.setRoleID(roleID);
							System.out.println("----Admin Scene Creator---add classic user---");
							System.out.println(user.toString());
							gymService.addUser(user);

						}

					});

				} else {
					// user trainer role, add salary, vacation days, working hours

					Stage trainerStage = new Stage();
					trainerStage.setTitle("Employee additional information");
					GridPane employeePane = new GridPane();

					// Position the pane at the center of the screen, both vertically and
					// horizontally
					employeePane.setAlignment(Pos.CENTER);

					// Set a padding of 20px on each side
					employeePane.setPadding(new Insets(40, 40, 40, 40));

					// Set the horizontal gap between columns
					employeePane.setHgap(10);

					// Set the vertical gap between rows
					employeePane.setVgap(10);

					// Add Header
					Label headerLabel = new Label("Employee additional information");
					headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
					employeePane.add(headerLabel, 0, 0, 2, 1);
					GridPane.setHalignment(headerLabel, HPos.CENTER);
					GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

					// Add salary label
					Label salaryLabel = new Label("Salary : ");
					employeePane.add(salaryLabel, 0, 1);

					// Add salary text field
					TextField salaryField = new TextField();
					salaryField.setPrefHeight(40);
					employeePane.add(salaryField, 1, 1);

					// Add vacation days label
					Label vacationLabel = new Label("Vacation Days : ");
					employeePane.add(vacationLabel, 0, 2);

					// Add vacation days text field
					TextField vacationField = new TextField();
					vacationField.setPrefHeight(40);
					employeePane.add(vacationField, 1, 2);

					// Add number of working hours per week label
					Label workHoursLabel = new Label("Working hours / week : ");
					employeePane.add(workHoursLabel, 0, 3);

					// Add number of working hours per week text field
					TextField workHoursField = new TextField();
					workHoursField.setPrefHeight(40);
					employeePane.add(workHoursField, 1, 3);

					// Add Register Button
					Button registerButton = new Button("Register");
					registerButton.setPrefHeight(40);
					registerButton.setDefaultButton(true);
					registerButton.setPrefWidth(100);
					employeePane.add(registerButton, 0, 10, 2, 1);
					GridPane.setHalignment(registerButton, HPos.CENTER);
					GridPane.setMargin(registerButton, new Insets(20, 0, 20, 0));

					Scene employeeScene = new Scene(employeePane, 600, 300);
					trainerStage.setScene(employeeScene);
					trainerStage.show();

					registerButton.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {

							if (salaryField.getText().isEmpty()) {
								showAlert(Alert.AlertType.ERROR, employeePane.getScene().getWindow(), "Form Error!",
										"Please enter your salary");
								return;
							} else {
								salary = Double.parseDouble(salaryField.getText().replace(",", "."));
							}

							if (vacationField.getText().isEmpty()) {
								showAlert(Alert.AlertType.ERROR, employeePane.getScene().getWindow(), "Form Error!",
										"Please enter your height");
								return;
							} else {
								vacationDays = Integer.parseInt(vacationField.getText());
							}

							if (workHoursField.getText().isEmpty()) {
								showAlert(Alert.AlertType.ERROR, employeePane.getScene().getWindow(), "Form Error!",
										"Please enter your height");
								return;
							} else {
								workingHours = Integer.parseInt(workHoursField.getText());
							}
							// Add new user admin
							if (roleCombo.getValue().equals("admin")) {
								employee = new Employee(forename, surname, address, birthday, username, password, email,
										IBAN, salary, vacationDays, workingHours);
								employee.setRoleID(roleID);
								System.out.println("----Admin Scene Creator---add employee user---");
								System.out.println(employee.toString());
								gymService.addEmployee(employee);
							} else {
								// Add new trainer
								trainer = new Trainer(forename, surname, address, birthday, username, password, email,
										IBAN, salary, vacationDays, workingHours);
								trainer.setRoleID(roleID);
								trainer.setSalary(salary);
								trainer.setVacationDays(vacationDays);
								System.out.println("----Admin Scene Creator---add trainer user---");
								System.out.println(trainer.toString());
								gymService.addEmployee(trainer);
							}
						}

					});
				}

			}
		});

		logoutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LoginForm login = new LoginForm();
				try {
					gridPane.getScene().getWindow().hide();
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
