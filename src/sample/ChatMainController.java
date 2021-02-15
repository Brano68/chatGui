package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ChatMainController{


    @FXML
    private Label label_login;
    @FXML
    private Label lbl_logout;

    public ChatMainController(){

    }


    public void closeWindow(MouseEvent mouseEvent) {
        lbl_logout.getScene().getWindow().hide();
    }
}
