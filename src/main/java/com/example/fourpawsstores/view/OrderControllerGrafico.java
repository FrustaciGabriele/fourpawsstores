package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.OrderController;
import com.example.fourpawsstores.model.bean.ListOrderBean;
import com.example.fourpawsstores.model.bean.OrderBean;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
    public void inizializza() {
        Title= new Label("I tuoi ordini:");
        Iprofilo.setImage(new Image(getClass().getResourceAsStream("/images/icona.png")));
        Imappa.setImage(new Image(getClass().getResourceAsStream("/images/map.png")));
        Iordini.setImage(new Image(getClass().getResourceAsStream("/images/packageclicked.png")));
        controller= new OrderController();
        orders= controller.getOrders();
    }
}
