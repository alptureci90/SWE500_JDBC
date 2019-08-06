package main.java.app;

import main.java.models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorService {

    Connection connection;

    public AuthorService(){
        connection = ConnectToDB.getConnection();
    }

    public ArrayList<Author> getAllAuthors(){
        String defaultOrder = "ASC";
        return getAllAuthors(defaultOrder);
    }

    public ArrayList<Author> getAllAuthors(String orderBy){
        String STATEMENT = "SELECT * FROM authors order by" + orderBy;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(STATEMENT);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSet2AuthorList(resultSet);
        } catch (SQLException e){
            System.out.println("Unsuccessful retrieving ALL AUTHORS");
            return null;
        }
    }

    public ArrayList<Author> mapResultSet2AuthorList(ResultSet resultSet){
        ArrayList<Author> authorArrayList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Author author = new Author();
                author.setAuthorID(resultSet.getInt("authorID"));
                author.setFirstName(resultSet.getString("firstName"));
                author.setLastName(resultSet.getString("lastName"));
                authorArrayList.add(author);
            }
            return authorArrayList;
        } catch (SQLException e){
            System.out.println("Error in mapping to ArrayListAuthor");
        }
        return null;
    }
}
