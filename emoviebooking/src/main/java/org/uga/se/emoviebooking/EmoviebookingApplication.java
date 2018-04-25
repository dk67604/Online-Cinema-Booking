package org.uga.se.emoviebooking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.uga.se.emoviebooking.persistence.UserDAO;
import org.uga.se.emoviebooking.service.User;

@SpringBootApplication

public class EmoviebookingApplication {

    public static void main(String[] args) {


        //AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        SpringApplication.run(EmoviebookingApplication.class, args);
    }
}
