package org.uga.se.emoviebooking.entity;

import java.io.Serializable;

public class Review implements Serializable {

    private String reviews;
    private String starts;


    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }
}
