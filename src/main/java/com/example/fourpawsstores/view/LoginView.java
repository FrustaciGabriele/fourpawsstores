package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.ControllerLogin;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CredentialsBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class LoginView {
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    public void login() {
        CredentialsBean credB;

        credB= new CredentialsBean(textFieldUsername.getText(), textFieldPassword.getText());
        try{
            ControllerLogin loginController= new ControllerLogin();
            loginController.start(credB);
        }catch (DAOException | IOException e){
            throw new IllegalArgumentException(e);
        }
    }
    @FXML
    public void handleEnter() {login();
    }

}
