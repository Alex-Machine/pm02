package sample.kursTemp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogController {

    @FXML
    private Button loginInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginAsGuest;

    @FXML
    void initialize() {
        loginInButton.setOnAction(event -> {
            String loginText = loginField.getText().trim();
            String loginPassword = passwordField.getText().trim();

            if (!loginText.equals("") && !loginPassword.equals("")) {
                try {
                    loginUser(loginText, loginPassword);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Login/password ERROR");
            }
        });

        signUpButton.setOnAction(event -> {
            openNewScene("signUp.fxml");
        });

        loginAsGuest.setOnAction(event -> {
            openNewScene("billCalculator.fxml");
        });
    }

    /**
     * Вход с имеющийся учетной записью
     * @param loginText логин пользователя
     * @param loginPassword пароль пользователя
     */
    private void loginUser(String loginText, String loginPassword) throws SQLException, ClassNotFoundException {
        LogDbHandler logDbHandler = new LogDbHandler();
        User user = new User();
        user.setUsername(loginText);
        user.setPassword(loginPassword);
        logDbHandler.getUser(user);
        ResultSet result = logDbHandler.getUser(user);
        int counter = 0;
        while (result.next()) {
            counter++;
        }
        if (counter >= 1) {
            openNewScene("/sample/kursTemp/billCalculator.fxml");
        } else {
            System.out.println("error");
        }
    }

    public void openNewScene(String window) {
        signUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}

