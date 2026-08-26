package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.FourPawsApplication;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CredentialsBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.dao.ConnectionFactory;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.example.fourpawsstores.model.dao.LoginProcDAO;
import com.example.fourpawsstores.model.dao.ProfileProcDAO;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Credentials;
import com.example.fourpawsstores.model.domain.Profile;
import com.example.fourpawsstores.model.domain.Store;
import com.example.fourpawsstores.utils.utils;
import com.example.fourpawsstores.view.CatalogueControllerGrafico;
import com.example.fourpawsstores.view.SearchControllerGrafico;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class ControllerLogin {

    public void start(CredentialsBean credB) throws DAOException, IOException, SQLException {
        Credentials.setUsername(credB.getUsername());
        Credentials.setPassword(credB.getPassword());
        Credentials.setRole(null);

        //effettuo il login
        try {
            new LoginProcDAO().login();
        } catch(DAOException | SQLException e) {
            throw new IllegalArgumentException(e);
        }
        FXMLLoader fxmlLoad;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        //se le credenziali sono non valide apro un popup
        if (Credentials.getRole() == null) {
            utils.showErrorPopup("Credenziali non valide");
            fxmlLoad = new FXMLLoader(FourPawsApplication.class.getResource("Login.fxml"));
            scene = new Scene(fxmlLoad.load(), utils.getSceneW(), utils.getSceneH());
        }//altrimenti verifico il ruolo del profilo e apro la rispettiva schermata
        else {
            String FxmlRole;
            try {
                ConnectionFactory.changeRole(Credentials.getRole());
            } catch(SQLException e) {
                throw new IllegalArgumentException(e);
            }

            if (Credentials.getRole().getId() == 1) {

                try {
                    new ProfileProcDAO().getProfile();
                } catch(DAOException | SQLException e) {
                    throw new IllegalArgumentException(e);
                }

                FxmlRole = "/com/example/fourpawsstores/negoziante.fxml";
            } else {
                try {
                    new ProfileProcDAO().getProfile();
                } catch(DAOException | SQLException e) {
                    throw new IllegalArgumentException(e);
                }
                FxmlRole = "/com/example/fourpawsstores/utente.fxml";
            }

            fxmlLoad = new FXMLLoader();
            Parent rootNode = fxmlLoad.load(getClass().getResourceAsStream(FxmlRole));
            if(Credentials.getRole().getId()==2){
            final SearchControllerGrafico controller=fxmlLoad.getController();
            controller.inizializza();}
            else{
                Store store= new FindStoresDAO().findStoreById(Profile.getStoreId());
                StoreBeans storeBeans= new StoreBeans(store.getid(),store.getName(),store.getDescription(),store.getImage(),store.getAddress(),store.getLat(),store.getLon(),store.getIdCatalog(),store.getTel());
                final CatalogueControllerGrafico controller=fxmlLoad.getController();
                controller.inizializza(storeBeans);
            }
            scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());
            scene.getRoot().requestFocus();
        }
        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }
}
