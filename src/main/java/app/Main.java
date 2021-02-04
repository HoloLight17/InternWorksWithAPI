package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(new File("src/main/java/app/styles/mainSceneStyle.fxml").toURL());
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Currency quotes and weather");
        primaryStage.getIcons().add(new Image("https://im0-tub-ru.yandex.net/i?id=34a8e33214d557260d02adc4ca8ecc49&n=13&exp=1"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
