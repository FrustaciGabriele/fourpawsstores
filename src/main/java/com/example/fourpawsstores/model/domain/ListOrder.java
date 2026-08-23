package com.example.fourpawsstores.model.domain;

import java.util.ArrayList;
import java.util.List;

public class ListOrder {
    private List<Order> listofOrder=new ArrayList<>();
    public void addOrder(Order ord){
        listofOrder.add(ord);
    }
    public List<Order> getListOrders(){return listofOrder;}
}
