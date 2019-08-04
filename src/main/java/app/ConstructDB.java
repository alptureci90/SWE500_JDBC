package main.java.app;

import java.sql.*;

public class ConstructDB {
    Connection connection;


    public ConstructDB(){
        //Get Connection
        connection = ConnectToDB.getConnection();

        //create authors
        createAuthors();

        //create publishers
        createPublishers();

        //create authorISBN
        createISBN();
    }

    public void createAuthors(){
        //create authors
        String createAuthor = "create table authors (authorID serial primary key, firstName varchar(20), lastName varchar(20));";
        createTable("authors", createAuthor);
    }

    public void createPublishers(){
            // Create Publishers Table
            String createPublishers = "create table publishers(publisherID serial primary key , publisherName char(100));";
            createTable("publishers", createPublishers);
    }

    public void createISBN(){
        String createAuthor = "create table publishers(publisherID serial primary key , publisherName char(100));";
        runStatement(createAuthor);
    }

    //create table
    public boolean createTable(String tableName, String createStatement){
        if (!isTableExist(tableName)){
            return runStatement(createStatement);
        }
        return false;
    }

    // check if table exists
    public boolean isTableExist(String tableName){
        String statement = "select exists(select 1 from information_schema.tables where table_name = '" + tableName + "');";
        return runStatement(statement);
    }

    // returns success or failure
    public boolean runStatement(String statement){
        if (connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(statement);
                return preparedStatement.execute();
            } catch (SQLException e){
                System.out.println("Error creating table: authors");
                System.out.println(e.getMessage());
                ConnectToDB.closeConnection(connection);
                return false;
            }
        }
        return false;
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
        return null;
    }

}
