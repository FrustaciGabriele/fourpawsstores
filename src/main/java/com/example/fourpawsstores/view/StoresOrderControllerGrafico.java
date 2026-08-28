package com.example.fourpawsstores.view;

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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.InputStream;
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
    public void inizializza() throws DAOException, SQLException {
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Icatalogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
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

        Label title=new Label("Dettagli ordine:");
        Button buttonClose = new Button("X");
        buttonClose.setOnAction(e -> popup.hide());
        buttonClose.setStyle("-fx-alignment: center-right;");
        HBox top=new HBox(title,buttonClose);
        Label client=new Label("Ordine per: "+orderB.getUserIdB());
        HBox infoClinet= new HBox(client);
        VBox productOrderList=new VBox(10);
        List<ProductBean> listB=orderB.getListProductB();
        for(ProductBean p :listB){
            HBox product = cardInfoProduct(p,orderB);
            productOrderList.getChildren().add(product);
        }
        ScrollPane scrollProduct = new ScrollPane(productOrderList);
        VBox all;
        if (orderB.getStateB().equals("in attesa")){
            HBox buttons= new HBox(10);
            Button accept =new Button("Accetta");
            accept.setOnAction(e->{
                try {
                    controller.acceptOrder(orderB);
                } catch (SQLException ex) {
                    utils.showErrorPopup("Errore");
                    throw new RuntimeException(ex);
                }
                popup.hide();
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
                popup.hide();
                utils.openAdvisepopup("Ordine rifiutato");
                try {
                    refreshUI();
                } catch (DAOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            buttons.getChildren().addAll(accept,reject);
             all=new VBox(top,infoClinet,scrollProduct,buttons);
        }else {
             all= new VBox(top,infoClinet,scrollProduct);
        }
        popup.getContent().addAll(overlay,all);
        popup.show(owner);
    }

    private void refreshUI() throws DAOException, SQLException {
        orders= controller.getOrdersStore();
        orderList.getChildren().clear();
        for (OrderBean order : orders.getListOrderB()){
            HBox orderline = cardInfoOrder(order);
            orderList.getChildren().add(orderline);
        }
    }

    public void goToCatalog(MouseEvent mouseEvent) {
    }

}
