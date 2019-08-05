package main.java.app;

import java.sql.*;

public class ConstructDB {
    Connection connection;
// TODO: ADD Foreign KEYS

    public ConstructDB(){
        //Get Connection
        connection = ConnectToDB.getConnection();

        //create authors
        createAuthorsTable();

        //create publishers
        createPublishersTable();

        //create titles
        createTitleTable();

        //create authorISBN
        createAuthorISBN();

        // Fill
    }

    public void createAuthorsTable(){
        //create authors
        String createAuthor = "create table authors (authorID serial primary key, firstName varchar(20), lastName varchar(20));";
        createTable("authors", createAuthor);
    }

    public void createPublishersTable(){
        // Create Publishers Table
        String createPublishers = "create table publishers(publisherID serial primary key , publisherName char(100));";
        createTable("publishers", createPublishers);
    }

    public void createTitleTable(){
        // Create Title Table
        String createTitle = "create table titles (isbn char(10) primary key, title varchar(500), editionNumber int, year char(4), publisherID int, price NUMERIC(8,2));";
        createTable("titles", createTitle);
    }

    public void createAuthorISBN(){
        String createAuthorISBN = "create table authorISBN (authorID int, isbn char(10));";
        createTable("authorISBN", createAuthorISBN);
    }

    //create table
    public boolean createTable(String tableName, String createStatement){
        if (!isTableExist(tableName)){
            System.out.println("Creating table: " + tableName);
            return runStatement(createStatement);
        }
        System.out.println("Table exists: " + tableName + " exists, skipping create!");
        return false;
    }

    // check if table exists
    public boolean isTableExist(String tableName){
        String statement = "select exists(select 1 from information_schema.tables where table_name = '" + tableName + "');";
        // TODO: buraya bak. bunu duzeltmem lazim
        ConnectToDB.closeConnection(connection);
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
