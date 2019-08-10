package main.java.models;

public class Publisher {

    private int publisherID;
    private String publisherName;

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }

    public String getPublisherName() {
        return publisherName.trim();
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public String toString(){
        String result = "||       "+publisherID+"    ||   "+publisherName.trim()+"   ||\n"
                 + new String(new char[50]).replace("\0","-");
        return result;
    }
}
