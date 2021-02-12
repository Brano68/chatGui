package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.database.Database;

public class Controller {
    @FXML
    private Label labelCorrect;
    @FXML
    private TextField textLogin;
    @FXML
    private PasswordField passwordLogin;
    @FXML
    private Button loginButton;


    public void pressLoginButton(ActionEvent event) {
        //System.out.println("AHOJ");
        String login = textLogin.getText().trim();
        String password = passwordLogin.getText().trim();
        if(login.length()>0 && password.length()>0){
            Database database = new Database();
            boolean areYouLogin = database.login(login, password);
            if(areYouLogin){
                System.out.println("You are there");
            }else {
                System.out.println("Something wrong!!!");
                labelCorrect.setVisible(true);
            }
        }
    }
}
