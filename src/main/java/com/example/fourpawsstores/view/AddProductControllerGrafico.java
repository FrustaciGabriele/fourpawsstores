package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.AddProductController;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.domain.Product;
import com.example.fourpawsstores.utils.utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Objects;

public class AddProductControllerGrafico {
    @FXML
    private ImageView Back;
    @FXML
    private Label title;
    @FXML
    private TextField prodName;
    @FXML
    private TextArea prodDescription;
    @FXML
    private TextField prodPrice;
    private ProductBean newProd;
    private AddProductController controller;

    public void inizializza() {
        Back.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backArrow.png"))));
        title.setText("Aggiungi un prodotto");
        controller= new AddProductController();
        newProd= controller.newProduct();
    }
    public void goBack(MouseEvent mouseEvent) {
    }

    public void addProduct(ActionEvent actionEvent) {
        if(!controller.checkName(prodName.getText())){
            utils.showErrorPopup("il nome deve essere compreso tra 1 e 45 caratteri");
        } else if (!controller.checkDescription(prodDescription.getText())) {
            utils.showErrorPopup("la descrizione deve essere compresa tra 1 e 200 caratteri");
        } else if (!controller.checkPrice(prodPrice.getText())) {
            utils.showErrorPopup("il prezzo deve essere maggiore di 0 e del tipo 123.00");
        }
    }


    public void addImg(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
            } catch (Exception e) {

                utils.showErrorPopup("Errore durante il caricamento dell'immagine.");
            }
        }
    }

}
