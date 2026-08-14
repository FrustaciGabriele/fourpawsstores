package com.example.fourpawsstores.model.bean;

import java.util.ArrayList;
import java.util.List;

public class ListOrderBean {
    private List<OrderBean> listOrderB= new ArrayList<>();
    public void addOrder(OrderBean orderB){
        listOrderB.add(orderB);
    }
}
