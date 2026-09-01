package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.CatalogueController;
import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CartBean;
import com.example.fourpawsstores.model.bean.CatalogueBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
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
    private VBox productListCat;
    private CatalogueBean catalogB;
    private CatalogueController controllerCat;
    private CartBean cart;
    private StoreBeans storebean;

    public void inizializza(StoreBeans store) throws DAOException, SQLException {

        Back.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/backArrow.png"))));
        ShoppingCart.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/cart.png"))));
        storebean=store;
        title.setText(""+ store.getName()+ " catalogo:");
        controllerCat = new CatalogueController(store);
        catalogB = controllerCat.getCatalogue(store);
        cart= controllerCat.createCart();
        if (catalogB.getListProdB().size()==0){
            productListCat.getChildren().add(new Label("Il catalogo è vuoto"));
        }
        for(ProductBean p : catalogB.getListProdB()){
            HBox product = showInfoProductCat(p);

            productListCat.getChildren().add(product);

        }

    }




    public void goBack(MouseEvent mouseEvent) throws IOException {
        NavigationController navController= new NavigationController();
        navController.goBackToMap();
    }

    public void openCart(MouseEvent mouseEvent) {
        if(cart.getLenght()>=1){
            openPopUpCart();
        }
        else {
            utils.showErrorPopup("Non hai aggiunto nulla al carrello");
        }
}
public void openPopUpCart(){
    Popup popup= new Popup();
    Stage owner = ApplicazioneStage.getStage();

    Rectangle overlay = new Rectangle(owner.getWidth() - 50, owner.getHeight() - 80, Color.WHITE);
    overlay.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");

    Button closeButton = new Button("X");
    closeButton.setOnAction(e -> popup.hide());
    closeButton.setStyle(SETTING2);

    Text title = new Text("IL TUO CARRELLO: \t");
    title.setFont(Font.font(SETTING3, FontWeight.BOLD, 18));
    title.setStyle(SETTING1);

    HBox header = new HBox(10, title, closeButton);
    header.setAlignment(Pos.CENTER);

    VBox productCartList= new VBox(8);

    for(ProductBean p :cart.getList()){
        HBox product =showInfoCartProducts(p);
        productCartList.getChildren().add(product);
    }
    ScrollPane scrollProduct = new ScrollPane(productCartList);
    scrollProduct.setPrefHeight(400);
    scrollProduct.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollProduct.setFitToWidth(true);

    productCartList.setFillWidth(true);
    productCartList.maxWidthProperty().bind(scrollProduct.widthProperty());

    HBox buttonPay=new HBox(10);
    Button cashButton= new Button("Paga al ritiro");
    cashButton.setOnAction(e->{
        try {
            controllerCat.inviaordine(1);
            popup.hide();
            utils.openAdvisepopup("Ordine inviato.\n" +"Controlla il suo stato nell'apposita sezione");
            //refreshUI();
        } catch (DAOException|RuntimeException | SQLException ex) {
            popup.hide();
            utils.showErrorPopup(ex.getMessage());
        }
        try {
            refreshUICat();
        } catch (DAOException | SQLException exc) {
            throw new RuntimeException(exc);
        }

    });
    Button creditButton= new Button("Paga con carta");
    creditButton.setOnAction(e->{
        try {
            controllerCat.inviaordine(2);
            popup.hide();
            utils.openAdvisepopup("Ordine inviato.\n" +"Controlla il suo stato nell'apposita sezione");
            //refreshUI();
        } catch (DAOException|RuntimeException | SQLException ex) {
            popup.hide();
            utils.showErrorPopup(ex.getMessage());
        }
        try {
            refreshUICat();
        } catch (DAOException | SQLException exc) {
            throw new RuntimeException(exc);
        }
    });

    Label total=new Label("Totale: "+ String.valueOf(cart.getTot())+"€");
    buttonPay.getChildren().addAll(cashButton,creditButton);
    buttonPay.setStyle(SETTING1);
    VBox popUpContent= new VBox(header,scrollProduct,total,buttonPay);
    popUpContent.maxWidthProperty().bind(overlay.widthProperty());
    popUpContent.maxHeightProperty().bind(overlay.heightProperty());
    popUpContent.prefWidthProperty().bind(overlay.widthProperty());
    popUpContent.prefHeightProperty().bind(overlay.heightProperty());
    StackPane popupRoot = new StackPane(overlay, popUpContent);


    popup.getContent().add(popupRoot);
    popup.show(owner);
}

    private void refreshUICat() throws DAOException, SQLException {
        productListCat.getChildren().clear();
        cart= controllerCat.createCart();
        catalogB = controllerCat.getCatalogue(storebean);
        for(ProductBean p : catalogB.getListProdB()){
            HBox product = showInfoProductCat(p);

            productListCat.getChildren().add(product);
        }
    }

    private HBox showInfoProductCat(ProductBean p){
    HBox productCat =new HBox(10);
     productCat.getStyleClass().add("product-row");
     productCat.setPrefHeight(100);
     productCat.setMinHeight(100);
     productCat.setMaxHeight(100);
     productCat.setAlignment(Pos.CENTER_LEFT);
     productCat.setFillHeight(true);
     HBox.setHgrow(productCat, Priority.ALWAYS);

    VBox info =new VBox(10);
        productCat.setAlignment(Pos.CENTER);
        HBox.setHgrow(info, Priority.ALWAYS);
        info.setMaxWidth(Double.MAX_VALUE);

    HBox button= new HBox(10);
        button.setMinWidth(130);
        button.setPrefWidth(130);
        productCat.setAlignment(Pos.CENTER_RIGHT);
    Label name = new Label("Prodotto: \n" + p.getNameB());
    Label price= new Label("Prezzo: "+ p.getPriceB()+"€");
    Label numAdd= new Label("0");
    HBox imgProd;
        if(p.getImage() != null) {
        try {
            InputStream input = p.getImage().getBinaryStream();
            Image image = new Image(input);

            ImageView imageView = new ImageView(image);

            imageView.setFitHeight(90);
            imageView.setFitWidth(90);
            imageView.setPreserveRatio(true);

            imgProd = new HBox(imageView);
        } catch (SQLException e) {
            imgProd = new HBox(new Text("IMG \nNON PRESENTE"));
        }
    }else{
        imgProd = new HBox(new Text("IMG \nNON PRESENTE"));
    }
     imgProd.setAlignment(Pos.CENTER);
     imgProd.setMinWidth(90);
     imgProd.setPrefWidth(90);
     imgProd.setMaxWidth(90);
     imgProd.setMinHeight(90);
     imgProd.setPrefHeight(90);
     imgProd.setMaxHeight(90);
     imgProd.getStyleClass().add("scroll-image");

     if(p.getState().equals("disponibile")){
     Button addCartButton= new Button("+");
     addCartButton.setOnAction(e ->{
         int q = Integer.parseInt(numAdd.getText()) + 1;
         if(controllerCat.addProduct(p,q)){
             numAdd.setText(String.valueOf(q));
             cart.addToCart(p,q);}
         else {utils.showErrorPopup("Errore nell'inserimento");}
     });
     Button removeCartButton= new Button("-");
     removeCartButton.setOnAction(e->{
         int q= Integer.parseInt(numAdd.getText());
         if (controllerCat.removeProduct(p,q)){
             q=q -1;
             numAdd.setText(String.valueOf(q));
             cart.deletefromCart(p,q);
         }else {utils.showErrorPopup("Errore nella rimozione");}
     });

     button.getChildren().addAll(addCartButton,numAdd,removeCartButton);}
     else{
         button.getChildren().add(new Text("Non disponibile"));
     }
     Button description= new Button("i");
     description.getStyleClass().add("roundbutton");
     description.setOnAction(e ->{
         utils.showPopUpDes(p.getDescriptionB());
     });

     info.getChildren().addAll(name,price);
     productCat.getChildren().addAll(imgProd,info,description,button);
     return productCat;
}
private HBox showInfoCartProducts(ProductBean p){
    HBox product =new HBox(10);
    product.setStyle("-fx-border-color: #cccccc;\n" +
            "    -fx-border-width: 1;\n" +
            "    -fx-border-radius: 5;\n" +
            "    -fx-background-radius: 5;\n" +
            "    -fx-padding: 5;");
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
    name.maxWidthProperty().bind(info.widthProperty());
    price.maxWidthProperty().bind(info.widthProperty());
    numProd.maxWidthProperty().bind(info.widthProperty());

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
            Label notimg = new Label("IMG \n NON PRESENTE");
            notimg.wrapTextProperty();
            img = new HBox(notimg);
        }
    }else{
        Label notimg = new Label("IMG \n NON PRESENTE");
        notimg.wrapTextProperty();
        img = new HBox(notimg);
    }
    img.setAlignment(Pos.CENTER);
    img.setMinWidth(90);
    img.setPrefWidth(90);
    img.setMaxWidth(90);
    img.setMinHeight(90);
    img.setPrefHeight(90);
    img.setMaxHeight(90);
    img.setStyle(" -fx-border-color: #cccccc;\n" +
            "    -fx-border-width: 1;\n" +
            "    -fx-background-color: #ffffff;\n" +
            "    -fx-border-radius: 6;\n" +
            "    -fx-background-radius: 6;");

    info.getChildren().addAll(name,price);
    product.getChildren().addAll(img,info,numProd);
    return product;
}
}
