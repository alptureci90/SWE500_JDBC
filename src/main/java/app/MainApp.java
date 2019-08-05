package main.java.app;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args){

        // Registering the Driver...
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println("POSTGRES Driver Not Found!!!");
        }

        //TEST DB Connection
        //ConnectToDB.testDBConnection();

        // Construct the tables
        // TODO: add foreign key parameters
        // ConstructDB constructDB = new ConstructDB();

        // Populate the DB
        // AutoPopulateDB autoPopulateDB = new AutoPopulateDB();


        // Under Development: Retrieve info
        //i.e. TEST i.e. getAllAuthors
        QueryDatabase qdb = new QueryDatabase();
        qdb.getAllAuthors();


        //runService();



    }

    public static void runService(){
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Please input a line");
                long then = System.currentTimeMillis();
                String line = scanner.nextLine();
                long now = System.currentTimeMillis();
                System.out.printf("Waited %.3fs for user input%n", (now - then) / 1000d);
                System.out.printf("User input was: %s%n", line);
            }
        } catch(IllegalStateException | NoSuchElementException e) {
            // System.in has been closed
            System.out.println("System.in was closed; exiting");
        }
    }

}
