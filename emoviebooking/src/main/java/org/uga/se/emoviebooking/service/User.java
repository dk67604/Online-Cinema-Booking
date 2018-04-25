package org.uga.se.emoviebooking.service;

import org.uga.se.emoviebooking.entity.RegisteredUser;

public interface User {
    public int register(RegisteredUser registeredUser);
    public int resetPassword(String email);
    public void refundTicket();
    public void addBooking();


}
