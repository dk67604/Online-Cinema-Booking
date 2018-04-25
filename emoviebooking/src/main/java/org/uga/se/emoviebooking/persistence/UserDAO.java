package org.uga.se.emoviebooking.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;
import org.uga.se.emoviebooking.ApplicationConfig;
import org.uga.se.emoviebooking.entity.Administrator;
import org.uga.se.emoviebooking.entity.RegisteredUser;
import org.uga.se.emoviebooking.helper.DBConnection;
import org.uga.se.emoviebooking.helper.HelperMethods;
import org.uga.se.emoviebooking.service.User;

import javax.sql.DataSource;
import java.sql.Connection;

@Repository
@Qualifier("userDao")
public class UserDAO implements User {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int register(RegisteredUser registeredUser) {

        String accountId=HelperMethods.generateRandomAccountId();
        registeredUser.setAccountId(accountId);


        int cardStatus=-1;
        int addressStatus=-1;
        int status=jdbcTemplate.update("INSERT INTO `mydb`.`Users`\n" +
                "(`User ID`,\n" +
                "`First Name`,\n" +
                "`Last Name`,\n" +
                "`Email`,\n" +
                "`Type`,\n" +
                "`Password`) VALUES (?,?,?,?,?,?) ",registeredUser.getAccountId(),registeredUser.getFirstName(),registeredUser.getLastName(),
                registeredUser.getEmail(),registeredUser.getUserType(),registeredUser.getPassword().getPassword());
        System.out.println("User added!"+status);
        if(status>0&&registeredUser.getAddress()!=null){
            cardStatus=jdbcTemplate.update("INSERT INTO `mydb`.`Credit Cards`\n" +
                    "(`User ID`,\n" +
                    "`Card ID`,\n" +
                    "`Expiration Date`,\n" +
                    "`SecurityCode`"+
                    ") VALUES (?,?,?,?) ",registeredUser.getAccountId(),registeredUser.getCreditCard().getCardId(),
                    registeredUser.getCreditCard().getCardExpirationDate(),registeredUser.getCreditCard().getSecurityCode());
        }
        if(status>0&&registeredUser.getCreditCard()!=null){
            addressStatus=jdbcTemplate.update("\n" +
                    "INSERT INTO `mydb`.`Address`\n" +
                    "(`Street Address 1`,\n" +
                    "`Zip`,\n" +
                    "`State`,\n" +
                    "`User ID`\n" +
                     ") VALUES (?,?,?,?)",registeredUser.getAddress().getStreetAddress1(),
                    registeredUser.getAddress().getZipcode(),registeredUser.getAddress().getState(),registeredUser.getAccountId());
        }
        if(status>0){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("maikhar@hotmail.com");
            message.setSubject("ECinemaBooking:Registration Confirmed");
            message.setText("Your registration has been confirmed with email: "+registeredUser.getEmail());
            emailSender.send(message);

        }
        return status;


    }

    @Override
    public int resetPassword(String emailAddress) {
        String password=HelperMethods.generateRandomPassword();
        String hashPassword=HelperMethods.hashPassword(password);
        int status=jdbcTemplate.update("UPDATE `mydb`.`Users`\n" +
                "SET  `Password`=? WHERE `Email` =?",hashPassword,emailAddress);
        if(status>0){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("maikhar@hotmail.com");
            message.setSubject("ECinemaBooking:Password Reset");
            message.setText("Your password has been reset,use this password to login:"+password);
            emailSender.send(message);
        }
        return status;
    }

    @Override
    public void refundTicket() {

    }

    @Override
    public void addBooking() {

    }

    public String checkUserExist(String emailAddress){
        String sql="SELECT Email FROM mydb.Users where Email=?";
        try {
            String name = (String) jdbcTemplate.queryForObject(
                    sql, new Object[]{emailAddress}, String.class);
            return name;
        } catch (final EmptyResultDataAccessException e) {
            return null;
        }
    }


    public  String verifyPassword(Object object){
        RegisteredUser registeredUser=null;
        Administrator administrator=null;
        String sql="SELECT Password FROM mydb.Users where Email=? and Type=?";
        String emailAddress=null;
        String password=null;
        String userType=null;
        if(object instanceof  RegisteredUser) {
            registeredUser = (RegisteredUser) object;
            emailAddress=registeredUser.getEmail();
            userType=registeredUser.getUserType();
            password=registeredUser.getPassword().getPassword();
        }
        else{
            administrator=(Administrator)object;
            emailAddress=administrator.getEmail();
            userType=administrator.getUserType();
            password=registeredUser.getPassword().getPassword();


        }
            try {
                String storedPassword = (String) jdbcTemplate.queryForObject(
                        sql, new Object[]{emailAddress,userType}, String.class);
                boolean status= HelperMethods.checkPassword(password,storedPassword);
                if(status){
                    return "SUCCESS";
                }
                else{
                    return "FAILED";
                }

            } catch (final EmptyResultDataAccessException e) {
                return null;
            }



    }
}
