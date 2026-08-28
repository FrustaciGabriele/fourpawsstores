package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.AddProductController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Product;
import com.example.fourpawsstores.utils.utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
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
     private Image image;
     private byte[] imageBytes;

    public void inizializza() {
        Back.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backArrow.png"))));
        title.setText("Aggiungi un prodotto");
        controller= new AddProductController();
    }
    public void goBack() throws IOException, DAOException, SQLException {
      controller.goBackScene();
    }


    public void addProduct(ActionEvent actionEvent) {
        Blob blob=null;
        if(image!=null){
            try {
                blob = new SerialBlob(imageBytes);
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }

        }
        if(!controller.checkName(prodName.getText())){
            utils.showErrorPopup("il nome deve essere compreso tra 1 e 45 caratteri");
        } else if (!controller.checkDescription(prodDescription.getText())) {
            utils.showErrorPopup("la descrizione deve essere compresa tra 1 e 200 caratteri");
        } else if (!controller.checkPrice(prodPrice.getText())) {
            utils.showErrorPopup("il prezzo deve essere maggiore di 0 e del tipo 123.00");
        }else {
            try {
                ProductBean newProd= new ProductBean(prodName.getText(),prodDescription.getText(),blob,new BigDecimal(prodPrice.getText()).setScale(2, RoundingMode.HALF_UP));
                controller.newProduct(newProd);
                utils.openAdvisepopup("Prodotto inserito con successo");
                goBack();
            }catch (IllegalArgumentException e) {
                utils.showErrorPopup(e.getMessage());
            } catch (Exception e) {
                utils.showErrorPopup("Errore improvviso:"+ e.getMessage());
            }

        }
    }


    public void addImg(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un'immagine");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                 image = new Image(selectedFile.toURI().toString());
                 imageBytes = Files.readAllBytes(selectedFile.toPath());


            } catch (Exception e) {

                utils.showErrorPopup("Errore durante il caricamento dell'immagine.");
            }
        }
    }

}
