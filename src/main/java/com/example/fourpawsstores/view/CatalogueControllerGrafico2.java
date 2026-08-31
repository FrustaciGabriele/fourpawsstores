package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.CatalogueController;
import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.*;
import com.example.fourpawsstores.utils.utils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;

public class CatalogueControllerGrafico2{
    @FXML
    private ImageView Back;
    @FXML
    private ImageView ShoppingCart;
    @FXML
    private Label title;
    @FXML
    private VBox productList;
    @FXML
    private VBox cartBox;
    private CatalogueBean catB;
    private CatalogueController controller;
    private CartBean cart;
    private StoreBeans storeB;

    public void inizializza(StoreBeans store)  throws DAOException, SQLException {
        Back.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backArrow.png"))));
        title.setText(""+ store.getName()+ " catalogo:");
        storeB=store;
        controller= new CatalogueController(store);
        catB= controller.getCatalogue(store);
        cart= controller.createCart();
        for(ProductBean p :catB.getListProdB()){
            HBox product = showInfoProduct(p);

            productList.getChildren().add(product);

        }
        if(cart.getLenght()==0){
            cartBox.getChildren().add(new Label("Carrello vuoto"));
        }else{
            cartBox.getChildren().add(showCart());
        }
    }

    private VBox showCart() {
        VBox productCartList= new VBox();

        for(ProductBean p :cart.getList()){
            HBox product =showInfoProductCart(p);
            productCartList.getChildren().add(product);
        }
        ScrollPane scrollProduct = new ScrollPane(productCartList);
        scrollProduct.setFitToWidth(true);
        scrollProduct.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        HBox buttonPay=new HBox(10);
        Button cashButton= new Button("Paga al ritiro");
        cashButton.setOnAction(e->{
            try {
                controller.inviaordine(1);
                utils.openAdvisepopup("Ordine inviato.\n" +"Controlla il suo stato nell'apposita sezione");
                refreshUI2();
            } catch (DAOException|RuntimeException | SQLException ex) {
                utils.showErrorPopup(ex.getMessage());
                try {
                    refreshUI2();
                } catch (DAOException | SQLException exc) {
                    throw new RuntimeException(exc);
                }
            }

        });
        Button creditButton= new Button("Paga con carta");
        creditButton.setOnAction(e->{
            try {
                controller.inviaordine(2);
                utils.openAdvisepopup("Ordine inviato.\n" +"Controlla il suo stato nell'apposita sezione");
                refreshUI2();
            } catch (DAOException|RuntimeException | SQLException ex) {
                utils.showErrorPopup(ex.getMessage());
                try {
                    refreshUI2();
                } catch (DAOException | SQLException exc) {
                    throw new RuntimeException(exc);
                }
            }

        });

        Label total=new Label("Totale: "+ String.valueOf(cart.getTot())+"€");
        buttonPay.getChildren().addAll(cashButton,creditButton);
        VBox Content= new VBox(scrollProduct,total,buttonPay);
        return Content;
    }
    private void refreshUI2() throws DAOException, SQLException {
        productList.getChildren().clear();
        cart= controller.createCart();
        cartBox.getChildren().clear();
        catB= controller.getCatalogue(storeB);
        for(ProductBean p :catB.getListProdB()){
            HBox product =showInfoProduct(p);

            productList.getChildren().add(product);

        }
    }

    private HBox showInfoProduct(ProductBean p){
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

        HBox buttons= new HBox(10);
        buttons.setMinWidth(130);
        buttons.setPrefWidth(130);
        product.setAlignment(Pos.CENTER_RIGHT);
        Label name = new Label("Prodotto: \n" + p.getNameB());
        Label price= new Label("Prezzo: "+ p.getPriceB()+"€");
        Label numAdd= new Label("0");
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

        if(p.getState().equals("disponibile")){
            Button addCartButton= new Button("+");
            addCartButton.setOnAction(e ->{
                int q = Integer.parseInt(numAdd.getText()) + 1;
                if(controller.addProduct(p,q)){
                    numAdd.setText(String.valueOf(q));
                    cart.addToCart(p,q);
                    refreshCart();}
                else {
                    utils.showErrorPopup("Errore nell'inserimento");}
            });
            Button removeCartButton= new Button("-");
            removeCartButton.setOnAction(e->{
                int q= Integer.parseInt(numAdd.getText());
                if (controller.removeProduct(p,q)){
                    q=q -1;
                    numAdd.setText(String.valueOf(q));
                    cart.deletefromCart(p,q);
                    refreshCart();
                }
                else {
                    utils.showErrorPopup("Errore nella rimozione");}
            });

            buttons.getChildren().addAll(addCartButton,numAdd,removeCartButton);}
        else{
            buttons.getChildren().add(new Text("Non disponibile"));
        }
        Button description= new Button("i");
        description.getStyleClass().add("roundbutton");
        description.setOnAction(e ->{
            utils.showPopUpDes(p.getDescriptionB());
        });

        info.getChildren().addAll(name,price);
        product.getChildren().addAll(img,info,description,buttons);
        return product;
    }
    public HBox showInfoProductCart(ProductBean p){
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

        Label name = new Label("Prodotto: " + p.getNameB());
        Label price= new Label("Prezzo: "+ p.getPriceB()+"€");
        Label numProd= new Label("Qt: "+ String.valueOf(cart.getListNumProd().get(cart.getList().indexOf(p))));

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
                img = new HBox(new Text("IMG NON PRESENTE"));
            }
        }else{
            img = new HBox(new Text("IMG NON PRESENTE"));
        }
        img.setAlignment(Pos.CENTER);
        img.setMinWidth(90);
        img.setPrefWidth(90);
        img.setMaxWidth(90);
        img.setMinHeight(90);
        img.setPrefHeight(90);
        img.setMaxHeight(90);
        img.getStyleClass().add("scroll-image");

        info.getChildren().addAll(name,price);
        product.getChildren().addAll(img,info,numProd);
        return product;
    }
    public void refreshCart(){
        cartBox.getChildren().clear();
        if(cart.getLenght()==0){
            cartBox.getChildren().add(new Label("Carrello vuoto"));
        }else{
            cartBox.getChildren().add(showCart());
        }
 }
    public void goBack(MouseEvent mouseEvent) throws IOException {
        NavigationController navController= new NavigationController();
        navController.goBackToMap();
    }


}
