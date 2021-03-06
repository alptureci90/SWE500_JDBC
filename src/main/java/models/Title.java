package main.java.models;

import java.io.Serializable;

public class Title implements Serializable {

    private String isbn;
    private String title;
    private int editionNumber;
    private String year;
    private int publisherID;
    private float price;


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString(){
        String result = "||\t"+isbn+"\t||\t"
                +title.trim()+"\t\t\t||\t\t"
                +editionNumber+"\t||\t"
                +year+"\t||\t"
                +publisherID+"\t||\t"
                +price+"\t||\n"
                + new String(new char[80]).replace("\0","-");
        return result;
    }
}
