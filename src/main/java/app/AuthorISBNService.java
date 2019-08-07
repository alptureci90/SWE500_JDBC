package main.java.app;

public class AuthorISBNService {

    QueryDatabase queryDatabase;

    public AuthorISBNService(){
        queryDatabase = new QueryDatabase();
    }

    public void insertIntoAuthorISBN(int authorID, String isbn){
        String STATEMENT = "insert into authorisbn (authorid, isbn) VALUES (" + authorID + "," +isbn + ");";
        if (queryDatabase.execute(STATEMENT)){
            System.out.println("Adding to the AuthorISBN: SUCCESS!");
        } else {
            System.out.println("FAILURE!");
        }
    }


}
