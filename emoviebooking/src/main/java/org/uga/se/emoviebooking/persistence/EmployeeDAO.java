package org.uga.se.emoviebooking.persistence;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.uga.se.emoviebooking.entity.Employee;
import org.uga.se.emoviebooking.entity.Movie;
import org.uga.se.emoviebooking.entity.Promotion;
import org.uga.se.emoviebooking.entity.Ticket;
import org.uga.se.emoviebooking.service.EmployeeService;

public class EmployeeDAO implements EmployeeService {


    @Override
    public void addMovie(Movie movie) {

    }

    @Override
    public void deleteMovie(Movie movie) {

    }

    @Override
    public void createPromotion(Promotion promotion) {

    }

    @Override
    public void changeFee(Movie movie) {

    }

    @Override
    public void changePrice(Ticket ticket) {

    }

    @Override
    public void updateMovie(Movie movie) {

    }

    @Override
    public void addShowtime(Movie movie) {

    }

    @Override
    public JSONPObject getShowtimes(Movie movie) {
        return null;
    }

    @Override
    public void deleteShowtime(Movie movie) {

    }
}
