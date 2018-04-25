package org.uga.se.emoviebooking;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.uga.se.emoviebooking.entity.*;
import org.uga.se.emoviebooking.helper.ResponseTemplate;
import org.uga.se.emoviebooking.persistence.MovieDAO;
import org.uga.se.emoviebooking.service.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class MovieController {
    @Autowired
    MovieDAO movieDAO;

    @RequestMapping(path = "/movies", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseTemplate getLatestMovies() throws IOException {
        ResponseTemplate responseTemplate=new ResponseTemplate();
        List<Movie> movies=movieDAO.getLatestMovies();

        List<Map<String,String>> array=new ArrayList<>();
        if(movies!=null&&movies.size()>0){
            responseTemplate.setSuccessMessage("Movies successfully fetched");
            responseTemplate.setStatus("200");
            Map<String,Object> map=new HashMap<>();
            for(Movie movie:movies){
                Map<String,String> response=new HashMap<>();
                response.put("title",movie.getMovieTitle());
                response.put("cast",movie.getCast());
                response.put("trailer",movie.getTrailer());
                response.put("movieTrailer",movie.getMovieTrailer());
                response.put("director",movie.getDirector());
                response.put("category",movie.getCategory());
                response.put("rating",movie.getRating());
                array.add(response);
            }
            map.put("movies",array);
            responseTemplate.setResponse(map);
        }else{
            responseTemplate.setErrorMessage("Failed to fetch movies,try later!");
            responseTemplate.setMessageCode("5001");
            responseTemplate.setStatus("200");
        }

        return responseTemplate;
    }
    @RequestMapping(path = "/showtimes",params = {"title"},method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseTemplate forgotPassword(@RequestParam("title") String movieTitle) throws IOException {
        ResponseTemplate responseTemplate=new ResponseTemplate();
        Movie movie=movieDAO.getAvailableShowTime(movieTitle);
        if(movie!=null){
            System.out.println(movie.getMovieTitle());
            responseTemplate.setStatus("200");
            responseTemplate.setSuccessMessage("Showtimes fetched");
            List<Map<String,String>> showtimes=new ArrayList<>();
            for(Showtime showtime:movie.getShowtimes()) {
                Map<String, String> showTimeRes = new HashMap<>();
                showTimeRes.put("showtime", showtime.getShowTime());
                showTimeRes.put("availableseat", showtime.getAvailableSeat());
                showTimeRes.put("hallnumber",String.valueOf(showtime.getHall().getHallNumber()));
                showtimes.add(showTimeRes);
            }
           Map<String,Object> finalRes=new HashMap<>();
            finalRes.put("showtimes",showtimes);
            responseTemplate.setResponse(finalRes);



        }else{
                responseTemplate.setErrorMessage("Fail to find movie");
                responseTemplate.setStatus("200");
            }


        return responseTemplate;
    }
    @RequestMapping(path = "/bookticket",method = RequestMethod.POST, consumes ={MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseTemplate bookTicket(@RequestBody  String stringToParse) throws IOException {
        ResponseTemplate responseTemplate=new ResponseTemplate();
        JsonNode jsonNode = new ObjectMapper().readTree(stringToParse);
        RegisteredUser registeredUser=new RegisteredUser();

        if(jsonNode!=null){
            registeredUser.setEmail(jsonNode.get("userEmailID").textValue());
            String selectedSeat=jsonNode.get("selectedSeats").textValue();
            String[] seatArray=selectedSeat.split(",");
            List<Ticket> ticketList=new ArrayList<>();
            for(String seat:seatArray){
                Ticket ticket=new Ticket();
                ticket.setMovieTitle(jsonNode.get("movieTitle").textValue());
                Showtime showtime=new Showtime();
                showtime.setShowTime(jsonNode.get("showTime").textValue());
                ticket.setPrice(Integer.valueOf(jsonNode.get("ticketPrice").textValue()));
                Seat bookedSeat=new Seat();
                Hall hall=new Hall();
                hall.setHallNumber(Integer.valueOf(jsonNode.get("hallnumber").textValue()));
                bookedSeat.setMovieHall(hall);
                bookedSeat.setSeatCode(seat);
                ticket.setSeat(bookedSeat);
                ticket.setShowTime(showtime);
                ticketList.add(ticket);
            }
            movieDAO.bookTicket(ticketList,registeredUser);
        }


        return responseTemplate;
    }

}
