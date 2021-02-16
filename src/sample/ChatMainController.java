package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import sample.database.Database;

import java.util.List;

public class ChatMainController extends Controller{


    @FXML
    private Button button_refresh;
    @FXML
    private TextArea list_of_people;
    @FXML
    private Label label_login;
    @FXML
    private Label lbl_logout;

    public ChatMainController(){
        //2sposob lebo fxml este neni vytvoreny a spusta sa uz konstruktor tak preto casovac
        /*
        PauseTransition pause = new PauseTransition(Duration.seconds(0.01));
        pause.setOnFinished(event -> label_login.setText(loginStatic));
        pause.play();
         */
    }


    public void closeWindow(MouseEvent mouseEvent) {
        lbl_logout.getScene().getWindow().hide();
    }

    public void setName(){
        label_login.setText(loginStatic);
    }

    public void setPeople(){
        List<String> list = new Database().AllUsers();
        for(String i : list){
            list_of_people.appendText(i+ '\n');
            //System.out.println(i);
        }
    }


    public void refreshMessages() {
        Timeline t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(15000),
                (ActionEvent event) -> {
                    System.out.println("AHOJ");
                }
        ));
        t.play();
    }
}
