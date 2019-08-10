package main.java.app;

import main.java.models.Publisher;
import main.java.models.Title;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TitleService {

    int maxISBNLength = 0;
    int maxTitleLength = 0;
    int maxEdLength = 0;
    int maxYearLength = 0;
    int maxPubIDLength = 0;
    int maxPriceLength = 0;
    
    String ISBNString = "ISBN";
    String titleString = "Title";
    String edString = "Ed. #";
    String yearString = "Year";
    String pubIdString = "Publisher Id";
    String priceString = "Price";

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

            titleArrayList.forEach(x ->
            {
                maxISBNLength = Math.max(maxISBNLength, x.getIsbn().length());
                maxTitleLength = Math.max(maxTitleLength, x.getTitle().length());
                maxEdLength = Math.max(maxEdLength, Integer.toString(x.getEditionNumber()).length());
                maxYearLength = Math.max(maxYearLength, x.getYear().length());
                maxPubIDLength = Math.max(maxPubIDLength, Integer.toString(x.getPublisherID()).length());
                maxPriceLength = Math.max(maxPriceLength, Float.toString(x.getPrice()).length());
            });

            maxISBNLength = Math.max(maxISBNLength,ISBNString.length());
            maxTitleLength = Math.max(maxTitleLength, titleString.length());
            maxEdLength = Math.max(maxEdLength, edString.length());
            maxYearLength = Math.max(maxYearLength, yearString.length());
            maxPubIDLength = Math.max(maxPubIDLength, pubIdString.length());
            maxPriceLength = Math.max(maxPriceLength, priceString.length());

            String header = String.format("||%" + OutputService.SL(maxISBNLength,ISBNString) + "s" + ISBNString + "%" + OutputService.SR(maxISBNLength,ISBNString) + "s" +
                    "||%" + OutputService.SL(maxTitleLength,titleString) + "s" + titleString + "%" + OutputService.SR(maxTitleLength,titleString) + "s" +
                    "||%" + OutputService.SL(maxEdLength,edString) + "s" + edString + "%" + OutputService.SR(maxEdLength,edString) + "s" +
                    "||%" + OutputService.SL(maxYearLength,yearString) + "s" + yearString + "%" + OutputService.SR(maxYearLength,yearString) + "s" +
                    "||%" + OutputService.SL(maxPubIDLength,pubIdString) + "s" + pubIdString + "%" + OutputService.SR(maxPubIDLength,pubIdString) + "s" +
                    "||%" + OutputService.SL(maxPriceLength,priceString) + "s" + priceString + "%" + OutputService.SR(maxPriceLength,priceString) + "s" +
                    "||", "", "", "", "", "", "","","","","","","");
            System.out.println(header);

            for (Title t : titleArrayList) {

                String title = String.format("||%" + OutputService.SL(maxISBNLength,t.getIsbn()) + "s" + t.getIsbn() + "%" + OutputService.SR(maxISBNLength,t.getIsbn()) + "s" +
                        "||%" + OutputService.SL(maxTitleLength,t.getTitle()) + "s" + t.getTitle() + "%" + OutputService.SR(maxTitleLength,t.getTitle()) + "s" +
                        "||%" + OutputService.SL(maxEdLength,Integer.toString(t.getEditionNumber())) + "s" + t.getEditionNumber() + "%" + OutputService.SR(maxEdLength,Integer.toString(t.getEditionNumber())) + "s" +
                        "||%" + OutputService.SL(maxYearLength,t.getYear()) + "s" + t.getYear() + "%" + OutputService.SR(maxYearLength,t.getYear()) + "s" +
                        "||%" + OutputService.SL(maxPubIDLength,Integer.toString(t.getPublisherID())) + "s" + t.getPublisherID() + "%" + OutputService.SR(maxPubIDLength,Integer.toString(t.getPublisherID())) + "s" +
                        "||%" + OutputService.SL(maxPriceLength,Float.toString(t.getPrice())) + "s" + t.getPrice() + "%" + OutputService.SR(maxPriceLength,Float.toString(t.getPrice())) + "s" +
                        "||", "", "", "", "", "", "","","","","","","");
                System.out.println(title);
            }
        } else {System.out.println("Title List is Empty!");}
    }
}
