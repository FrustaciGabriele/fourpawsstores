package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.FourPawsApplication;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CredentialsBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.dao.*;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Credentials;
import com.example.fourpawsstores.model.domain.Profile;
import com.example.fourpawsstores.model.domain.Store;
import com.example.fourpawsstores.utils.utils;
import com.example.fourpawsstores.view.*;
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
        } catch (DAOException | SQLException e) {
            throw new IllegalArgumentException(e);
        }

        if (Credentials.getRole() == null) {
            utils.showErrorPopup("Credenziali non valide");
            refreshLogin();
        }
        else {
            try {
                ConnectionFactory.changeRole(Credentials.getRole());
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }

            if (Credentials.getRole().getId() == 1) {
                try {
                    new ProfileProcDAO().getProfile();
                } catch (DAOException | SQLException e) {
                    throw new IllegalArgumentException(e);
                }
                NavigationController navcontroller= new NavigationController();
                navcontroller.goBackSceneCatStore();
            } else {
                try {
                    new ProfileProcDAO().getProfile();
                } catch (DAOException | SQLException e) {
                    throw new IllegalArgumentException(e);
                }
                NavigationController navcontroller= new NavigationController();
                navcontroller.goBackToMap();
            }
    }
    }

    private void refreshLogin() throws IOException {
        FXMLLoader fxmlLoad;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        if(utils.getGUI()==0){
            fxmlLoad = new FXMLLoader(FourPawsApplication.class.getResource("Login.fxml"));}
        else{
            fxmlLoad = new FXMLLoader(FourPawsApplication.class.getResource("Login2.fxml"));
        }
        scene = new Scene(fxmlLoad.load(), utils.getSceneW(), utils.getSceneH());
        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }
}
