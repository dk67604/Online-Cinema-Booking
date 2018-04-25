package org.uga.se.emoviebooking.entity;

import java.io.Serializable;
import java.util.List;

public class RegisteredUser implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String accountId;
    private List<Movie> orderHistory;
    private List<Promotion> promotionStatus;
    private Password password;
    private CreditCard creditCard;
    private  Address address;

    private  String userType;
    public RegisteredUser(){
        this.creditCard=new CreditCard();
        this.password=new Password();
        this.address=new Address();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<Movie> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Movie> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public List<Promotion> getPromotionStatus() {
        return promotionStatus;
    }

    public void setPromotionStatus(List<Promotion> promotionStatus) {
        this.promotionStatus = promotionStatus;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
