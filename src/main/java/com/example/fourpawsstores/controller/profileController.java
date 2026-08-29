package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CardBean;
import com.example.fourpawsstores.model.bean.ProfileBean;
import com.example.fourpawsstores.model.dao.ProfileProcDAO;
import com.example.fourpawsstores.model.dao.cardProcedureDAO;
import com.example.fourpawsstores.model.domain.Card;
import com.example.fourpawsstores.model.domain.Profile;

import java.sql.SQLException;

public class profileController {
    private Card card;
    public ProfileBean getProfile() {
        ProfileBean p =new ProfileBean(Profile.getUsername(),Profile.getName(),Profile.getStoreId());
        return p;
    }

    public CardBean getCard() throws DAOException, SQLException {
         card= new cardProcedureDAO().getCardUser(Profile.getCardNum());
         CardBean cardBean=  new CardBean(card.getCardNumber(), card.getCardUser(),card.getCVV(),card.getExpire() );
         return cardBean;
    }

    public String masKNumber(String cardNumberB) {
        String s="*".repeat(Math.max(0,cardNumberB.length()-4))+cardNumberB.substring(Math.max(0,cardNumberB.length()-4));
        return s;
    }
}
