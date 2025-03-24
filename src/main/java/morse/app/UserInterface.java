package morse.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserInterface extends Application{

    @Override
    public void start(Stage stage) throws Exception {

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui.fxml"));

        Parent root = loader.load();

        // Create a scene with the loaded FXML
        Scene scene = new Scene(root);

        stage.setMinWidth(420.0);
        stage.setMinHeight(200.0);

        // Set up the primary stage
        stage.setTitle("My JavaFX App");
        stage.setScene(scene);
        stage.show();
    }

    public static void run(){
        launch();
    }
    
}
