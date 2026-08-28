package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListOrderBean;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.dao.FindOrdersDao;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.example.fourpawsstores.model.domain.*;
import com.example.fourpawsstores.utils.utils;
import com.example.fourpawsstores.view.SearchControllerGrafico;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderController {
    private ListOrder orders;
    private FacadeCreateOrder facade;
    public OrderController(){facade= new FacadeCreateOrder();}
    public ListOrderBean getOrders() throws DAOException, SQLException {
        ListOrderBean ordersBean = new ListOrderBean();
        orders= facade.findOrders();
        for (Order ord: orders.getListOrders()){
            OrderBean ordBean = new OrderBean(ord.getStoreId(),ord.getUserId(),ord.getTypeOfPay(),ord.getTotal(),ord.getState(),ord.getDate(),ord.getOrderId());
            ordersBean.addOrder(ordBean);
        }
        return ordersBean;
    }

    public OrderBean getCompleteOrder(OrderBean order) throws DAOException, SQLException {
        Order completeOrder= new FindOrdersDao().getCompleteOrder(order);
        List<Product> list= completeOrder.getListProduct();
        List<Integer> listQuantity= completeOrder.getQuantity();
        List<Integer> listid=completeOrder.getListProdId();
        for (Product p : list){
            ProductBean prodB= new ProductBean(p.getId(),p.getName(),p.getDescription(),p.getImg(),p.getPrice(),p.getState());
            order.addToProductListB(prodB);
            order.addToQuantityB(listQuantity.get(list.indexOf(p)));
            order.addToProdIdB(listid.get(list.indexOf(p)));
        }
    return order;
    }

    public StoreBeans getInfoStore(int storeIdB) throws DAOException, SQLException {
        Store store=  new FindStoresDAO().findStoreById(storeIdB);
        StoreBeans storeB= new StoreBeans(store.getid(),store.getName(),store.getDescription(),store.getImage(),store.getAddress(), store.getLat(), store.getLon(), store.getIdCatalog(),store.getTel());
        return storeB;
    }

    public void goBackToMap() throws IOException {
        FXMLLoader fxmlLoad;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        String FxmlRole = "/com/example/fourpawsstores/utente.fxml";
        fxmlLoad = new FXMLLoader();
        Parent rootNode = fxmlLoad.load(getClass().getResourceAsStream(FxmlRole));
        SearchControllerGrafico controller=fxmlLoad.getController();
        controller.inizializza();
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());
        scene.getRoot().requestFocus();
        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show(); stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }
}
