package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CartBean;
import com.example.fourpawsstores.model.bean.CatalogueBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.*;

import java.sql.SQLException;

public class CatalogueController {
    private Catalogue catalogue;
    private FacadeGetCatalogue facade;
    private FacadeGetOrder facadeOrder;
    public CatalogueController(){facade=new FacadeGetCatalogue();}
    public CatalogueBean getCatalogue(StoreBeans store) throws DAOException, SQLException {
       CatalogueBean cat;
       catalogue= facade.getItems(store);
       cat= new CatalogueBean(store.getid());
       for(Product p : catalogue.getList()){
           System.out.println("id prodotto" + p.getId());
           ProductBean prodB = new ProductBean(p.getId(),p.getName(),p.getDescription(),p.getImg(),p.getPrice());
           cat.addProductBean(prodB);
       }
       return cat;
    }

    public void inviaordine(CartBean cart, int type, int storeid) throws DAOException, SQLException {
        String paymentType;
        if (type==1){
            paymentType="Paga al ritiro";
        }
        else{
            paymentType="Paga con carta";
        }
        facadeOrder=new FacadeGetOrder();
        Order newOrder= facadeOrder.createOrder(cart,storeid,paymentType);
        facadeOrder.insertOrder(newOrder);
    }
}
