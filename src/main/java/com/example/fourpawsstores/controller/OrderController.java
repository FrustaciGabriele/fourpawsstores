package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListOrderBean;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.domain.FacadeCreateOrder;
import com.example.fourpawsstores.model.domain.ListOrder;
import com.example.fourpawsstores.model.domain.Order;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class OrderController {
    private ListOrder orders;
    private FacadeCreateOrder facade;
    public OrderController(){facade= new FacadeCreateOrder();}
    public ListOrderBean getOrders() throws DAOException, SQLException {
        ListOrderBean ordersBean = new ListOrderBean();
        orders= facade.findOrders();
        for (Order ord: orders.getListOrders()){
            OrderBean ordBean = new OrderBean(ord.getStoreId(),ord.getUserId(),ord.getListProdId(),ord.getQuantity(),ord.getTypeOfPay(),ord.getTotal(),ord.getState(),ord.getDate(),ord.getOrderId());
            ordersBean.addOrder(ordBean);
        }
        return ordersBean;
    }
}
