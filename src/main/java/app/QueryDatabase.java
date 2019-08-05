package main.java.app;

import main.java.models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryDatabase {

    public QueryDatabase(){

    }

    public void getAllAuthors(String orderInfo){
        Connection connection = ConnectToDB.getConnection();
        try {
            String s = "select * from authors order by ?;";
            PreparedStatement ps = connection.prepareStatement(s);
            ps.setString(1, orderInfo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Author a = new Author();
                a.setAuthorID(rs.getInt("authorID"));
                a.setFirstName(rs.getString("firstName"));
                a.setLastName("lastName");
            }
            System.out.println(rs.findColumn("lastName"));
        } catch (SQLException e){

        }

        ConnectToDB.closeConnection(connection);
    }
}
