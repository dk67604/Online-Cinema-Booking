package org.uga.se.emoviebooking.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.uga.se.emoviebooking.entity.*;
import org.uga.se.emoviebooking.helper.HelperMethods;
import org.uga.se.emoviebooking.service.MovieService;
import org.uga.se.emoviebooking.service.User;

import javax.sql.DataSource;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@Qualifier("movieDao")
public class MovieDAO implements MovieService {
    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                dataSource);
    }

    @Override
    public List<Movie> getLatestMovies() {

        String sql="SELECT Title,Category,Cast,Trailer,Producer,`Trailer Video`,Rating FROM mydb.Movie";
        List<Movie> movies=new ArrayList<>();
        try {

            List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
            for(Map<String,Object> row:rows){
                Movie movie=new Movie();
                movie.setMovieTitle((String)row.get("Title"));
                movie.setCategory((String)row.get("Category"));
                movie.setCast((String)row.get("Cast"));
                movie.setTrailer((String)row.get("Trailer"));
                movie.setDirector((String)row.get("Producer"));
                movie.setMovieTrailer((String)row.get("Trailer Video"));
                movie.setRating((String)row.get("Rating"));
                movies.add(movie);
            }
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
        return movies;
    }

    @Override
    public Movie getAvailableShowTime(String movieTitle) {
        String sql="select a.Time,a.`Available Seats`,a.`Hall ID` from mydb.ShowTime a INNER JOIN mydb.Movie b on a.`Movie ID`= b.`Movie ID` where b.Title =?";
        Movie movie=new Movie();
        try {
            List<Showtime> showtimes=new ArrayList<>();
            List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql,movieTitle);
            for(Map<String,Object> row:rows){
                Showtime showtime=new Showtime();
                Hall hall=new Hall();
                hall.setHallNumber(Integer.valueOf((String) row.get("Hall ID")));
                Timestamp timestamp=(Timestamp)row.get("Time");
                showtime.setHall(hall);
                showtime.setShowTime(timestamp.toString());
                showtime.setAvailableSeat(String.valueOf((Integer)row.get("Available Seats")));
                showtimes.add(showtime);
            }
            movie.setShowtimes(showtimes);
            movie.setMovieTitle(movieTitle);
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
        return movie;

    }

    @Override
    public Ticket bookTicket(List<Ticket> ticketList, RegisteredUser registeredUser) {
        String showId=getShowId(ticketList.get(0).getShowTime().getShowTime(),ticketList.get(0).getSeat().getMovieHall().getHallNumber());
        System.out.println("showId:"+showId);
        if(showId!=null){

            String sql="select SeatNo,`Show ID`,`Hall ID` from mydb.Seat where `Show ID`=:showId and code IN (:codeIds)";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("codeIds", seatcCodes(ticketList));
            params.put("showId",showId);
            List<Map<String, Object>> result=namedParameterJdbcTemplate.queryForList(sql,params);
            String movieId=getMovieId(ticketList.get(0).getMovieTitle());
            String userId=getUserId(registeredUser.getEmail());
            List<Integer> insertStatus=new ArrayList<>();
            String showTime="";
            if(result!=null&&result.size()>0){
                String bookingId=HelperMethods.generateRandomID();
                for(int i=0;i<result.size();i++){
                    String tiketSql="INSERT INTO `mydb`.`Ticket`\n" +
                            "(`Booking ID`,\n" +
                            "TicketID,\n"+
                            "`MovieIDMovie`,\n" +
                            "`ShowTime`,\n" +
                            "`Seat`,\n" +
                            "`Purchase Price`,\n" +
                            "`Users_User ID`,\n" +
                            "`Seat_Hall ID`,\n" +
                            "`Seat_SeatNo`,\n" +
                            "`Seat_Show ID`)\n" +
                            "VALUES\n" +
                            "(?,?,?,?,?,?,?,?,?,?) ";
                     showTime=ticketList.get(i).getShowTime().getShowTime();
                    int status=jdbcTemplate.update(tiketSql,bookingId,HelperMethods.generateRandomID(),movieId,
                            showTime.substring(0,showTime.length()-2),ticketList.get(i).getSeat().getSeatCode(),ticketList.get(i).getPrice(),
                            userId,result.get(i).get("Hall ID"),result.get(i).get("SeatNo"),result.get(i).get("Show ID"));

                    insertStatus.add(status);

                }

            }
            boolean sendEmailFlag=false;
            for (Integer i:insertStatus){
                if(i==0) {
                    sendEmailFlag=false;
                    break;
                }
                else {
                    sendEmailFlag=true;
                }
            }
            if(sendEmailFlag){
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo("maikhar@hotmail.com");
                message.setSubject("ECinemaBooking:Booking Confirmation");
                message.setText("Your movie ticket has been confirmed.\n\n Ticket Summery\n\n" +
                        "Movie Title:" +ticketList.get(0).getMovieTitle()+"\n"+
                        "Tickets" +getSeats(seatcCodes(ticketList))+"\n"+
                        "Showtime:"+showTime.substring(0,showTime.length()-2)+"\n"+
                "Total Price:"+ticketList.get(0).getPrice()+"\n");
                emailSender.send(message);
            }


        }

        return null;
    }


    public String getSeats(List<String> seats){
        StringBuilder sb=new StringBuilder();
        for(String st:seats){
            sb.append(st+",");
        }
        return sb.toString().substring(0,sb.length()-1);
    }

    public List<String> seatcCodes(List<Ticket> ticketList){
        List<String> seatList=new ArrayList<>();
        for(Ticket ticket:ticketList){
            seatList.add(ticket.getSeat().getSeatCode());
        }
        return  seatList;
    }



    public String getShowId(String showtime,Integer hallumber){
        String sql="SELECT `Show ID` FROM mydb.ShowTime where `Time`=? and `Hall ID`=?";
        try {
            String showID = (String) jdbcTemplate.queryForObject(
                    sql, new Object[]{showtime.substring(0,showtime.length()-2),hallumber}, String.class);
            return showID;
        } catch (final EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getMovieId(String movieTitle){

        String sql="SELECT `Movie ID` from `mydb`.`Movie` where `Title`=?";
        String movieId=getResult(movieTitle,sql);
        return movieId;

    }


    public String getUserId(String emailId) {
        String sql="SELECT `User ID` FROM mydb.Users where `Email`=?";
        String userId=getResult(emailId,sql);
        return  userId;
    }

    public String getResult(String queryParam,String sql){

        try {
            String result = (String) jdbcTemplate.queryForObject(sql, new Object[]{queryParam}, String.class);
            return result;
        } catch (final EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    }
