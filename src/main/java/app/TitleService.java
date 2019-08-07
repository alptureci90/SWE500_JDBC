package main.java.app;

import main.java.models.Publisher;
import main.java.models.Title;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TitleService {
    QueryDatabase queryDatabase;
    public TitleService(){
        queryDatabase = new QueryDatabase();
    }

    public void getAllTitles(String orderInfo){
        String STATEMENT = "SELECT * FROM titles order by title " + orderInfo;
        ResultSet resultSet = queryDatabase.executeQuery(STATEMENT);
        if (resultSet != null) {
            ArrayList<Title> titleArrayList = mapResultSet2TitleList(resultSet);
            printResults(titleArrayList);
        } else {
            System.out.println("Unsuccessful retrieving ALL TITLES");
        }
    }

    public void getTitlesOfPublisher(int publisherID, String orderInfo){
        String STATEMENT = "select * from titles " +
                " where publisherid = " + publisherID +
                " order by title " + orderInfo + ";";
        ResultSet resultSet = queryDatabase.executeQuery(STATEMENT);
        if (resultSet != null){
            ArrayList<Title> titleArrayList = mapResultSet2TitleList(resultSet);
            printResults(titleArrayList);
        } else {
            System.out.println("Unsuccessful retrieving publisher specific TITLES");
        }
    }

    public void addTitle(String isbn, String title, int editionNumber, String year, int publisherID, float price){
        String STATEMENT = "INSERT INTO titles (isbn, title, editionnumber, year, publisherid, price) VALUES (?,?,?,?,?,?);";
        // Check if authorID exists
        // Check if publisherID exists

        // after add is done, insert record to mapping table
        boolean result = true;
        try {
            PreparedStatement preparedStatement = queryDatabase.connection.prepareStatement(STATEMENT);
            preparedStatement.setString(1,isbn);
            preparedStatement.setString(2,title);
            preparedStatement.setInt(3,editionNumber);
            preparedStatement.setString(4, year);
            preparedStatement.setInt(5, publisherID);
            preparedStatement.setFloat(6,price);
            preparedStatement.execute();
            queryDatabase.connection.close();
            System.out.println("Adding to the publishers: SUCCESS!");
        } catch (SQLException e){
            System.out.println("FAILURE!");
            ConnectToDB.closeConnection(queryDatabase.connection);
        }
    }

    private ArrayList<Title> mapResultSet2TitleList(ResultSet resultSet){
        ArrayList<Title> titleArrayList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Title title = new Title();
                title.setIsbn(resultSet.getString("isbn"));
                title.setTitle(resultSet.getString(2));
                title.setEditionNumber(resultSet.getInt(3));
                title.setYear(resultSet.getString(4));
                title.setPublisherID(resultSet.getInt("publisherID"));
                title.setPrice(resultSet.getFloat(6));
                titleArrayList.add(title);
            }
            return titleArrayList;
        } catch (SQLException e){
            System.out.println("Error in mapping to ArrayListAuthor");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void printResults(ArrayList<Title> titleArrayList){
        if (titleArrayList != null) {
            String header =
                    "||\tisbn\t\t" +
                    "||\tTitle\t\t\t\t\t" +
                    "||\tEd. #\t" +
                    "||\tyear\t" +
                    "||\tpubID\t" +
                    "||\tprice\t" +
                    "||";
            System.out.println(header);
            for (Title p : titleArrayList) {
                System.out.println(p.toString());
            }
        } else {System.out.println("Title List is Empty!");}
    }
}
