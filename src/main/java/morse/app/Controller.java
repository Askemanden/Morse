package morse.app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller implements Initializable {

    @FXML
    VBox buttonContainer;
    @FXML
    Button joinButton;
    @FXML
    Button hostButton;
    @FXML
    Button translatorButton;
    @FXML
    Button settingsButton;
    @FXML
    Button exitButton;
    @FXML
    VBox joinContainer;
    @FXML
    TextField chatID;
    @FXML
    TextField chatCode;
    @FXML
    Button joinChatButton;
    @FXML
    VBox hostContainer;
    @FXML
    TextField ChatName;
    @FXML
    Label hostChatID;
    @FXML
    Label hostChatCode;
    @FXML
    Button hostChatButton;
    @FXML
    VBox translatorContainer;
    @FXML
    TextArea inputTextArea;
    @FXML
    TextArea outputTextArea;
    @FXML
    Button playTranslated;
    @FXML
    VBox settingsContainer;
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
    @FXML
    HBox messageInputContainer;
    @FXML
    TextArea messageInput;
    @FXML
    Button sendButton;

    // If the user is hosting a chat, this will be the host object
    Host host;
    // If the user is joining a chat, this will be the client object
    Client client;

    final Node[] sideBar = new Node[4];
    byte[] morse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sidebar containers setup
        sideBar[0] = joinContainer;
        sideBar[1] = hostContainer;
        sideBar[2] = translatorContainer;
        sideBar[3] = settingsContainer;
        
        // Sidebar buttons
        joinButton.setOnAction(event -> toggleSideBar(0));
        hostButton.setOnAction(event -> toggleSideBar(1));
        translatorButton.setOnAction(event -> toggleSideBar(2));
        settingsButton.setOnAction(event -> toggleSideBar(3));
        exitButton.setOnAction(event -> System.exit(0));

        // Sidebar menus
        // Join sidebar
        joinChatButton.setOnAction(event -> joinChat());

        // Host sidebar
        hostChatButton.setOnAction(event -> hostChat());

        // Translator sidebar
        inputTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            byte[] newMorse = Morse.encode(newValue);
            morse = newMorse;
            outputTextArea.setText(Morse.formatEncoded(newMorse));
        });
        playTranslated.setOnAction(event -> SoundPlayer.playSequence(morse));

        // Settings sidebar
        // TODO Unimplemented

        // Chat controlls
        // TODO Unimplemented
        messageInput.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = Morse.cleanFormatted(newValue);
            if(newValue.length() > 0 && newValue.charAt(newValue.length() - 1) == '\n'){
                newValue = newValue.substring(0, newValue.length() - 2); // Remove newline
                sendMessage();
            }
            messageInput.setText(newValue);
        });
        sendButton.setOnAction(event -> sendMessage());

    }

    private void joinChat(){
        String id = chatID.getText();
        String code = chatCode.getText();
        // TODO Unimplemented
    }

    private void hostChat(){
        host = new Host(chatName.getText());
        hostChatID.setText(host.networkName);
        hostChatCode.setText(String.valueOf(host.port));
    }
    
    private void sendMessage() {
        String message = messageInput.getText();
        messageInput.clear();
        // TODO Unimplemented
    }

    public void addMessage(String message){
        Label newMessage = new Label(message);
        messagesContainer.getChildren().add(newMessage);
        messages.vvalueProperty().bind(messagesContainer.heightProperty());
    }

    private void toggleSideBar(int index){
        for(int i = 0; i < sideBar.length; i++){
            if(i == index){
                changeVisibility(sideBar[i]);
            }else{
                hide(sideBar[i]);
            }
        }
    }

    private void changeVisibility(Node node){
        node.setVisible(!node.isVisible());
        node.setManaged(!node.isManaged());
    }

    private void hide(Node node){
        node.setVisible(false);
        node.setManaged(false);
    }

    private void show(Node node){
        node.setVisible(true);
        node.setManaged(true);
    }
    
}
