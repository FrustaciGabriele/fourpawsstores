package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.CatalogueController;
import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CartBean;
import com.example.fourpawsstores.model.bean.CatalogueBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Credentials;
import com.example.fourpawsstores.model.domain.Role;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;


public class CatalogueControllerGrafico {
    private static final String SETTING1 = "-fx-alignment: center;";
    private static final String SETTING2 = "-fx-alignment: center-right;";
    private static final String SETTING3 = "TimesNewRoman";
    private static final String SETTING4 = "-fx-background-color: white; -fx-padding: 20px; -fx-margin: 20px; ";
    @FXML
    private ImageView Back;
    @FXML
    private ImageView ShoppingCart;
    @FXML
    private Label title;
    @FXML
    private VBox productList;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Icatalogo;
    @FXML
    private ImageView Iordini;
    private CatalogueBean catB;
    private CatalogueController controller;
    private CartBean cart;

    public void inizializza(StoreBeans store) throws DAOException, SQLException {

        Back.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backArrow.png"))));
        ShoppingCart.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));

        title.setText(""+ store.getName()+ " catalogo:");
        controller= new CatalogueController(store);
        catB= controller.getCatalogue(store);
        cart= controller.createCart();
        for(ProductBean p :catB.getListProdB()){
            HBox product =showInfoProduct(p);

            productList.getChildren().add(product);

        }

    }




    public void goBack(MouseEvent mouseEvent) throws IOException {
        NavigationController navController= new NavigationController();
        navController.goBackToMap();
    }

    public void openCart(MouseEvent mouseEvent) {
        if(cart.getLenght()>=1){
        Popup popup= new Popup();
        Stage owner = ApplicazioneStage.getStage();

        Rectangle overlay = new Rectangle(owner.getWidth() - 50, owner.getHeight() - 80, Color.WHITE);

        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> popup.hide());
        closeButton.setStyle(SETTING2);

        Text title = new Text("IL TUO CARRELLO: \t");
        title.setFont(Font.font(SETTING3, FontWeight.BOLD, 18));
        title.setStyle(SETTING1);

        HBox header = new HBox(10, title, closeButton);
        header.setAlignment(Pos.CENTER);

        VBox productCartList= new VBox();

        for(ProductBean p :cart.getList()){
            HBox product =showInfoCartProducts(p);
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
            } catch (DAOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
            popup.hide();
            utils.openAdvisepopup("Ordine inviato.\n" +"Controlla il suo stato nell'apposita sezione");
        });
        Button creditButton= new Button("Paga con carta");
        creditButton.setOnAction(e->{
            try {
                controller.inviaordine(2);
            } catch (DAOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
            popup.hide();
            utils.openAdvisepopup("Ordine inviato.\n" +"Controlla il suo stato nell'apposita sezione");
        });

        Label total=new Label("Totale: "+ String.valueOf(cart.getTot())+"€");
        buttonPay.getChildren().addAll(cashButton,creditButton);
        VBox popUpContent= new VBox(header,scrollProduct,total,buttonPay);
        StackPane popupRoot = new StackPane(overlay, popUpContent);
        popup.getContent().add(popupRoot);
        popup.show(owner);}
        else {
            utils.showErrorPopup("Non hai aggiunto nulla al carrello");
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
             cart.addToCart(p,q);}
         else {utils.showErrorPopup("Errore nell'inserimento");}
     });
     Button removeCartButton= new Button("-");
     removeCartButton.setOnAction(e->{
         int q= Integer.parseInt(numAdd.getText());
         if (controller.removeProduct(p,q)){
             q=q -1;
             numAdd.setText(String.valueOf(q));
             cart.deletefromCart(p,q);
         }
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
private HBox showInfoCartProducts(ProductBean p){
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
}
