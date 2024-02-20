import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainSceneController {

    @FXML
    private TextField InputIP;

    @FXML
    private TextField InputUsername;

    @FXML
    private Button btnExitLogin;

    @FXML
    private Button btnJoinServer;

    @FXML
    void btnJoinServerClicked(ActionEvent event) {
        // validate name method (from server class)
        // validate IP method  (from server class)
        // method client joins server (from client class)
    }

}
