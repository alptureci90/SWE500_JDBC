package main.java.app;

import main.java.models.Author;

import java.sql.*;
import java.util.ArrayList;

public class AutoPopulateDB {

    Connection connection;

    public AutoPopulateDB(){
        connection = ConnectToDB.getConnection();

        // Fill Authors
        fillAuthors();

        // Fill Publishers
        fillPublishers();

        // Fill Titles
        fillTitles();

        // Fill AuthorISBN
        fillAuthorISBN();
    }

    public void fillAuthors(){

        //ArrayList<Author> listOfAuthors = new ArrayList<>();
        String listString = "Dan Brown, JK Rownling, Stephen King, RK Narayan, Agatha Christie, Cheten Bhagat, " +
                "William Shakespeare, Leo Tolstoy, Charles Dickens, Mark Twain, Nola Roberts, Jane Austen, CS Lewis, James Patterson, MK Ghandi";

        String[] arrayAuthors = listString.split(", ");

        for (String author : arrayAuthors){
            String[] fullName = author.split(" ");

            //Redundant work. No need to change to Java Class at this point.
            //Author a = new Author(fullName[0], fullName[1]);
            //listOfAuthors.add(a);

            pushAuthor(fullName[0], fullName[1]);
        }
    }

    public void fillPublishers(){
        String listPublishers = "Pearson PLC, Reed Elsevier PLC & Reed Elsevier NV, The Woodbridge Company Ltd., Bertesmann AG, Wolter Kluwer, " +
                "Scholastic, Apollo Global Management LLC, News Corp, Apax and Omers Capital Partners, Oxford University, Stanford, CBS, " +
                "Cambridge University Press, Bloomsbury Publishing PLC, Wiley";

        String[] arrayPublishers = listPublishers.split(", ");

        for (String publisherName : arrayPublishers){
            pushPublisher(publisherName);
        }
    }

    public void fillTitles(){
        String listTitles = "1000110001, Harry Potter, 1, 2010, 45.54, 14;" +
                            "2000220002, Angels and Demons, 1, 2011, 60.34, 12;" +
                            "3000330003, The Lone Wolf Returns, 1, 2014, 54.54, 5;" +
                            "4000440004, Experiments with Truth, 1, 1940, 20.24, 1;" +
                            "5000550005, Two States, 1, 2005, 10.45, 15;" +
                            "6000660006, War and Peace, 1, 1869, 5.45, 7;" +
                            "7000770007, Murder on the Orient Express, 1, 1934, 7.45, 12;" +
                            "8000880008, Life on the Mississippi, 1, 1883, 12.45, 8;" +
                            "9000990009, Malgudi Days, 1, 1920, 6.45, 3;" +
                            "1010101010, Harry Potter, 2, 2012, 50.54, 14;" +
                            "2020202020, The Davinci Code, 1, 2008, 74.0, 12;" +
                            "3030303030, 5 Point Someone, 1, 2007, 10.55, 15;" +
                            "4040404040, Harry Potter, 3, 2014, 60.54, 14;" +
                            "5050505050, Romeo and Juliet, 1, 1603, 15.25, 6;" +
                            "6060606060, Game of Thrones, 1, 2001, 75.68, 9";

        String[] arrayTitles = listTitles.split("; ");

        for (String t : arrayTitles){
            String[] title = t.split(", ");
            pushTitles(title[0], title[1], Integer.parseInt(title[2]), title[3], Float.parseFloat(title[4]), Integer.parseInt(title[5]));
        }
    }

    public void fillAuthorISBN(){

    }


    public void pushAuthor(String firstName, String lastName){
        String s = "INSERT INTO authors (firstName, lastName) values (?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(s);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            executeStatement(statement);
        } catch (SQLException e){
            System.out.println("Error in prepared statement: pushAuthors");
            System.out.println(e.getMessage());
        }
    }

    public void pushPublisher(String publisherName){
        String s = "insert into publishers(publisherName) values (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(s);
            statement.setString(1, publisherName);
            executeStatement(statement);
        } catch (SQLException e){
            System.out.println("Error in prepared statement: pushPublishers");
            e.getMessage();
        }
    }

    public void pushTitles(String isbn, String title, int editionNumber, String year, float price, int publisherID){
        String STATEMENT = "insert into titles (isbn, title, editionNumber, year, price, publisherID) values (?, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(STATEMENT);
            preparedStatement.setString(1, isbn);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, editionNumber);
            preparedStatement.setString(4, year);
            preparedStatement.setFloat(5, price);
            preparedStatement.setInt(6, publisherID);
            executeStatement(preparedStatement);
        } catch (SQLException e){
            System.out.println("Error in prepared statement: pushTitles");
            e.getMessage();
        }
    }

    public boolean executeStatement(PreparedStatement ps){
        try{
            ps.execute();
            System.out.println("Execution Successfull;");
            ConnectToDB.closeConnection(connection);
            return true;
        } catch (SQLException e){
            System.out.println("Error in execution");
            System.out.println(e.getMessage());
            System.out.println("Closing Connection");
            ConnectToDB.closeConnection(connection);
            return false;
        }
    }
}
