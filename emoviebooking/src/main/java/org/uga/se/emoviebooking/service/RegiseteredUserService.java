package org.uga.se.emoviebooking.service;

import org.uga.se.emoviebooking.entity.Movie;
import org.uga.se.emoviebooking.entity.Promotion;
import org.uga.se.emoviebooking.entity.RegisteredUser;

import java.util.List;

public interface RegiseteredUserService {

    public void  setPromotionStatus(Promotion promotion);
    public void  emailReceipt(RegisteredUser registeredUser);
    public List<Movie> viewOrderHistory(RegisteredUser registeredUser);
    public List<Promotion> promotionCodeHistory(RegisteredUser registeredUser);
}
