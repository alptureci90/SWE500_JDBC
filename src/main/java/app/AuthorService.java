package main.java.app;

import main.java.models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorService {

    QueryDatabase queryDatabase;

    public AuthorService(){
        queryDatabase = new QueryDatabase();
    }

    public void getAllAuthors(String orderBy){
        String STATEMENT = "SELECT * FROM authors order by firstName " + orderBy;
        ResultSet resultSet = queryDatabase.executeQuery(STATEMENT);
        if (resultSet != null){
            ArrayList<Author> authorArrayList = mapResultSet2AuthorList(resultSet);
            printResults(authorArrayList);
                } else {
                    System.out.println("Unsuccessful retrieving ALL AUTHORS");
        }
    }

    public void addAuthor(String firstName, String lastName){
        String STATEMENT = "INSERT INTO authors (firstname, lastname) values ('"+ firstName +"','"+lastName+"');";
        if (queryDatabase.execute(STATEMENT)){
            System.out.println("Adding to the authors: SUCCESS!");
        } else {
            System.out.println("FAILURE!");
        }
    }

    public void updateAuthor(String[] arr_full_name){
        if (arr_full_name != null && arr_full_name.length == 5){
            if (isID(arr_full_name[2])){
                int i = Integer.parseInt(arr_full_name[2]);
                String firstName = arr_full_name[3];
                String lastName = arr_full_name[4];
                String STATEMENT = "update authors set firstname = '" + firstName + "', lastname = '" + lastName + "' where authorID = " + i;
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
        String STATEMENT = "select exists(select 1 from authors where authorid = " + id + ");";
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

    private ArrayList<Author> mapResultSet2AuthorList(ResultSet resultSet){
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

    public void printResults(ArrayList<Author> authorArrayList){
        String header = "|| authorID || First Name || Last Name ||";
        System.out.println(header);
        for (Author a : authorArrayList){
            System.out.println(a.toString());
        }
    }
}
