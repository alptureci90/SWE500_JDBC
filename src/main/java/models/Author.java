package main.java.models;

import java.io.Serializable;

public class Author implements Serializable {

    private int authorID;
    private String firstName;
    private String lastName;

    public Author(){

    }

    public Author(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
