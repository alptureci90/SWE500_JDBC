package main.java.app;

import main.java.models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AutoPopulateDB {

    Connection connection;

    public AutoPopulateDB(){
        connection = ConnectToDB.getConnection();

        // Fill Authors
        fillAuthors();
    }

    public void fillAuthors(){

        Author author = new Author("Dan", "Brown");
        pushAuthor(author.getFirstName(), author.getLastName());


    }

    public void fillPublishers(){

    }

    public void fillTitles(){

    }

    public void fillAuthorISBN(){

    }

    public void pushAuthor(String firstName, String lastName){
        String s = "INSERT INTO authors (firstName, lastName) values (?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(s);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            executeStatement(statement);
        } catch (SQLException e){
            System.out.println("Error pushing record");
            System.out.println(e.getMessage());
        }
    }

    public void pushPublisher(String publisherName){
        String s = "insert into publishers(publisherName) values (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(s);
            statement.setString(1, publisherName);
        } catch (SQLException e){
            System.out.println("Error in prepared statement");
            e.getMessage();
        }
    }

    public boolean executeStatement(PreparedStatement ps){
        try{
            ps.execute();
            System.out.println("Execution Successfull;");
            return true;
        } catch (SQLException e){
            System.out.println("Error in execution");
            System.out.println(e.getMessage());
            System.out.println("Closing Connection");
            ConnectToDB.closeConnection(connection);
            return false;
        }
    }
}
