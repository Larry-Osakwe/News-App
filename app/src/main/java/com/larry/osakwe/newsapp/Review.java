package com.larry.osakwe.newsapp;

/**
 * Created by Larry Osakwe on 6/30/2017.
 */

public class Review {

    private String gameTitle;
    private int gameID;
    private long date;
    private String author;
    private int rating;
    private String URL;
    private String image;
    private String platform;

    public Review(String gameTitle, int gameID, long date, String author, int rating, String URL, String image, String platform) {
        this.gameTitle = gameTitle;
        this.gameID = gameID;
        this.date = date;
        this.author = author;
        this.rating = rating;
        this.URL = URL;
        this.image = image;
        this.platform = platform;
    }


    public String getGameTitle() {
        return gameTitle;
    }

    public int getGameID() {
        return gameID;
    }

    public long getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public int getRating() {
        return rating;
    }

    public String getURL() {
        return URL;
    }

    public String getImage() {
        return image;
    }

    public String getPlatform() {
        return platform;
    }
}
