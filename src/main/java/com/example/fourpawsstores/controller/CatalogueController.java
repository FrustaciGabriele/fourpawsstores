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
    private FacadeCreateOrder facadeOrder;
    private Cart clientCart;
    private int storeid;
    public CatalogueController(StoreBeans store){
        facade=new FacadeGetCatalogue();
        storeid=store.getid();
    }
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
    public CartBean createCart(){
        clientCart=new Cart();
        CartBean cartB =new CartBean();
        return cartB;
    }
    public boolean addProduct(ProductBean prodB, int q){
        int idProdB = prodB.getId();
        for (Product p : catalogue.getList()){
            if (p.getId()== idProdB){
                clientCart.addToCart(p,q);
                return true;
            }
        }
        return false;
    }
    public boolean removeProduct(ProductBean prodB, int q){
        int idProdB = prodB.getId();
        if (q>=1){
            q=q-1;
        for (Product p : catalogue.getList()){
            if (p.getId()== idProdB){
                clientCart.deletefromCart(p,q);
                return true;
            }
        }}
        return false;
    }
    public void inviaordine(int type) throws DAOException, SQLException {
        String paymentType;
        if (clientCart.getLenght()>0){
        if (type==1){
            paymentType="Paga al ritiro";
        }
        else{
            paymentType="Paga con carta";
        }
        facadeOrder=new FacadeCreateOrder();
        Order newOrder= facadeOrder.createOrder(clientCart,storeid,paymentType);
        facadeOrder.insertOrder(newOrder);}
    }
}
