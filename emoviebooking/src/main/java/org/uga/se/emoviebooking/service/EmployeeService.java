package org.uga.se.emoviebooking.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.uga.se.emoviebooking.entity.Movie;
import org.uga.se.emoviebooking.entity.Promotion;
import org.uga.se.emoviebooking.entity.Ticket;

public interface EmployeeService {
    public void addMovie(Movie movie);
    public void deleteMovie(Movie movie);
    public void createPromotion(Promotion promotion);
    public void changeFee(Movie movie);
    public void changePrice(Ticket ticket);
    public void updateMovie(Movie movie);
    public void addShowtime(Movie movie);
    public JSONPObject getShowtimes(Movie movie);
    public void deleteShowtime(Movie movie);

}
