package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CardBean;
import com.example.fourpawsstores.model.bean.ProfileBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.dao.DEMODAO;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.example.fourpawsstores.model.dao.ProfileProcDAO;
import com.example.fourpawsstores.model.dao.cardProcedureDAO;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Card;
import com.example.fourpawsstores.model.domain.Profile;
import com.example.fourpawsstores.model.domain.Store;
import com.example.fourpawsstores.utils.utils;
import com.example.fourpawsstores.view.OrderControllerGrafico;
import com.example.fourpawsstores.view.SearchControllerGrafico;
import com.example.fourpawsstores.view.StoresOrderControllerGrafico;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class profileController {
    private Card card;
    public ProfileBean getProfile() {
        ProfileBean p =new ProfileBean(Profile.getUsername(),Profile.getName(),Profile.getCardNum());
        return p;
    }
    public ProfileBean getProfileStore() {
        ProfileBean p =new ProfileBean(Profile.getUsername(),Profile.getName(),Profile.getStoreId());
        return p;
    }

    public CardBean getCard() throws DAOException, SQLException {
        if (utils.getMode()==0){
         card= new cardProcedureDAO().getCardUser(Profile.getCardNum());}
        else {
            card= new DEMODAO().obtainCard(Profile.getCardNum());
        }
         CardBean cardBean=  new CardBean(card.getCardNumber(), card.getCardUser(),card.getCVV(),card.getExpire() );
         return cardBean;
    }

    public String masKNumber(String cardNumberB) {
        String s="*".repeat(Math.max(0,cardNumberB.length()-4))+cardNumberB.substring(Math.max(0,cardNumberB.length()-4));
        return s;
    }

    public StoreBeans obtainStorebyId(int storeIdB) throws DAOException, SQLException {
        Store store;
        if (utils.getMode()==0){
         store=  new FindStoresDAO().findStoreById(storeIdB);}
        else {
            store=new DEMODAO().getStore(storeIdB);
        }
        StoreBeans storeB= new StoreBeans(store.getid(),store.getName(),store.getDescription(),store.getImage(),store.getAddress(), store.getLat(), store.getLon(), store.getIdCatalog(),store.getTel());
        return storeB;
    }

}
