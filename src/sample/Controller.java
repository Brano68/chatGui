package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.Database;

import java.io.IOException;

public class Controller {
    @FXML
    private Label labelCorrect;
    @FXML
    private TextField textLogin;
    @FXML
    private PasswordField passwordLogin;
    @FXML
    private Button loginButton;

    ///public
    public static String loginStatic = "";


    public void pressLoginButton(ActionEvent event) throws IOException {
        //System.out.println("AHOJ");
        String login = textLogin.getText().trim();
        String password = passwordLogin.getText().trim();
        if(login.length()>0 && password.length()>0){
            Database database = new Database();
            boolean areYouLogin = database.login(login, password);
            if(areYouLogin){
                System.out.println("You are there");
                loginStatic = textLogin.getText();
                ///
                //first way closing the first window
                loginButton.getScene().getWindow().hide();

                //second way closing the first window
                //Stage stage = (Stage) loginButton.getScene().getWindow();
                //stage.close();

                //opening a new formular
                openTheSecondWindow();
                ///
            }else {
                System.out.println("Something wrong!!!");
                labelCorrect.setVisible(true);
            }
        }
    }

    private void openTheSecondWindow() throws IOException {
        /*
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("chatMain.fxml"));
        primaryStage.setTitle("CHATING");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
         */

        FXMLLoader root = new FXMLLoader();
        root.setLocation(getClass().getResource("chatMain.fxml"));
        Stage stage = new Stage();
        stage.setTitle("CHATING");
        stage.setScene(new Scene(root.load(), 600, 600));
        stage.setResizable(false);
        stage.show();
        ChatMainController chatMainController = root.getController();
        chatMainController.setName();
        chatMainController.setPeople();
        chatMainController.setCHOICE();
        chatMainController.refreshMessages();
    }


}
