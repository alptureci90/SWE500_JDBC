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
    int maxPublisherNameLength = 0;
    int maxId = 0;
    String publisherNameString = "Publisher Name";
    String idString = "publisherID";

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
        } else {
            System.out.println("FAILURE!");
        }
    }

    public void updatePublisher(String[] arr_full_name){
        if (arr_full_name != null){
            if (isID(arr_full_name[2])){
                int id = Integer.parseInt(arr_full_name[2]);
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i <arr_full_name.length; i++){
                    sb.append(arr_full_name[i] + " ");
                }
                String STATEMENT = "update PUBLISHERS set publishername = '" + sb.toString() + "' where publisherid = " + id;
                if (queryDatabase.execute(STATEMENT)){
                    System.out.println("Editting the authors: SUCCESS!");
                } else {
                    System.out.println("FAILURE!");
                }
            }
        } else {
            System.out.println("give good authors name");
        }
    }

    public boolean isID(String prospectiveID){
        try{
            Integer.parseInt(prospectiveID);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean isIDExists(int id){
        String STATEMENT = "select exists(select 1 from publishers where publisherid = " + id + ");";
        ResultSet resultSet = queryDatabase.executeQuery(STATEMENT);
        if (resultSet != null){
            try{
                while (resultSet.next()){
                    return resultSet.getBoolean(1);
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
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

            publisherArrayList.forEach(x ->
            {
                maxPublisherNameLength = Math.max(maxPublisherNameLength, x.getPublisherName().length());
                maxId = Math.max(maxId, x.getPublisherID());
            });

            maxPublisherNameLength = Math.max(maxPublisherNameLength, publisherNameString.length());

            String header = String.format("||%" + OutputService.SL(maxId, idString) + "s" + idString + "%" + OutputService.SR(maxId, idString) + "s" +
                    "||%" + OutputService.SL(maxPublisherNameLength, publisherNameString) + "s" + publisherNameString + "%" + OutputService.SR(maxPublisherNameLength, publisherNameString) + "s" +
                    "||", "", "", "", "");
            System.out.println(header);
            for (Publisher p : publisherArrayList) {

                String publisherIdString = Integer.toString(p.getPublisherID());

                String publisher = String.format("||%" + OutputService.SL(maxId, publisherIdString) + "s" + publisherIdString + "%" + OutputService.SR(maxId, publisherIdString) + "s" +
                        "||%" + OutputService.SL(maxPublisherNameLength, p.getPublisherName()) + "s" + p.getPublisherName() + "%" + OutputService.SR(maxPublisherNameLength, p.getPublisherName()) + "s" +
                        "||", "", "", "", "");

                System.out.println(publisher);
            }
        } else {System.out.println("Publisher List is Empty!");}
    }
}
