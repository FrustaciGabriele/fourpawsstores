package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.AddProductController;
import com.example.fourpawsstores.controller.CatalogueController;
import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CatalogueBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.utils.utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;

public class CatalogueStoreControllerGrafico2 {
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Icatalogo;
    @FXML
    private ImageView Iordini;
    @FXML
    private VBox productList;
    @FXML
    private TextField prodName;
    @FXML
    private TextArea prodDescription;
    @FXML
    private TextField prodPrice;
    private CatalogueController controller;
    private CatalogueBean catalogueBean;
    private Image image;
    private byte[] imageBytes;

    public void inizializza(StoreBeans storeB) throws DAOException, SQLException {
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Icatalogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/package.png"))));
        controller= new CatalogueController(storeB);
        catalogueBean= controller.getCatalogue(storeB);
        if(controller.checkLenght()){
            for(ProductBean p :catalogueBean.getListProdB()){

                HBox productInfo =productInfo(p);

                productList.getChildren().add(productInfo);

            }
        }else{
            Label warning= new Label("Non sono presenti prodotti nel catalogo");
            productList.getChildren().add(warning);
        }
    }
    private HBox productInfo(ProductBean p){
        HBox product =new HBox(10);
        product.getStyleClass().add("product-row");
        product.setPrefHeight(100);
        product.setMinHeight(100);
        product.setMaxHeight(100);
        product.setAlignment(Pos.CENTER_LEFT);
        product.setFillHeight(true);
        HBox.setHgrow(product, Priority.ALWAYS);

        VBox info =new VBox(10);
        product.setAlignment(Pos.CENTER);
        HBox.setHgrow(info, Priority.ALWAYS);
        info.setMaxWidth(Double.MAX_VALUE);

        VBox buttons= new VBox(10);
        buttons.setMinWidth(130);
        buttons.setPrefWidth(130);
        product.setAlignment(Pos.CENTER_RIGHT);
        Label name = new Label("Prodotto: \n" + p.getNameB());
        Label price= new Label("Prezzo: "+ p.getPriceB()+"€");
        HBox img;
        if(p.getImage() != null) {
            try {
                InputStream input = p.getImage().getBinaryStream();
                Image image = new Image(input);

                ImageView imageView = new ImageView(image);

                imageView.setFitHeight(90);
                imageView.setFitWidth(90);
                imageView.setPreserveRatio(true);

                img = new HBox(imageView);
            } catch (SQLException e) {
                img = new HBox(new Text("IMG \nNON PRESENTE"));
            }
        }else{
            img = new HBox(new Text("IMG \nNON PRESENTE"));
        }
        img.setAlignment(Pos.CENTER);
        img.setMinWidth(90);
        img.setPrefWidth(90);
        img.setMaxWidth(90);
        img.setMinHeight(90);
        img.setPrefHeight(90);
        img.setMaxHeight(90);
        img.getStyleClass().add("scroll-image");
        Button removeProd= new Button("Rimuovi il prodotto");
        removeProd.setOnAction(e ->{
            try {
                deleteProd(p);
                utils.openAdvisepopup("Prodotto rimosso correttamente");
                refreshUI();
            } catch (DAOException | SQLException ex) {
                utils.showErrorPopup("Errore");
                throw new RuntimeException(ex);
            }

        });
        String buttonText;
        if(p.getState().equals("disponibile")){
            buttonText="Segna indisponibile";
        }else{buttonText="Segna disponibile  ";}
        Button notAvailableProd= new Button(buttonText);
        notAvailableProd.setOnAction(e->{
            try {
                changeState(p);
                utils.openAdvisepopup("Prodotto modificato correttamente");
                refreshUI();
            } catch (DAOException | SQLException ex) {
                utils.showErrorPopup("Errore");
                throw new RuntimeException(ex);
            }

        });
        Button description= new Button("i");
        description.getStyleClass().add("roundbutton");
        description.setOnAction(e ->{
            utils.showPopUpDes(p.getDescriptionB());
        });


        buttons.getChildren().addAll(removeProd,notAvailableProd);
        info.getChildren().addAll(name,price);
        product.getChildren().addAll(img,info,description,buttons);
        return product;
    }
    private void refreshUI() throws DAOException, SQLException {
        catalogueBean = controller.refreshCatalogue();
        productList.getChildren().clear();
        for(ProductBean p :catalogueBean.getListProdB()){

            HBox productInfo =productInfo(p);

            productList.getChildren().add(productInfo);

        }
    }

    private void changeState(ProductBean p) throws DAOException, SQLException {
        controller.changeProductState(p);
    }

    private  void deleteProd(ProductBean p) throws DAOException, SQLException {
        controller.deleteProduct(p);

    }
    public void SeeOrder(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.goToOrderSceneStore();
    }

    public void gotoProfileStoreScene(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.goToProfileStore();
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

    public void addProduct(ActionEvent actionEvent) {
        AddProductController addcontroller= new AddProductController();
        Blob blob=null;
        if(image!=null){
            try {
                blob = new SerialBlob(imageBytes);
            } catch (SQLException e) {
                throw new IllegalArgumentException(e);
            }

        }
        if(!addcontroller.checkName(prodName.getText())){
            utils.showErrorPopup("il nome deve essere compreso tra 1 e 45 caratteri");
        } else if (!addcontroller.checkDescription(prodDescription.getText())) {
            utils.showErrorPopup("la descrizione deve essere compresa tra 1 e 200 caratteri");
        } else if (!addcontroller.checkPrice(prodPrice.getText())) {
            utils.showErrorPopup("il prezzo deve essere maggiore di 0 e del tipo 123.00");
        }else {
            try {
                ProductBean newProd= new ProductBean(prodName.getText(),prodDescription.getText(),blob,new BigDecimal(prodPrice.getText()).setScale(2, RoundingMode.HALF_UP));
                addcontroller.newProduct(newProd);
                utils.openAdvisepopup("Prodotto inserito con successo");
                refreshAddProd();
                refreshUI();
            }catch (IllegalArgumentException e) {
                utils.showErrorPopup(e.getMessage());
            } catch (Exception e) {
                utils.showErrorPopup("Errore improvviso:"+ e.getMessage());
            }

        }
    }

    private void refreshAddProd() {
        image=null;
        imageBytes=null;
        prodName.clear();
        prodDescription.clear();
        prodPrice.clear();
    }
}
