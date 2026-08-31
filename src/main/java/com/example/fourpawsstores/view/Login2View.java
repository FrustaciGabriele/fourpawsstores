package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.ControllerLogin;
import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CredentialsBean;
import com.example.fourpawsstores.utils.utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class Login2View {
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField textFieldPassword;
    @FXML
    private ComboBox<String> demo;
    @FXML
    public void login() {
        if (demo.getValue().equals("Mode: Demo")){
            utils.switchMode();}
        CredentialsBean credB;

        credB= new CredentialsBean(textFieldUsername.getText(), textFieldPassword.getText());
        try{
            ControllerLogin loginController= new ControllerLogin();
            loginController.start(credB);
        }catch (DAOException | IOException e){
            throw new IllegalArgumentException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void handleEnter() {login();
    }

    public void changeUI(MouseEvent mouseEvent) throws IOException {
        NavigationController navController= new NavigationController();
        navController.changeUIStyle();
    }
}
