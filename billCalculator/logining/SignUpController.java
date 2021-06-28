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
import java.sql.SQLException;

public class SignUpController {

    @FXML
    private Button signUpButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button goToBack;

    /**
     * Реализует нажания кнопок
     * Кнопку входа
     * Кнопку возвращения на главный экран
     */
    @FXML
    void initialize() {
        signUpButton.setOnAction(event -> {
            try {
                signUpNewUser();

                signUpButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/kursTemp/sample.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            goToBack.setOnAction(event1 -> {
                goToBack.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/kursTemp/welcome.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            });
        });
    }

    /**
     * Регистрация нового пользователя
     */
    private void signUpNewUser() throws SQLException, ClassNotFoundException {
        LogDbHandler logDbHandler = new LogDbHandler();
        String username = loginField.getText();
        String password = passwordField.getText();

        User user = new User(username, password);
        logDbHandler.signUpUser(user);
    }
}
