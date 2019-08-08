package main.java.app;

import main.java.models.Author;
import main.java.models.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDatabase {
    Connection connection;

    public QueryDatabase(){
        connection = ConnectToDB.getConnection();
    }

    public ResultSet executeQuery(String STATEMENT){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(STATEMENT);
            ResultSet resultSet = preparedStatement.executeQuery();
            connection.close();
            return resultSet;
        } catch (SQLException e){
            System.out.println("Unsuccessful retrieving information");
            ConnectToDB.closeConnection(connection);
            return null;
        }
    }

    public boolean execute(String STATEMENT){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(STATEMENT);
            preparedStatement.execute();
            connection.close();
            return true;
        } catch (SQLException e){
            System.out.println("Unsuccessful retrieving information");
            System.out.println(e.getMessage());
            ConnectToDB.closeConnection(connection);
            return false;
        }
    }

}
