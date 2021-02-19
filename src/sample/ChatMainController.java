package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import sample.database.Database;
import sample.entity.Message;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatMainController extends Controller{


    @FXML
    private TextField new_password;
    @FXML
    private TextField old_password;
    @FXML
    private ComboBox combo_box;
    @FXML
    private TextArea list_of_messages;
    @FXML
    private TextField text_for_sending;
    @FXML
    private TextField receiver;
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
                    refresButtonMethod(null);
                    /*
                    list_of_messages.clear();
                    List<Message> list = new Database().getMyMessages(loginStatic);
                    if(list.isEmpty()){
                        list_of_messages.appendText("You do not have any new messages");
                    }
                    else{
                        for(Message m : list){
                            Date date = m.getDt();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                            String strDate = formatter.format(date);
                            String od = m.getFrom();
                            String komu = loginStatic;
                            String message = m.getText();
                            list_of_messages.appendText(strDate + " " +od + " " + komu + " " + message + '\n');
                        }
                    }

                     */

                }
        ));
        t.play();
    }

    public void sendMessage(ActionEvent event) {
        String message = text_for_sending.getText();
        String prijemca = receiver.getText();
        if(message != null && message != "" && prijemca != null && prijemca != ""){
            Database database = new Database();
            database.sendMessage(database.getUserId(loginStatic), prijemca, message);
        }
        text_for_sending.setText("");
        receiver.setText("");
    }

    public void refresButtonMethod(ActionEvent event) {
        list_of_messages.clear();

        List<Message> list = new Database().getMyMessages(loginStatic);
        if(list.isEmpty()){
            list_of_messages.appendText("You do not have any new messages");
        }
        else{
            /*
            for(Message m : list){
                Date date = m.getDt();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String strDate = formatter.format(date);
                String od = m.getFrom();
                String komu = loginStatic;
                String message = m.getText();
                list_of_messages.appendText(strDate + " " +od + " " + komu + " " + message + '\n');
            }

             */
            //od zadu
            for(int i = list.size()-1; i >= 0 ; i--){
                Message message = list.get(i);
                Date date = message.getDt();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String strDate = formatter.format(date);
                String od = message.getFrom();
                String komu = loginStatic;
                String messagee = message.getText();
                list_of_messages.appendText(strDate + " " +od + " " + komu + " " + messagee + '\n');
            }
        }
    }

    public void setCHOICE(){
        List<String> list = new Database().AllUsers();
        if(list.isEmpty()){
            return;
        }else{
            for(String s : list){
                combo_box.getItems().add(s);
            }
        }

    }

    public void copyName(ActionEvent event) {
        receiver.setText((String)combo_box.getValue());
    }

    public void changePassword(ActionEvent event) {
        String stareHeslo = old_password.getText();
        String noveheslo = new_password.getText();
        if(stareHeslo == null || noveheslo == null || stareHeslo == "" || noveheslo == ""){
            System.out.println("You have not tiped it correctlly!!!");
            return;
        }else{
            if(new Database().changePassword(stareHeslo, loginStatic, noveheslo)){
                System.out.println("Your password has been changed!!!");
            }else{
                System.out.println("Something wrong!!!");
            }
        }
        old_password.setText("");
        new_password.setText("");
    }

    public void addEmogyBall(ActionEvent event) {
        String string = text_for_sending.getText();
        char znak = '\u26BD';
        text_for_sending.setText(string + znak);
    }

    public void addEmogySnowman(ActionEvent event) {
        String string = text_for_sending.getText();
        char znak = '\u26C4';
        text_for_sending.setText(string + znak);
    }


    public void addEmogyHand(ActionEvent event) {
        String string = text_for_sending.getText();
        char znak = '\u270B';
        text_for_sending.setText(string + znak);
    }
}
