package org.uga.se.emoviebooking.entity;

import java.io.Serializable;

public class Promotion implements Serializable {
    private String promoCode;
    private String percentageOff;
    private String expirationDate;


    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPercentageOff() {
        return percentageOff;
    }

    public void setPercentageOff(String percentageOff) {
        this.percentageOff = percentageOff;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }


}
