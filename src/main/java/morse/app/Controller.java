package morse.app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class Controller implements Initializable {

    @FXML
    VBox buttonContainer;
    @FXML
    Button translatorButton;
    @FXML
    Button settingsButton;
    @FXML
    Button exitButton;
    @FXML
    VBox translator;
    @FXML
    TextArea inputTextArea;
    @FXML
    TextArea outputTextArea;
    @FXML
    VBox settings;
    @FXML
    VBox messageBox;
    @FXML
    Label chatName;
    @FXML
    ScrollPane messages;
    @FXML
    VBox messagesContainer;
    @FXML
    VBox profile;
    @FXML
    Label username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("tester");
    }
    
    
}
