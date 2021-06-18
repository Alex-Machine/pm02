package billCalculator;
/**
 *реализует запуск программы для рассчета чаевых
 *
 * @Author Mashina
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("billCalculator.fxml"));
        //    primaryStage.getIcons().add(new Image("billCalculator\\icon.png")); //это не лишний код, но с ним не создается jar-файл
        primaryStage.setTitle("Чаевые");
        primaryStage.setScene(new Scene(root, 441, 371));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
