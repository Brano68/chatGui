package sample.database;

import sample.entity.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String nameOfDatabase = "mysqluser";
    private String passwordOfDatabase = "Kosice2021!";

    private Connection connection;
    private String login = "Brano";
    private String password = "123456789"; //zmenene na 111111111

    //main for creating a user
    public static void main(String[] args) {
        Database database = new Database();
        //System.out.println(database.createConnection());
        //database.insertNewUser();
        //database.printAllUsers();
        //System.out.println(database.login(database.login, database.password));
        //System.out.println(database.changePassword("123456789", "Brano", "111111111"));
        //System.out.println(database.login("Peterik", "111111111"));
        //database.printAllUsers();

        //System.out.println(database.getUserId("Brano"));

        //database.sendMessage(database.getUserId("Brano"), "Simon", "jak sa mame?");
        //database.sendMessage(database.getUserId("Brano"), "DANKO", "dobre ranko aj tebe Danko :)");
        List<Message> list = database.getMyMessages("Brano");

        System.out.println("Your messages: ");
        if(list.isEmpty()){
            System.out.println("You do not have any messages!!!");
        }else{
            for(Message i : list) {
                if (list != null) {
                    System.out.println("ID: " + i.getId() + " Date: " + i.getDt() + " Sender: " + i.getFrom() +
                            " Receiver: " + i.getTo() + " Text: " + i.getText());

                }
        }
    }

        //database.deleteAllMyMessages("Brano");
    }


    //method for creating a connection
    public boolean createConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, nameOfDatabase, passwordOfDatabase);
            if(connection != null){
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    //method for creating a new user
    public boolean insertNewUser(){
        if(createConnection()){
            String query = "INSERT INTO user (login, password) VALUES (?, ?)";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, login);
                ps.setString(2, MD5.getMd5(password));
                int result = ps.executeUpdate();
                System.out.println(result);
                connection.close();
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }


    //method who is in the table user
    public void printAllUsers(){
        if(createConnection()){
            String query = "SELECT * from user";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String name = rs.getString("login");
                    String pas = rs.getString("password");
                    System.out.println(name + " " + pas);
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println("Something is wrong");
        }
    }


    //method for finding out if the users name and password is correct
    public boolean login(String login, String password){
        String hash = MD5.getMd5(password);
        if(createConnection()){
            String query = "SELECT * from user";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String name = rs.getString("login");
                    String pas = rs.getString("password");
                    if(name.equals(login) && pas.equals(hash)){
                        return true;
                    }
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }


    //method for changing a password
    public boolean changePassword(String oldPassword, String userName, String newPassword){
        if(createConnection()){
            String query = "UPDATE user SET password = ? WHERE login = ? AND password = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, MD5.getMd5(newPassword));
                ps.setString(2, userName);
                ps.setString(3, MD5.getMd5(oldPassword));
                int result = ps.executeUpdate();
                System.out.println(result);
                connection.close();
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }


    public boolean sendMessage(int from, String toUser, String text){
        if(text==null || text.equals(""))
            return false;
        int to = getUserId(toUser);
        if(to==-1)
            return false;

        if(createConnection()){
            String query = "INSERT INTO message (fromUser, toUser, text) VALUES (?, ?, ?)";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, from);
                ps.setInt(2, to);
                ps.setString(3, text);
                int result = ps.executeUpdate();
                System.out.println(result);
                connection.close();
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println("Something wrong");
        }
        return false;
    }


    public int getUserId(String login){
        if(login.equals("")){
            return -1;
        }
        String query = "SELECT user.id from user where login like ?";

        if(createConnection()){
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, login);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    int idecko = rs.getInt("id");
                    //System.out.println("id" + idecko);
                    return idecko;
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println("Something is wrong");
        }
        return -1;
    }


    public List<Message> getMyMessages(String login){

        if(login.equals("")){
            return null;
        }

        List<Message> list = new ArrayList<>();

        if(createConnection()){
            String query = "SELECT message.id, message.dt, message.fromUser, message.toUser, message.text, " +
                    " user.login AS Sender FROM message INNER JOIN user ON user.id = message.fromUser " +
                    " WHERE toUser = ?";
            //System.out.println(query);
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1,getUserId(login));
                //System.out.println(ps);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id");
                    Date date = rs.getDate("dt");
                    String fromUser = rs.getString("Sender");
                    int toUser = rs.getInt("toUser");
                    String text = rs.getString("text");
                    //System.out.println(id + " " + date + " " + fromUser +" " + login + " " + text);
                    Message message = new Message(id, fromUser, login, date, text);
                    list.add(message);
                }
                connection.close();
                // volame metodu deleteAllMyMessages
                deleteAllMyMessages(login);
                return list;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println("Something is wrong");
        }

        return null;
    }


    public void deleteAllMyMessages(String login){
        if(login.equals("")){
            System.out.println("Write your login");
            return;
        }

        if(createConnection()){
            String query = "DELETE FROM message WHERE toUser = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, getUserId(login));
                int result = ps.executeUpdate();
                System.out.println(result);
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    //method who is in the table user
    public List<String> AllUsers(){
        List<String> list = new ArrayList<>();
        if(createConnection()){
            String query = "SELECT login from user";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String name = rs.getString("login");
                    list.add(name);
                }
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println("Something is wrong");
        }
        return list;
    }

}
