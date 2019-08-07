package main.java.app;

import main.java.models.Author;
import main.java.models.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PublisherService{

    QueryDatabase queryDatabase;

    public PublisherService(){
        queryDatabase = new QueryDatabase();
    }

    public void getAllPublishers(String orderInfo){
        String STATEMENT = "SELECT * FROM publishers order by publishername " + orderInfo;
        ResultSet resultSet = queryDatabase.executeQuery(STATEMENT);
        if (resultSet!=null) {
            ArrayList<Publisher> publisherArrayList = mapResultSet2PublisherList(resultSet);
            printResults(publisherArrayList);
        } else{
            System.out.println("Unsuccessful retrieving ALL PUBLISHERS");
        }
    }

    public void getPublisher(int pubID){
        String STATEMENT = "SELECT * FROM publishers where publisherID = " + pubID;
        ResultSet resultSet = queryDatabase.executeQuery(STATEMENT);
        if (resultSet!=null) {
            ArrayList<Publisher> publisherArrayList = mapResultSet2PublisherList(resultSet);
            printResults(publisherArrayList);
        } else{
            System.out.println("Unsuccessful retrieving PUBLISHER");
        }
    }

    public void addPublisher(String publisherName){
        String STATEMENT = "INSERT INTO publishers (publisherName) values ('"+ publisherName +"');";
        if (queryDatabase.execute(STATEMENT)){
            System.out.println("Adding to the publishers: SUCCESS!");
        }
        System.out.println("FAILURE!");
    }

    private ArrayList<Publisher> mapResultSet2PublisherList(ResultSet resultSet){
        ArrayList<Publisher> publisherArrayList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Publisher publisher = new Publisher();
                publisher.setPublisherID(resultSet.getInt("publisherID"));
                publisher.setPublisherName(resultSet.getString("publishername"));
                publisherArrayList.add(publisher);
            }
            return publisherArrayList;
        } catch (SQLException e){
            System.out.println("Error in mapping to ArrayListAuthor");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void printResults(ArrayList<Publisher> publisherArrayList){
        if (publisherArrayList != null) {
            String header = "|| publisherID || Publisher Name ||";
            System.out.println(header);
            for (Publisher p : publisherArrayList) {
                System.out.println(p.toString());
            }
        } else {System.out.println("Publisher List is Empty!");}
    }
}
