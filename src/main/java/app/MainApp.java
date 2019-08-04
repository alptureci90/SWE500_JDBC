package main.java.app;

public class MainApp {

    public static void main(String[] args){


        try{
            // Registering the Driver...
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e){
            System.out.println("POSTGRES Driver Not Found!!!");
        }

        ConnectToDB.testDBConnection();
        ConstructDB constructDB = new ConstructDB();

    }

}
