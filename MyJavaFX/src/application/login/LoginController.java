package application.login;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	protected void handleSubmitButtonAction() {
		System.out.println("Username: " + username.getText());
		System.out.println("Password: " + password.getText());
	}
}
