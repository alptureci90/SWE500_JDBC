package main.java.app;

import java.sql.*;

public class ConstructDB {
    Connection connection;


    public ConstructDB(){
        //Get Connection
        connection = ConnectToDB.getConnection();

        //create authors
        createAuthors();

        //create authorISBN
        createISBN();
    }

    public void createAuthors(){
        if (connection != null){
            try{
                //Create Authors Table
                String createAuthor = "create table authors (authorID serial primary key, firstName varchar(20), lastName varchar(20));";
                PreparedStatement statement = connection.prepareStatement(createAuthor);
                statement.execute();
            } catch (SQLException e){
                System.out.println("Error creating table: authors");
                System.out.println(e.getMessage());
                ConnectToDB.closeConnection(connection);
            }
        }
    }

    public void createPublishers(){
        if(connection != null){
            // Create Publishers Table
            String createPublishers = "create table publishers(publisherID serial primary key , publisherName char(100));";
            PreparedStatement statement = connection.prepareStatement(createPublishers);
            statement.execute()
        }
    }

    public void createISBN(){
        String createAuthor = "create table publishers(publisherID serial primary key , publisherName char(100));";
        runStatement(createAuthor);
    }

    // check if table exists
    public boolean checkIfTableExits(String tableName){
        String statement =

        return false;
    }

    // returns success or failure
    public boolean runStatement(String statement){
        if (connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(statement);
                preparedStatement.execute();
            } catch (SQLException e){
                System.out.println("Error creating table: authors");
                System.out.println(e.getMessage());
                ConnectToDB.closeConnection(connection);
            }
        }
    }

    // Returns a result set
    public ResultSet executeQuery(String statement){
        if (connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(statement);
                ResultSet resultSet =  preparedStatement.executeQuery();
            } catch (SQLException e){
                System.out.println("Error creating table: authors");
                System.out.println(e.getMessage());
                ConnectToDB.closeConnection(connection);
                return null;
            }
        }
    }

}
