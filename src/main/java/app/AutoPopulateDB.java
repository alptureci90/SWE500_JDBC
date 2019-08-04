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
        pushRecord();


    }

    public void fillPublishers(){

    }

    public void fillTitles(){

    }

    public void fillAuthorISBN(){

    }

    public void pushRecord(){
        String s = "INSERT INTO authors (firstName, lastName) values ('Dan','Brown')";
        try {
            PreparedStatement statement = connection.prepareStatement(s);
            statement.execute();
        } catch (SQLException e){
            System.out.println("Error pushing record");
            System.out.println(e.getMessage());
        }

    }
}
