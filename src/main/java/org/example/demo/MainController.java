package org.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {
    // Login Page
    @FXML
    private TextField InputIP;

    @FXML
    private TextField InputUsername;

    @FXML
    private Button btnJoinServer;

    //------------------------
    // ChatGUI
    @FXML
    private TextArea MessageInput;

    @FXML
    private Button btnSendMessage;

    @FXML
    private Label labelChatWith;

    //-----------------
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainController.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    //-----------------
    // Login Page
    @FXML
    void btnJoinServerClicked(ActionEvent event) throws IOException {
            // once validation is passed
            // open ChatGUI fxml/ GUI
        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("ChatGUI.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load());
        Stage stage2 = new Stage();
        stage2.setTitle("Hello!");
        stage2.setScene(scene2);
        stage2.show();

        // need to close login page
    }

    //---------------------------
    // ChatGui
    @FXML
    void btnSendMessageClicked(ActionEvent event) {

    }
    //---------------------------
}

