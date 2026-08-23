package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.OrderController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListOrderBean;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.domain.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

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
        Title= new Label("I tuoi ordini:");
        Iprofilo.setImage(new Image(getClass().getResourceAsStream("/images/icona.png")));
        Imappa.setImage(new Image(getClass().getResourceAsStream("/images/map.png")));
        Iordini.setImage(new Image(getClass().getResourceAsStream("/images/packageclicked.png")));
        controller= new OrderController();
        orders= controller.getOrders();
        for (OrderBean order : orders.getListOrderB()){
            System.out.println("ordine num"+order.getOrderIdB());
            Label id =new Label("Numero ordine:"+ order.getOrderIdB());
            Label state= new Label("Stato ordine: "+ order.getStateB());
            Label payment= new Label("Pagamento: "+ order.getTypeOfPayB());
            Button viewOrder =new Button("visualizza ordine");
            viewOrder.setOnAction(e->{getOrder();});
            VBox infoorder =new VBox(10);
            infoorder.getChildren().addAll(id,state,payment);
            HBox orderline = new HBox(10);
            orderline.getChildren().addAll(infoorder,viewOrder);
            orderList.getChildren().add(orderline);
        }
    }

    private void getOrder() {
    }
}
