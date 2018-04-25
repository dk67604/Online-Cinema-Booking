package org.uga.se.emoviebooking.entity;

import java.io.Serializable;

public class Ticket implements Serializable {
    private int price;
    private int age;
    private String movieTitle;
    private Showtime showTime;
    private String purchaseDate;
    private Seat seat;
    private Promotion promotion;


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Showtime getShowTime() {
        return showTime;
    }

    public void setShowTime(Showtime showTime) {
        this.showTime = showTime;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
