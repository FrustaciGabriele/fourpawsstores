package com.example.fourpawsstores.model.bean;

import java.sql.Timestamp;

public class CardBean {
    private String CardNumberB;
    private String CardUserB;
    private int CVVB;
    private String ExpireB;
    public CardBean(String num,String user,int cvv,String exdate){
        CardNumberB=num;
        CardUserB=user;
        CVVB=cvv;
        ExpireB=exdate;
    }
    public String getCardNumberB(){
        return CardNumberB;
    }
    public String getCardUserB(){
        return CardUserB;
    }
    public int getCVVB(){
        return CVVB;
    }
    public String getExpireB(){
        return ExpireB;
    }


}
