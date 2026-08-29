package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CardBean;
import com.example.fourpawsstores.model.bean.ProfileBean;
import com.example.fourpawsstores.model.dao.ProfileProcDAO;
import com.example.fourpawsstores.model.dao.cardProcedureDAO;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Card;
import com.example.fourpawsstores.model.domain.Profile;
import com.example.fourpawsstores.utils.utils;
import com.example.fourpawsstores.view.OrderControllerGrafico;
import com.example.fourpawsstores.view.SearchControllerGrafico;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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

    public void goToMap() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        fxmlFile="/com/example/fourpawsstores/utente.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        final SearchControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza();
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());


        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }

    public void goToOrder() throws DAOException, SQLException, IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        fxmlFile="/com/example/fourpawsstores/ordini.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        final OrderControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza();
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());


        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }
}
