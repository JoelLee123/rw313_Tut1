import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class ChatGUI {

    // update combo box here

    @FXML
    private TextArea MessageInput;

    @FXML
    private TextArea TextChatOutput;

    @FXML
    private Button btnCloseChat;

    @FXML
    private Button btnSendMessage;

    @FXML
    private ListView<?> chatsAvailable;

    @FXML
    void btnCloseChatClicked(ActionEvent event) {
        // client leaves
        // client should leave array list of clients saved in server class


    }

    @FXML
    void btnSendMessageClicked(ActionEvent event) {
        //call method in client class
    }

}
