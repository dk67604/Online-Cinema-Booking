package org.uga.se.emoviebooking.entity;

import java.io.Serializable;

public class Seat implements Serializable {
    private String seatCode;

    private Hall movieHall;

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public Hall getMovieHall() {
        return movieHall;
    }

    public void setMovieHall(Hall movieHall) {
        this.movieHall = movieHall;
    }
}
