package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.profileController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CardBean;
import com.example.fourpawsstores.model.bean.ProfileBean;
import com.example.fourpawsstores.model.domain.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.Objects;

public class ClientProfileControllerGrafico {
    @FXML
    private Label clientName;
    @FXML
    private Label userName;
    @FXML
    private Label cardUser;
    @FXML
    private Label cardNumber;
    @FXML
    private Label cardExDate;
    @FXML
    private Label cardCVV;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Imappa;
    @FXML
    private ImageView Iordini;
    private profileController controller;
    private  CardBean cardB;

    public void inizializza() throws DAOException, SQLException {
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona2.png"))));
        Imappa.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/map.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/package.png"))));
        controller=new profileController();
        ProfileBean profileB= controller.getProfile();
        cardB= controller.getCard();
        String maskedNum= controller.masKNumber(cardB.getCardNumberB());//"*".repeat(Math.max(0,cardB.getCardNumberB().length()-4))+cardB.getCardNumberB().substring(Math.max(0,))
        cardUser.setText(profileB.getNameB());
        userName.setText(profileB.getUsername());
        cardNumber.setText(maskedNum);
        cardExDate.setText(cardB.getExpireB());
        cardCVV.setText("***");
        clientName.setText(cardB.getCardUserB());
    }
    public void changeCard(MouseEvent mouseEvent) {
    }
}
