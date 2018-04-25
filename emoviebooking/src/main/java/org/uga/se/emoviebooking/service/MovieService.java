package org.uga.se.emoviebooking.service;

import org.uga.se.emoviebooking.entity.Movie;
import org.uga.se.emoviebooking.entity.RegisteredUser;
import org.uga.se.emoviebooking.entity.Showtime;
import org.uga.se.emoviebooking.entity.Ticket;

import java.util.List;
import java.util.Map;

public interface MovieService {

    public List<Movie> getLatestMovies();
    public Movie  getAvailableShowTime(String movieTitle);
    public Ticket bookTicket(List<Ticket> ticket, RegisteredUser registeredUser);


}
