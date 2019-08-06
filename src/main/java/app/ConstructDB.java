package main.java.app;

import java.sql.*;

public class ConstructDB {
    Connection connection;

    public ConstructDB(){
        //Get Connection
        connection = ConnectToDB.getConnection();

        // TEST
        // getAllTablesFromDB();

        //create authors
        createAuthorsTable();

        //create publishers
        createPublishersTable();

        //create titles
        createTitleTable();

        //create authorISBN
        createAuthorISBN();

        // Close Connection
        ConnectToDB.closeConnection(connection);
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
        String createTitle = "create table titles (isbn char(10) primary key, title varchar(500), editionNumber int, year char(4), publisherID int references publishers(publisherID), price NUMERIC(8,2));";
        createTable("titles", createTitle);
    }

    public void createAuthorISBN(){
        String createAuthorISBN = "create table authorISBN (authorID int references authors(authorID), isbn char(10) references titles(isbn));";
        createTable("authorISBN", createAuthorISBN);
    }

    //create table
    public boolean createTable(String tableName, String createStatement){
        if (!isTableExist2(tableName)){
            System.out.println("Creating table: " + tableName);
            return runStatement(createStatement);
        }
        System.out.println("Table exists: " + tableName + " exists, skipping create!");
        return false;
    }

    // check if table exists
    public boolean isTableExist2(String tableName){
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet rs = dbm.getTables(null, null, tableName, null);
            if (rs.next()) {
                System.out.println("Table exists");
                return true;
            } else {
                System.out.println("Table does not exist");
                return false;
            }
        } catch (SQLException e){
            System.out.println("We are fucked!");
        }
        return false;
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


    // Test Method
    public void getAllTablesFromDB(){
        String st = "select * from information_schema.tables;";
        try{
            PreparedStatement ps = connection.prepareStatement(st);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println(rs.getString(1)
                        + "||" + rs.getString(2)
                        + "||" + rs.getString(3));

            }
        } catch (SQLException e){
            System.out.println("ERRRRRORRRr");
        }

    }

}
