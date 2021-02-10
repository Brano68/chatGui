package sample.database;

import java.sql.*;

public class Database {
    private String url = "jdbc:mysql://itsovy.sk:3306/chat2021";
    private String nameOfDatabase = "mysqluser";
    private String passwordOfDatabase = "Kosice2021!";

    private Connection connection;
    private String login = "Brano";
    private String password = "123456789";

    //main for creating a user
    public static void main(String[] args) {
        Database database = new Database();
        //System.out.println(database.createConnection());
        //database.insertNewUser();
        //database.printAllUsers();
        //System.out.println(database.login(database.login, database.password));
        //System.out.println(database.changePassword("123456789", "Brano", "111111111"));
        System.out.println(database.login("Peterik", "111111111"));
        database.printAllUsers();
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

}
