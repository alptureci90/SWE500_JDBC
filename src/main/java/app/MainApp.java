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
        //ConstructDB constructDB = new ConstructDB();

        // Populate the DB
        //AutoPopulateDB autoPopulateDB = new AutoPopulateDB();


        // Under Development: Retrieve info
        //i.e. TEST i.e. getAllAuthors
        //QueryDatabase qdb = new QueryDatabase();
        //qdb.getAllAuthors();


        runService();



    }

    public static void runService(){
        String defOrderInfo =  "\n\t\t\tDefault ordering is ASC." +
                                "\n\t\t\tFor DESC please type DESC at the end.";
        String help = " --get authors : retrieves all authors." + defOrderInfo +
                    "\n --get publishers : retrieves all publishers. " + defOrderInfo +
                    "\n --get titles : retrieves all titles." + defOrderInfo +
                    "\n --list -p <publisher name> : lists all the available inventory for that publisher." + defOrderInfo +
                    "\n --add -a <First Name> <Last Name>" +
                    "\n --update -a <id> <new First Name> <new Last Name>" +
                    "\n --add -t <isbn(char(10))> <title(char(500))> <editionNumber(int)> <year(char(4))> <price(float(8,2))> <publisher name or id>" +
                    "\n --add -p <publisher name>" +
                    "\n --update -p <id> <new name>";

        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Please input a operation number you would like to run");
                System.out.println(help);
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
