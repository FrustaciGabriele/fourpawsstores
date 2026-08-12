package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.CatalogueController;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.SQLException;


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
    private CatalogueBean catB;
    private CatalogueController controller;
    private CartBean cart;
    private int storeid;

    public void inizializza(StoreBeans store) throws DAOException, SQLException {
        storeid=store.getid();
        System.out.println("id= %d"+ store.getid());
        Back.setImage(new Image(getClass().getResourceAsStream("/images/backArrow.png")));
        ShoppingCart.setImage(new Image(getClass().getResourceAsStream("/images/icona.png")));
        title= new Label(store.getName()+ " catalogo:");
        controller= new CatalogueController();
        catB= controller.getCatalogue(store);
        cart= new CartBean();
        for(ProductBean p :catB.getListProdB()){
            System.out.println(p.getId()+""+p.getNameB());
            HBox product =new HBox(10);
            VBox info =new VBox(10);
            HBox buttons= new HBox(10);

            Label name = new Label("Prodotto: " + p.getNameB());
            Label remaining= new Label("Rimanenti: "+p.getStock());
            Label price= new Label("Prezzo: "+ p.getPriceB()+"€");
            Label numAdd= new Label("0");

            HBox img;
            if(p.getImage() != null) {
                try {
                    InputStream input = p.getImage().getBinaryStream();
                    Image image = new Image(input);

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(true);

                    img = new HBox(imageView);
                } catch (SQLException e) {
                    img = new HBox(new Text("IMG NON PRESENTE"));
                }
            }else{
                img = new HBox(new Text("IMG NON PRESENTE"));
            }

            Button addCartButton= new Button("+");
            addCartButton.setOnAction(e ->{
                int q = Integer.parseInt(numAdd.getText()) + 1;
                if (q<=(p.getStock())){
                numAdd.setText(String.valueOf(q));
                cart.addToCart(p,q);
                }else {
                    utils.showErrorPopup("Non in stock");}

            });
            Button removeCartButton= new Button("-");
            removeCartButton.setOnAction(e->{
                int q= Integer.parseInt(numAdd.getText());
                if (q>=1){
                    q=q -1;
                    numAdd.setText(String.valueOf(q));
                    cart.deletefromCart(p,q);
                }
            });
            Button description= new Button("i");
            description.getStyleClass().add("roundbutton");
            description.setOnAction(e ->{
                showPopUpDes(p.getDescriptionB());
            });


            buttons.getChildren().addAll(addCartButton,numAdd,removeCartButton);
            info.getChildren().addAll(name,remaining,price);
            product.getChildren().addAll(img,info,description,buttons);
            productList.getChildren().add(product);

        }

    }

    private void showPopUpDes(String descriptionB) {
        System.out.println("" + descriptionB);
        Popup popup= new Popup();
        Stage owner = ApplicazioneStage.getStage();

        // Crea l'overlay nero
        Rectangle overlay = new Rectangle(owner.getWidth() - 5, owner.getHeight() - 5, Color.BLACK);
        overlay.setOpacity(0.3);

        // Crea il pulsante di chiusura
        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> popup.hide());
        closeButton.setStyle(SETTING2);

        Text title = new Text("Descrizione: \t");
        title.setFont(Font.font(SETTING3, FontWeight.BOLD, 18));
        title.setStyle(SETTING1);

        HBox header = new HBox(10, title, closeButton);
        header.setAlignment(Pos.CENTER);

        Label messageLabel = new Label();
        messageLabel.setText("\n" + descriptionB);
        messageLabel.setWrapText(true);

        VBox vBoxContentBody = new VBox(messageLabel);

        // Crea il contenuto del popup
        VBox popupContent = new VBox(header, vBoxContentBody);
        popupContent.setFillWidth(true);
        popupContent.setMaxWidth(owner.getWidth() - 200);
        popupContent.setMaxHeight(owner.getHeight() - 600);
        popupContent.setStyle(SETTING4);

        // Aggiungi l'overlay e il contenuto al popup
        StackPane popupRoot = new StackPane(overlay, popupContent);
        popupRoot.setStyle(SETTING1); // Centra il contenuto del popup
        popup.getContent().add(popupRoot);

        // Mostra il popup
        popup.show(owner);
    }


    public void goBack(MouseEvent mouseEvent) {
    }

    public void openCart(MouseEvent mouseEvent) {
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

        for(ProductBean p :catB.getListProdB()){
            HBox product =new HBox(10);
            VBox info =new VBox(10);

            Label name = new Label("Prodotto: " + p.getNameB());
            Label remaining= new Label("Rimanenti: "+p.getStock());
            Label price= new Label("Prezzo: "+ p.getPriceB()+"€");
            Label numProd= new Label("Qt: "+ String.valueOf(cart.getListNumProd().get(cart.getList().indexOf(p))));

            HBox img;
            if(p.getImage() != null) {
                try {
                    InputStream input = p.getImage().getBinaryStream();
                    Image image = new Image(input);

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(200);
                    imageView.setPreserveRatio(true);

                    img = new HBox(imageView);
                } catch (SQLException e) {
                    img = new HBox(new Text("IMG NON PRESENTE"));
                }
            }else{
                img = new HBox(new Text("IMG NON PRESENTE"));
            }

            info.getChildren().addAll(name,remaining,price);
            product.getChildren().addAll(img,info,numProd);
            productCartList.getChildren().add(product);
        }
        ScrollPane scrollProduct = new ScrollPane(productCartList);

        HBox buttonPay=new HBox(10);
        Button cashButton= new Button("Paga al ritiro");
        cashButton.setOnAction(e->{controller.inviaordine(cart,1,storeid);});
        Button creditButton= new Button("Paga con carta");
        creditButton.setOnAction(e->{controller.inviaordine(cart,1, storeid);});

        Label total=new Label("Totale: "+ String.valueOf(cart.getTot())+"€");
        buttonPay.getChildren().addAll(cashButton,creditButton);
        VBox popUpContent= new VBox(header,scrollProduct,total,buttonPay);
        StackPane popupRoot = new StackPane(overlay, popUpContent);
        popup.getContent().add(popupRoot);
        popup.show(owner);
}
}
