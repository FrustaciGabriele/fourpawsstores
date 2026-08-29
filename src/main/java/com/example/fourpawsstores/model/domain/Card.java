package com.example.fourpawsstores.model.domain;

import java.sql.Timestamp;

public class Card {
    private String CardNumber;
    private String CardUser;
    private int CVV;
    private String Expire;
    public Card(){}
    public Card(String num,String user,int cvv,String exdate){
        CardNumber=num;
        CardUser=user;
        CVV=cvv;
        Expire=exdate;
    }
    public String getCardNumber(){
        return CardNumber;
    }
    public String getCardUser(){
        return CardUser;
    }
    public int getCVV(){
        return CVV;
    }
    public String getExpire(){
        return Expire;
    }
}
