import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class App extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {


        // load the GUI (like below) when server is started up
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainController.fxml"));
        MainSceneController controller = new MainSceneController(); // Your controller class instance
        loader.setController(controller);
        Parent root = loader.load();

        primaryStage.setTitle("My JavaFX Application");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}