package main.java.app;

import main.java.models.Author;
import main.java.models.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PublisherService {

    Connection connection;

    public PublisherService(){
        connection = ConnectToDB.getConnection();
    }

    public ArrayList<Publisher> getAllPublishers(String orderInfo){
        String STATEMENT = "SELECT * FROM publishers order by publishername " + orderInfo;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(STATEMENT);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSet2PublisherList(resultSet);
        } catch (SQLException e){
            System.out.println("Unsuccessful retrieving ALL AUTHORS");
            return null;
        }
    }

    private ArrayList<Publisher> mapResultSet2PublisherList(ResultSet resultSet){
        ArrayList<Publisher> publisherArrayList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Publisher publisher = new Publisher();
                publisher.setPublisherID(resultSet.getInt("authorID"));
                publisher.setPublisherName(resultSet.getString("publishername"));
                publisherArrayList.add(publisher);
            }
            return publisherArrayList;
        } catch (SQLException e){
            System.out.println("Error in mapping to ArrayListAuthor");
        }
        return null;
    }
}
