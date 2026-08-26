package com.example.fourpawsstores.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class AddProductControllerGrafico {
    @FXML
    ImageView Back;
    @FXML
    Label title;
    @FXML
    TextField prodName;
    @FXML
    TextField prodDescription;

    public void inizializza() {
        Back.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backArrow.png"))));
        title.setText("Aggiungi un prodotto");
    }
    public void goBack(MouseEvent mouseEvent) {
    }

    public void addProduct(ActionEvent actionEvent) {
    }


}
