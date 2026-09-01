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
    private Image imgNewProd;
    private byte[] imageBytes;

    public void inizializza(StoreBeans storeB) throws DAOException, SQLException {
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Icatalogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/catalogo2.png"))));
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
        HBox prodInfoCat =new HBox(10);
        prodInfoCat.getStyleClass().add("product-row");
        prodInfoCat.setPrefHeight(100);
        prodInfoCat.setMinHeight(100);
        prodInfoCat.setMaxHeight(100);
        prodInfoCat.setAlignment(Pos.CENTER_LEFT);
        prodInfoCat.setFillHeight(true);
        HBox.setHgrow(prodInfoCat, Priority.ALWAYS);

        VBox info =new VBox(10);
        prodInfoCat.setAlignment(Pos.CENTER);
        HBox.setHgrow(info, Priority.ALWAYS);
        info.setMaxWidth(Double.MAX_VALUE);

        VBox prodbuttons= new VBox(10);
        prodbuttons.setMinWidth(130);
        prodbuttons.setPrefWidth(130);
        prodInfoCat.setAlignment(Pos.CENTER_RIGHT);
        Label name = new Label("Prodotto: \n" + p.getNameB());
        Label price= new Label("Prezzo: "+ p.getPriceB()+"€");
        HBox imgProdCatalog;
        if(p.getImage() != null) {
            try {
                InputStream input = p.getImage().getBinaryStream();
                Image image = new Image(input);

                ImageView imageView = new ImageView(image);

                imageView.setFitHeight(90);
                imageView.setFitWidth(90);
                imageView.setPreserveRatio(true);

                imgProdCatalog = new HBox(imageView);
            } catch (SQLException e) {
                imgProdCatalog = new HBox(new Text("IMG \nNON PRESENTE"));
            }
        }else{
            imgProdCatalog = new HBox(new Text("IMG \nNON PRESENTE"));
        }
        imgProdCatalog.setAlignment(Pos.CENTER);
        imgProdCatalog.setMinWidth(90);
        imgProdCatalog.setPrefWidth(90);
        imgProdCatalog.setMaxWidth(90);
        imgProdCatalog.setMinHeight(90);
        imgProdCatalog.setPrefHeight(90);
        imgProdCatalog.setMaxHeight(90);
        imgProdCatalog.getStyleClass().add("scroll-image");
        Button removeProduct= new Button("Rimuovi il prodotto");
        removeProduct.setOnAction(e ->{
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
        Button AvailableProd= new Button(buttonText);
        AvailableProd.setOnAction(e->{
            try {
                changeState(p);
                utils.openAdvisepopup("Prodotto modificato correttamente");
                refreshUI();
            } catch (DAOException | SQLException ex) {
                utils.showErrorPopup("Errore");
                throw new RuntimeException(ex);
            }

        });
        Button descriptionProd= new Button("i");
        descriptionProd.getStyleClass().add("roundbutton");
        descriptionProd.setOnAction(e ->{
            utils.showPopUpDes(p.getDescriptionB());
        });


        prodbuttons.getChildren().addAll(removeProduct,AvailableProd);
        info.getChildren().addAll(name,price);
        prodInfoCat.getChildren().addAll(imgProdCatalog,info,descriptionProd,prodbuttons);
        return prodInfoCat;
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
        FileChooser fileChooserImg = new FileChooser();
        fileChooserImg.setTitle("Seleziona un'immagine");
        File selectedFile = fileChooserImg.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                imgNewProd = new Image(selectedFile.toURI().toString());
                imageBytes = Files.readAllBytes(selectedFile.toPath());


            } catch (Exception e) {

                utils.showErrorPopup("Errore durante il caricamento dell'immagine.");
            }
        }
    }

    public void addProduct(ActionEvent actionEvent) {
        AddProductController addcontroller= new AddProductController();
        Blob blob=null;
        if(imgNewProd !=null){
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
        imgNewProd =null;
        imageBytes=null;
        prodName.clear();
        prodDescription.clear();
        prodPrice.clear();
    }
}
