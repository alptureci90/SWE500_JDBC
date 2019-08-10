package main.java.app;

import main.java.models.Publisher;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainApp {

    static final Scanner scan = new Scanner(System.in);

    static final String DEF_ORDER_INFO =  "\n\t\t\tDefault ordering is ASC." +
            "\n\t\t\tFor DESC please type DESC at the end.";
    static final String HELP =
            "\n --list -a : retrieves all authors." + DEF_ORDER_INFO +
            "\n --list -p : retrieves all publishers. " + DEF_ORDER_INFO +
            "\n --list -t : retrieves all titles." + DEF_ORDER_INFO +
            "\n --get -p <int.pubID> <orderInfo> : lists all the available inventory for that publisher." + DEF_ORDER_INFO +
            "\n --add -a <First Name> <Last Name>" +
            "\n --update -a <id> <new First Name> <new Last Name>" +
            "\n --add -t" +
                    "\n\t-a <authorID> " +
                    "\n\t-i <isbn(char(10))> " +
                    "\n\t-tn <title(char(500))> " +
                    "\n\t-e <editionNumber(int)> " +
                    "\n\t-y <year(char(4))> " +
                    "\n\t-d <price(float(8,2))> " +
                    "\n\t-p <publisherId>" +
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
        //Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Please input a operation number you would like to run");
                long then = System.currentTimeMillis();
                String userInput = scan.nextLine();
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
        String option = arr_user_input[1];
        if (isCorrectAddForm(arr_user_input)) {
            switch (option) {
                case "-a":
                    AuthorService authorService = new AuthorService();
                    authorService.addAuthor(arr_user_input[2], arr_user_input[3]);
                    break;
                case "-p":
                    StringBuilder sb = new StringBuilder();
                    for(int i = 2; i < arr_user_input.length; i++){
                        sb.append(arr_user_input[i] + " ");
                    }
                    PublisherService publisherService = new PublisherService();
                    publisherService.addPublisher(sb.toString());
                    break;
                case "-t":
                    boolean author_stat = false;
                    int authorID = 0;
                    while (!author_stat){
                        System.out.println("please provide a valid Author ID");
                        String id = scan.nextLine();
                        if (id.equals("exit")){break;}
                        AuthorService as = new AuthorService();
                        author_stat = as.isIDExists(Integer.parseInt(id));
                        if (author_stat){authorID = Integer.parseInt(id);}
                    }

                    boolean publisher_stat = false;
                    int publisherID = 0;
                    while (!publisher_stat){
                        System.out.println("please provide a valid Publisher ID");
                        String pubID = scan.nextLine();
                        if (pubID.equals("exit")){break;}
                        PublisherService ps = new PublisherService();
                        publisher_stat = ps.isIDExists(Integer.parseInt(pubID));
                        if (publisher_stat){publisherID = Integer.parseInt(pubID);}
                    }

                    boolean isbn_stat = false;
                    String isbn = "";
                    while (!isbn_stat){
                        System.out.println("Please provide isbn\n");
                        String is = scan.nextLine();
                        if (is.equals("exit")){break;}
                        else if (is.trim().length() == 10){isbn_stat = true;}
                        isbn = is;
                    }


                    System.out.println("Please provide title\n");
                    String title = scan.nextLine();

                    System.out.println("Please provide e\n");
                    String editionNumber = scan.nextLine();

                    System.out.println("Please provide year\n");
                    String year = scan.nextLine();

                    System.out.println("Please provide price\n");
                    String price = scan.nextLine();

                    /*
                    String isbn = arr_user_input[2];
                    String title = arr_user_input[3];
                    int editionNumber = Integer.parseInt(arr_user_input[4]);
                    String year = arr_user_input[5];
                    int publisherID = Integer.parseInt(arr_user_input[6]);
                    float price = Float.parseFloat(arr_user_input[7]);
                    */

                    TitleService titleService = new TitleService();
                    titleService.addTitle(isbn, title, Integer.parseInt(editionNumber), year, publisherID, Float.parseFloat(price));

                    AuthorISBNService authorISBNService = new AuthorISBNService();
                    authorISBNService.insertIntoAuthorISBN(authorID, isbn);

                    break;
                default:
                    System.out.println("Give a valid option!");
                    break;
            }
        }
    }

    public void update(String[] arr_user_input){
        String option = arr_user_input[1];
        switch (option){
            case "-a":
                System.out.println("updating author");
                AuthorService authorService = new AuthorService();
                authorService.updateAuthor(arr_user_input);
                break;
            case "-p":
                System.out.println("updating publisher");
                PublisherService ps = new PublisherService();
                ps.updatePublisher(arr_user_input);
                break;
            default:
                System.out.println("give me good input");
                break;

        }

    }

    public boolean isCorrectAddForm(String[] arr_user_input){
        String option = arr_user_input[1];
        switch (option){
            case "-t":
                if (arr_user_input.length < 1) {
                    System.out.println("Please provide correct number of elements");
                    return false;
                }
                break;
            case "-a":
                if (arr_user_input.length != 4) {
                    System.out.println("Please provide correct number of elements");
                    return false;
                }
                break;
            case "-p":
                if (arr_user_input.length < 3) {
                    System.out.println("Please provide correct number of elements");
                    return false;
                }
                break;
            default:
                System.out.println("Give a valid option!");
                return false;
        }
        return true;
    }
}
