package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.controller.OrderController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListOrderBean;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.bean.ProductBean;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class StoresOrderControllerGrafico extends InfoCardControllerGrafico{
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Icatalogo;
    @FXML
    private ImageView Iordini;
    @FXML
    private VBox orderList;
    @FXML
    private Label Title;
    private OrderController controller;
    private ListOrderBean orders;
    private static final String SETTING1 = "TimesNewRoman";
    public void inizializza() throws DAOException, SQLException {
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Icatalogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/catalogo.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/packageclicked.png"))));
        Title.setText("I tuoi ordini:");
        controller=new OrderController();
        orders=controller.getOrdersStore();
        for (OrderBean order : orders.getListOrderB()){
            HBox orderline = cardInfoOrder(order);
            orderList.getChildren().add(orderline);
        }

    }
    @Override
    protected void showOrder(OrderBean order) throws DAOException, SQLException {
        OrderBean orderB= controller.getCompleteOrder(order);
        popupOrder(orderB);
    }

    private void popupOrder(OrderBean orderB) {
        Popup popup = new Popup();

        Stage owner = ApplicazioneStage.getStage();
        Rectangle overlay = new Rectangle(owner.getWidth() - 50, owner.getHeight() - 80, Color.WHITE);
        overlay.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");

        Label title=new Label("Dettagli ordine:");
        title.setFont(Font.font(SETTING1, FontWeight.BOLD, 18));
        title.setStyle("-fx-alignment: center");
        Button buttonClose = new Button("X");
        buttonClose.setOnAction(e -> popup.hide());
        HBox top=new HBox(title,buttonClose);
        Label clientName=new Label("Ordine per: "+orderB.getUserIdB());
        HBox infoClient= new HBox(clientName);
        VBox productOrderList=new VBox(10);
        List<ProductBean> listB=orderB.getListProductB();
        for(ProductBean p :listB){
            HBox product = cardInfoProduct(p,orderB);
            productOrderList.getChildren().add(product);
        }
        ScrollPane scrollProductOrder = new ScrollPane(productOrderList);
        scrollProductOrder.setPrefHeight(400);
        scrollProductOrder.setPrefWidth(355);
        scrollProductOrder.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollProductOrder.setFitToWidth(true);

        productOrderList.setFillWidth(true);
        productOrderList.maxWidthProperty().bind(scrollProductOrder.widthProperty());
        VBox all;
        if (orderB.getStateB().equals("in attesa")){
            HBox buttons= new HBox(10);
            buttons.setAlignment(Pos.BOTTOM_CENTER);
            Button acceptOrder =new Button("Accetta");
            acceptOrder.setOnAction(e->{
                try {
                    controller.acceptOrder(orderB);
                } catch (SQLException ex) {
                    utils.showErrorPopup("Errore");
                    throw new RuntimeException(ex);
                }
                popup.hide();
                utils.openAdvisepopup("Ordine accettato");
                try {
                    refreshUIOrder();
                } catch (DAOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button rejectOrder =new Button("Rifiuta");
            rejectOrder.setOnAction(e->{
                try {
                    controller.rejectOrder(orderB);
                } catch (SQLException ex) {
                    utils.showErrorPopup("Errore");
                    throw new RuntimeException(ex);
                }
                popup.hide();
                utils.openAdvisepopup("Ordine rifiutato");
                try {
                    refreshUIOrder();
                } catch (DAOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            buttons.getChildren().addAll(acceptOrder,rejectOrder);
             all=new VBox(top,infoClient,scrollProductOrder,buttons);
        }else {
             all= new VBox(top,infoClient,scrollProductOrder);
        }
        all.maxWidthProperty().bind(overlay.widthProperty());
        all.maxHeightProperty().bind(overlay.heightProperty());
        all.prefWidthProperty().bind(overlay.widthProperty());
        all.prefHeightProperty().bind(overlay.heightProperty());
        popup.getContent().addAll(overlay,all);
        popup.show(owner);
    }

    private void refreshUIOrder() throws DAOException, SQLException {
        orders= controller.getOrdersStore();
        orderList.getChildren().clear();
        for (OrderBean order : orders.getListOrderB()){
            HBox orderline = cardInfoOrder(order);
            orderList.getChildren().add(orderline);
        }
    }

    public void goToCatalog(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.goBackSceneCatStore();
    }

    public void goToProfile(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.goToProfileStore();
    }
}
