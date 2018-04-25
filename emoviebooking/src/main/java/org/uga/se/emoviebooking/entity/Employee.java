package org.uga.se.emoviebooking.entity;

import java.io.Serializable;

public class Employee implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String accountId;
    private Password password;
    private boolean isAccessToMovies;
    private boolean isAccessToPrizes;
    private boolean isAccessToFess;
    private boolean isAccessToUsers;
    private boolean isAccessToPromotion;
    private boolean isAccessToShowtimes;
    private Address mailingAddress;
    private CreditCard creditCard;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountId() {
        return accountId;
    }

    public Password getPassword() {
        return password;
    }

    public boolean isAccessToMovies() {
        return isAccessToMovies;
    }

    public boolean isAccessToPrizes() {
        return isAccessToPrizes;
    }

    public boolean isAccessToFess() {
        return isAccessToFess;
    }

    public boolean isAccessToUsers() {
        return isAccessToUsers;
    }

    public boolean isAccessToPromotion() {
        return isAccessToPromotion;
    }

    public boolean isAccessToShowtimes() {
        return isAccessToShowtimes;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setAccessToMovies(boolean accessToMovies) {
        isAccessToMovies = accessToMovies;
    }

    public void setAccessToPrizes(boolean accessToPrizes) {
        isAccessToPrizes = accessToPrizes;
    }

    public void setAccessToFess(boolean accessToFess) {
        isAccessToFess = accessToFess;
    }

    public void setAccessToUsers(boolean accessToUsers) {
        isAccessToUsers = accessToUsers;
    }

    public void setAccessToPromotion(boolean accessToPromotion) {
        isAccessToPromotion = accessToPromotion;
    }

    public void setAccessToShowtimes(boolean accessToShowtimes) {
        isAccessToShowtimes = accessToShowtimes;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
}
