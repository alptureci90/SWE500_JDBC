package main.java.app;

import main.java.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorService {

    QueryDatabase queryDatabase;
    int maxFirstNameLength = 0;
    int maxLastNameLength = 0;
    int maxId = 0;
    String firstNameString = "First Name";
    String lastNameString = "Last Name";
    String idString = "Author ID";

    public AuthorService() {
        queryDatabase = new QueryDatabase();
    }

    public void getAllAuthors(String orderBy) {
        String STATEMENT = "SELECT * FROM authors order by firstName " + orderBy;
        ResultSet resultSet = queryDatabase.executeQuery(STATEMENT);
        if (resultSet != null) {
            ArrayList<Author> authorArrayList = mapResultSet2AuthorList(resultSet);
            printResults(authorArrayList);
        } else {
            System.out.println("Unsuccessful retrieving ALL AUTHORS");
        }
    }

    public void addAuthor(String firstName, String lastName) {
        String STATEMENT = "INSERT INTO authors (firstname, lastname) values ('" + firstName + "','" + lastName + "');";
        if (queryDatabase.execute(STATEMENT)) {
            System.out.println("Adding to the authors: SUCCESS!");
        } else {
            System.out.println("FAILURE!");
        }
    }

    public void updateAuthor(String[] arr_full_name) {
        if (arr_full_name != null && arr_full_name.length == 5) {
            if (isID(arr_full_name[2])) {
                int i = Integer.parseInt(arr_full_name[2]);
                String firstName = arr_full_name[3];
                String lastName = arr_full_name[4];
                String STATEMENT = "update authors set firstname = '" + firstName + "', lastname = '" + lastName + "' where authorID = " + i;
                if (queryDatabase.execute(STATEMENT)) {
                    System.out.println("Editting the authors: SUCCESS!");
                } else {
                    System.out.println("FAILURE!");
                }
            }
        } else {
            System.out.println("give good authors name");
        }
    }

    public boolean isID(String prospectiveID) {
        try {
            Integer.parseInt(prospectiveID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isIDExists(int id) {
        String STATEMENT = "select exists(select 1 from authors where authorid = " + id + ");";
        ResultSet resultSet = queryDatabase.executeQuery(STATEMENT);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    private ArrayList<Author> mapResultSet2AuthorList(ResultSet resultSet) {
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
        } catch (SQLException e) {
            System.out.println("Error in mapping to ArrayListAuthor");
        }
        return null;
    }

    public void printResults(ArrayList<Author> authorArrayList) {

        authorArrayList.forEach(x ->
        {
            maxFirstNameLength = Math.max(maxFirstNameLength, x.getFirstName().length());
            maxLastNameLength = Math.max(maxLastNameLength, x.getLastName().length());
            maxId = Math.max(maxId, x.getAuthorID());
        });

        maxFirstNameLength = Math.max(maxFirstNameLength, firstNameString.length());
        maxLastNameLength = Math.max(maxLastNameLength, lastNameString.length());

        String header = String.format("||%" + OutputService.SL(maxId, idString) + "s" + idString + "%" + OutputService.SR(maxId, idString) + "s" +
                "||%" + OutputService.SL(maxFirstNameLength, firstNameString) + "s" + firstNameString + "%" + OutputService.SR(maxFirstNameLength, firstNameString) + "s" +
                "||%" + OutputService.SL(maxLastNameLength, lastNameString) + "s" + lastNameString + "%" + OutputService.SR(maxLastNameLength, lastNameString) + "s" + "||", "", "", "", "", "", "");
        System.out.println(header);
        for (Author a : authorArrayList) {

            String authorIdString = Integer.toString(a.getAuthorID());

            String author = String.format("||%" + OutputService.SL(maxId, authorIdString) + "s" + authorIdString + "%" + OutputService.SR(maxId, authorIdString) + "s" +
                    "||%" + OutputService.SL(maxFirstNameLength, a.getFirstName()) + "s" + a.getFirstName() + "%" + OutputService.SR(maxFirstNameLength, a.getFirstName()) + "s" +
                    "||%" + OutputService.SL(maxLastNameLength, a.getLastName()) + "s" + a.getLastName() + "%" + OutputService.SR(maxLastNameLength, a.getLastName()) + "s" + "||", "", "", "", "", "", "");

            System.out.println(author);
        }
    }
}
