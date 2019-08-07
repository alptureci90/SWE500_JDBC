package main.java.app;

import java.sql.*;

public class ConnectToDB {

    private static final String DB_NAME = "db_bookstore";
    private static final String USERNAME = "bookstore_admin";
    private static final String PASSWORD = "bookstore_admin";
    private static final String URL = "jdbc:postgresql://localhost:5432/db_bookstore";


    public static boolean testDBConnection(){

        try {
            // Connect
            System.out.println("Creating Connection...");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection Successful!");


            /*PreparedStatement pst = conn.prepareStatement("\\list");
            ResultSet result = pst.executeQuery();*/

            // Close
            System.out.println("Closing Connection...");
            conn.close();
            return true;
        }
        catch (SQLException e){
            System.out.println("Cant Connect to DB!");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Connection getConnection(){
        try {
            // Connect
            System.out.println("Creating Connection...");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Test Connection Successful!");
            return conn;
        }
        catch (SQLException e){
            System.out.println("Cant Connect to DB!");
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static void closeConnection(Connection connection){
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e){
                System.out.println("Error closing the connection");
            }
        }
    }


}
