package ma.fstt.trackingl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //load scene
        Parent root = FXMLLoader.load(getClass().getResource("Login-view.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("GLOVO");

        //style

        //Remove upper bar
        stage.initStyle(StageStyle.TRANSPARENT);
        //show scene
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}