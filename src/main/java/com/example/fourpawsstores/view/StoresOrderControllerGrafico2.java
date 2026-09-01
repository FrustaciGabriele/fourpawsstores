package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.controller.OrderController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListOrderBean;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.bean.ProductBean;
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
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class StoresOrderControllerGrafico2 extends InfoCardControllerGrafico{
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Icatalogo;
    @FXML
    private ImageView Iordini;
    @FXML
    private VBox orderList;
    @FXML
    private VBox orderBox;
    private OrderController controller;
    private ListOrderBean orders;
    public void inizializza() throws DAOException, SQLException {
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Icatalogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/catalogo.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/packageclicked.png"))));
        controller=new OrderController();
        orders=controller.getOrdersStore();
        if (orders.getListOrderB().size()==0){
            orderList.getChildren().add(new Label("Non sono prenseti ordini"));
        }else {
            for (OrderBean order : orders.getListOrderB()) {
                HBox orderline = cardInfoOrder(order);
                orderList.getChildren().add(orderline);
            }
            orderBox.getChildren().add(new Label("Nessun ordine selezionato"));
        }
    }

    @Override
    protected void showOrder(OrderBean order) throws DAOException, SQLException {
        OrderBean orderB= controller.getCompleteOrder(order);
        orderBox.getChildren().clear();
        orderBox.getChildren().add(showOrderBox(orderB));
    }

    private VBox showOrderBox(OrderBean orderB) {
        Label client=new Label("Ordine per: "+orderB.getUserIdB());
        HBox infoClinet= new HBox(client);
        VBox productsOrderList=new VBox(10);
        List<ProductBean> listB=orderB.getListProductB();
        for(ProductBean p :listB){
            HBox product = cardInfoProduct(p,orderB);
            productsOrderList.getChildren().add(product);
        }
        ScrollPane scrollProductBox = new ScrollPane(productsOrderList);
        scrollProductBox.setPrefHeight(400);
        scrollProductBox.setPrefWidth(355);
        scrollProductBox.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollProductBox.setFitToWidth(true);
        productsOrderList.setFillWidth(true);
        productsOrderList.maxWidthProperty().bind(scrollProductBox.widthProperty());
        VBox all;
        if (orderB.getStateB().equals("in attesa")){
            HBox buttons= new HBox(10);
            buttons.setAlignment(Pos.BOTTOM_CENTER);
            Button accept =new Button("Accetta");
            accept.setOnAction(e->{
                try {
                    controller.acceptOrder(orderB);
                } catch (SQLException ex) {
                    utils.showErrorPopup("Errore");
                    throw new RuntimeException(ex);
                }
                utils.openAdvisepopup("Ordine accettato");
                try {
                    refreshUI();
                } catch (DAOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button reject =new Button("Rifiuta");
            reject.setOnAction(e->{
                try {
                    controller.rejectOrder(orderB);
                } catch (SQLException ex) {
                    utils.showErrorPopup("Errore");
                    throw new RuntimeException(ex);
                }
                utils.openAdvisepopup("Ordine rifiutato");
                try {
                    refreshUI();
                } catch (DAOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            buttons.getChildren().addAll(accept,reject);
            all=new VBox(infoClinet,scrollProductBox,buttons);
        }else {
            all= new VBox(infoClinet,scrollProductBox);
        }
        return all;
    }

    private void refreshUI() throws DAOException, SQLException {
        orders= controller.getOrdersStore();
        orderList.getChildren().clear();
        for (OrderBean order : orders.getListOrderB()){
            HBox orderline = cardInfoOrder(order);
            orderList.getChildren().add(orderline);
        }
        orderBox.getChildren().clear();
    }

    public void gotoCatalog(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.goBackSceneCatStore();
    }

    public void goToProfileStore(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.goToProfileStore();
    }
}
