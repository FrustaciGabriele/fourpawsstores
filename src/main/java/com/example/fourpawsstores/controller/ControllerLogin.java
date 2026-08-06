package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.FourPawsApplication;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CredentialsBean;
import com.example.fourpawsstores.model.dao.ConnectionFactory;
import com.example.fourpawsstores.model.dao.LoginProcDAO;
import com.example.fourpawsstores.model.dao.ProfileProcDAO;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Credentials;
import com.example.fourpawsstores.utils.utils;
import com.example.fourpawsstores.view.SearchControllerGrafico;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerLogin {

    public void start(CredentialsBean credB) throws DAOException,IOException {
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
                FxmlRole = "/com/example/fourpawsstores/utente.fxml";
            }
            fxmlLoad = new FXMLLoader();
            Parent rootNode = fxmlLoad.load(getClass().getResourceAsStream(FxmlRole));
            final SearchControllerGrafico controller=fxmlLoad.getController();
            controller.inizializzamappa();
            scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());
        }
        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }
}
