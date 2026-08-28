package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.OrderController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListOrderBean;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Product;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class OrderControllerGrafico {
    @FXML
    Label Title;
    @FXML
    private ImageView Iordini;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Imappa;
    @FXML
    private VBox orderList;
    private ListOrderBean orders;
    private OrderController controller;
    public void inizializza() throws DAOException, SQLException {
        Title.setText("I tuoi ordini:");
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Imappa.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/map.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/packageclicked.png"))));
        controller= new OrderController();
        orders= controller.getOrders();
        for (OrderBean order : orders.getListOrderB()){
            HBox orderline = cardInfoOrder(order);
            orderList.getChildren().add(orderline);
        }
    }

    private void showOrder(OrderBean order) throws DAOException, SQLException {
        StoreBeans store= controller.getInfoStore(order.getStoreIdB());
        OrderBean orderB= controller.getCompleteOrder(order);
        popupOrder(store,orderB);
    }

    private void popupOrder(StoreBeans store, OrderBean orderB) {
        Popup popup = new Popup();

        Stage owner = ApplicazioneStage.getStage();
        Rectangle overlay = new Rectangle(owner.getWidth() - 50, owner.getHeight() - 80, Color.WHITE);

        Label title=new Label("Dettagli ordine:");
        Button buttonClose = new Button("X");
        buttonClose.setOnAction(e -> popup.hide());
        buttonClose.setStyle("-fx-alignment: center-right;");
        HBox top=new HBox(title,buttonClose);

        HBox storeInfo= new HBox(10);
        HBox img;
        if(store.getImage() != null) {
            try {
                InputStream input = store.getImage().getBinaryStream();
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
        Text Name=new Text("Nome: "+ store.getName());
        Text Address=new Text("Indirizzo: "+ store.getAddress());
        Text Tel= new Text("Tel. : "+ store.getTel());
        VBox information= new VBox(Name,Address,Tel);
        storeInfo.getChildren().addAll(img,information);

        VBox productOrderList=new VBox(10);
        List<ProductBean> listB=orderB.getListProductB();
        for(ProductBean p :listB){
            HBox product = cardInfoProduct(p,orderB);
            productOrderList.getChildren().add(product);
        }
        ScrollPane scrollProduct = new ScrollPane(productOrderList);
        VBox all=new VBox(top,storeInfo,scrollProduct);
        popup.getContent().addAll(overlay,all);
        popup.show(owner);
    }
    private HBox cardInfoOrder(OrderBean order){
        Label id =new Label("Numero ordine:"+ order.getOrderIdB());
        Label state= new Label("Stato ordine: "+ order.getStateB());
        Label payment= new Label("Pagamento: "+ order.getTypeOfPayB());
        Label total=new Label("Totale:"+order.getTotalB()+"€");
        Label date=new Label("Data: "+ order.getDateB());
        Button viewOrder =new Button("Visualizza");
        viewOrder.setOnAction(e->{
            try {
                showOrder(order);
            } catch (DAOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        VBox infoorder =new VBox(10);
        VBox otherinfo=new VBox(10);
        infoorder.getChildren().addAll(id,state,payment);
        otherinfo.getChildren().addAll(date,total);
        HBox orderline = new HBox(10);
        orderline.getStyleClass().add("order-row");
        orderline.getChildren().addAll(infoorder,otherinfo,viewOrder);
        return orderline;
    }
    private HBox cardInfoProduct(ProductBean p,OrderBean orderB){
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
        Label numProd= new Label("Qt: "+ String.valueOf(orderB.getQuantityB().get(orderB.getListProductB().indexOf(p))));

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

    public void goToMap(MouseEvent mouseEvent) throws IOException {
        controller.goBackToMap();

    }
}
