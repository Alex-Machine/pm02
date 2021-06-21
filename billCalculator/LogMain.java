package sample.kursTemp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Класс-контроллер для представления работы
 * регистрации в приложении
 *
 * @Author Машина А.А.
 */
public class LogMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        primaryStage.setTitle("Вход");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }
}
