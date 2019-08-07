package main.java.app;

import sun.applet.Main;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainApp {

    static final String DEF_ORDER_INFO =  "\n\t\t\tDefault ordering is ASC." +
            "\n\t\t\tFor DESC please type DESC at the end.";
    static final String HELP =
            "\n --list -a : retrieves all authors." + DEF_ORDER_INFO +
            "\n --list -p : retrieves all publishers. " + DEF_ORDER_INFO +
            "\n --list -t : retrieves all titles." + DEF_ORDER_INFO +
            "\n --get -p <publisher name> : lists all the available inventory for that publisher." + DEF_ORDER_INFO +
            "\n --add -a <First Name> <Last Name>" +
            "\n --update -a <id> <new First Name> <new Last Name>" +
            "\n --add -t <isbn(char(10))> <title(char(500))> <editionNumber(int)> <year(char(4))> <price(float(8,2))> <publisher name or id>" +
            "\n --add -p <publisher name>" +
            "\n --update -p <id> <new name>";


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


        MainApp mainApp = new MainApp();
        mainApp.runService();



    }

    public void runService(){
        System.out.println(HELP);
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Please input a operation number you would like to run");
                long then = System.currentTimeMillis();
                String userInput = scanner.nextLine();
                decision(userInput);
                long now = System.currentTimeMillis();
                System.out.printf("Waited %.3fs for user input%n", (now - then) / 1000d);
                System.out.printf("User input was: %s%n", userInput);
            }
        } catch(IllegalStateException | NoSuchElementException e) {
            // System.in has been closed
            System.out.println("System.in was closed; exiting");
        }
    }

    public void decision(String userInput){
        String[] arr_user_input = userInput.split(" ");
        String initial = arr_user_input[0];
        switch (initial){
            case "--list":
                list(arr_user_input);
                break;
            case "--get":
                get(arr_user_input);
                break;
            case "--add":
                add(arr_user_input);
                break;
            case "--update":
                update(arr_user_input);
                break;

                default:
                    System.out.println(HELP);
        }
    }

    public void list(String[] arr_user_input){
        String select = arr_user_input[1];
        String orderInfo = "ASC";
        if(arr_user_input.length == 3 && arr_user_input[2].toUpperCase().equals("DESC")) {
            orderInfo = "DESC";
        }
        switch (select){
            case "-a":
                AuthorService authorService = new AuthorService();
                authorService.getAllAuthors(orderInfo);
                break;
            case "-p":
                PublisherService publisherService = new PublisherService();
                publisherService.getAllPublishers(orderInfo);
                break;
            case "-t":
                TitleService titleService = new TitleService();
                titleService.getAllTitles(orderInfo);
                break;
            default:
                System.out.println("Give a valid option!");
        }

    }

    public void get(String[] arr_user_input){
        String select = arr_user_input[1];
        String orderInfo = "ASC";
        if(arr_user_input.length == 4 && arr_user_input[3].toUpperCase().equals("DESC")) {
            orderInfo = "DESC";
        }

        boolean stat = true;
        int pubID = 0;
        try{
            pubID = Integer.parseInt(arr_user_input[2]);
        } catch (Exception e){
            stat = false;
            System.out.println("Error: ");
            System.out.println("Provide Info in: --get -p <int.pubID> <orderInfo>");
        }
        if (stat) {
            switch (select) {
                case "-p":
                    PublisherService publisherService = new PublisherService();
                    publisherService.getPublisher(pubID);
                    TitleService titleService = new TitleService();
                    titleService.getTitlesOfPublisher(pubID, orderInfo);
                    break;
                default:
                    System.out.println("Provide Info in: --get -p <int.pubID> <orderInfo>");
            }
        }

    }

    public void add(String[] arr_user_input){
        String select = arr_user_input[1];
        if (isCorrectAddForm(arr_user_input)) {
            switch (select) {
                case "-a":
                    AuthorService authorService = new AuthorService();
                    authorService.addAuthor(arr_user_input[2], arr_user_input[3]);
                    break;
                case "-p":
                    PublisherService publisherService = new PublisherService();
                    publisherService.addPublisher(arr_user_input[2]);
                    break;
                case "-t":
                    TitleService titleService = new TitleService();
                    titleService.getAllTitles(orderInfo);
                    break;
                default:
                    System.out.println("Give a valid option!");
            }
        }
    }

    public void update(String[] arr_user_input){

    }

    public boolean isCorrectAddForm(String[] arr_user_input){
        String option = arr_user_input[1];
        switch (option){
            case "-a":
                if (arr_user_input.length != 4) return false;
                break;
            case "-p":
                if (arr_user_input.length != 3) return false;
                break;
            case "-t":
                if (arr_user_input.length != 8) return false;
                break;
            default:
                System.out.println("Give a valid option!");
        }
    }

    public boolean isValidString()
}
