package org.uga.se.emoviebooking.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection  {


    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn=DriverManager.getConnection("jdbc:mysql://" +
                    "ecinemabooking.cyesjekfmb7m.us-east-1.rds.amazonaws.com/mydb?user=movie_booking&password=mypassword");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }
}
