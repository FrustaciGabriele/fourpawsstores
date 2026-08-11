package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CatalogueBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.Catalogue;
import com.example.fourpawsstores.model.domain.FacadeGetCatalogue;
import com.example.fourpawsstores.model.domain.Product;
import javafx.scene.layout.HBox;

import java.sql.SQLException;

public class CatalogueController {
    Catalogue catalogue;
    FacadeGetCatalogue facade;
    public CatalogueController(){facade=new FacadeGetCatalogue();}
    public CatalogueBean getCatalogue(StoreBeans store) throws DAOException, SQLException {
       CatalogueBean cat;
       catalogue= facade.getItems(store);
       cat= new CatalogueBean(store.getid());
       for(Product p : catalogue.getList()){
           System.out.println("id prodotto" + p.getId());
           ProductBean prodB = new ProductBean(p.getId(),p.getName(),p.getDescription(),p.getImg(),p.getPrice(),p.getStoc());
           cat.addProductBean(prodB);
       }
       return cat;
    }

    public void addToCart(HBox product) {
    }
}
