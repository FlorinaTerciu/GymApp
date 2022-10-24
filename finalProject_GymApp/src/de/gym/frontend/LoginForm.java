package de.gym.frontend;

import java.util.ArrayList;
import java.util.List;

import de.gym.backend.User;
import de.gym.backend.UserDAO;
import de.gym.backend.UserDAOwithDB;
import de.gym.middle.GymService;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Login form is the primary stage of the gym App. As a user with the admin
 * role, I am redirected to registration page and user overview. As a user with
 * the trainer role, I am redirected to the page where I have the possibility to
 * add new courses. As a user with the classic role, I am redirected to the page
 * where I can register to available courses or give a new entry regarding my
 * progress.
 * 
 * @author florina
 *
 */
public class LoginForm extends Application {
	/** UserDAO object */
	private UserDAO userDB = new UserDAOwithDB();
	/** List with all users */
	private List<User> allUsers = new ArrayList<User>();
	/** Gym service object */
	private GymService gymService = new GymService();
	/** Primary Application Stage */
	private Stage loginStage;

	/**
	 * Create the login form and set as primary stage.
	 */
	@Override
	public void start(Stage loginStage) throws Exception {
		loginStage.setTitle("Gym App");
		this.loginStage = loginStage;
		// Create the login form grid pane
		GridPane loginGridPane = createLoginFormPane();
		// Add UI controls to the login form grid pane
		addUIControls(loginGridPane);
		// Create a scene with login form grid pane as the root node
		Scene scene = new Scene(loginGridPane, 900, 600);
		
		scene.getStylesheets().add("file:ressources/style.css");
		// Set the scene in primary stage
		loginStage.getIcons().add(new Image("file:ressources/icongym.png"));
		loginStage.setScene(scene);

		loginStage.show();

	}

	/**
	 * Create login grid pane
	 * 
	 * @return
	 */
	private GridPane createLoginFormPane() {
		// Instantiate a new Grid Pane
		GridPane loginGridPane = new GridPane();

		// Position the pane at the center of the screen, both vertically and
		// horizontally
		loginGridPane.setAlignment(Pos.CENTER);

		// Set a padding of 20px on each side
		loginGridPane.setPadding(new Insets(40, 400, 100, 400));

		// Set the horizontal gap between columns
		loginGridPane.setHgap(10);

		// Set the vertical gap between rows
		loginGridPane.setVgap(10);

		// Add Column Constraints

		// columnOneConstraints will be applied to all the nodes placed in column one.
		ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.RIGHT);

		// columnTwoConstraints will be applied to all the nodes placed in column two.
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(200, 200, Double.MAX_VALUE);
		columnTwoConstrains.setHgrow(Priority.ALWAYS);

		loginGridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

		return loginGridPane;
	}

	private void addUIControls(GridPane loginGridPane) {
		// Add Header
		Label headerLabel = new Label("Login Form");
		headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		loginGridPane.add(headerLabel, 0, 0, 2, 1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		// Add username label
		Label usernameLabel = new Label("Username : ");
		loginGridPane.add(usernameLabel, 0, 1);

		// Add username text field
		TextField usernameField = new TextField();
		usernameField.setPrefHeight(40);
		loginGridPane.add(usernameField, 1, 1);

		// Add password label
		Label passwordLabel = new Label("Password : ");
		loginGridPane.add(passwordLabel, 0, 2);

		// Add Password Field
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefHeight(40);
		loginGridPane.add(passwordField, 1, 2);

		// Add Login Button
		Button loginButton = new Button("Login");
		loginButton.setPrefHeight(40);
		loginButton.setDefaultButton(true);
		loginButton.setPrefWidth(100);
		loginGridPane.add(loginButton, 0, 3, 2, 1);
		GridPane.setHalignment(loginButton, HPos.CENTER);
		GridPane.setMargin(loginButton, new Insets(20, 0, 20, 0));

		allUsers = userDB.getAllUsers();

		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			String username;
			String password;

			@Override
			public void handle(ActionEvent event) {
				if (usernameField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, loginGridPane.getScene().getWindow(), "Form Error!",
							"Please enter your username");
					return;
				} else {
					username = usernameField.getText();
				}

				if (passwordField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, loginGridPane.getScene().getWindow(), "Form Error!",
							"Please enter your password");
					return;
				} else {
					password = passwordField.getText();
				}

				for (User user : allUsers) {
					
					if (user.getUsername().equals(username)
							&& user.getPassword().equals(userDB.passwordEncryptionSHA(password))) {
						gymService.setActualUser(user);
						loginStage.setScene(gymService.getNextScene());
						break;
					}
					
					
				}
				if(gymService.getActualUser()==null) {
					
				showAlert(Alert.AlertType.ERROR, loginGridPane.getScene().getWindow(), "Form Error!",
						"Username / Password not correct");
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
