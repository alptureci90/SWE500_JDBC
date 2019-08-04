package main.java.app;

import main.java.models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDatabase {

    public QueryDatabase(){

    }

    public void getAllAuthors(){
        Connection connection = ConnectToDB.getConnection();
        try {
            String s = "select * from authors;";
            PreparedStatement ps = connection.prepareStatement(s);
            ResultSet rs = ps.executeQuery();
            Author a = new Author(rs.getString("firstName"), rs.getString("lastName"));
            System.out.println(rs.findColumn("lastName"));
        } catch (SQLException e){

        }

        ConnectToDB.closeConnection(connection);
    }
}
