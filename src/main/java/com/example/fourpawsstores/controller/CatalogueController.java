package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CartBean;
import com.example.fourpawsstores.model.bean.CatalogueBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.dao.AddProductDAO;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.example.fourpawsstores.model.domain.*;
import com.example.fourpawsstores.utils.utils;
import com.example.fourpawsstores.view.CatalogueStoreControllerGrafico;
import com.example.fourpawsstores.view.SearchControllerGrafico;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CatalogueController {
    private Catalogue catalogue;
    private FacadeGetCatalogue facade;
    private FacadeCreateOrder facadeOrder;
    private Cart clientCart;
    private int storeid;
    private StoreBeans storeB;
    public CatalogueController(StoreBeans store){
        facade=new FacadeGetCatalogue();
        storeid=store.getid();
        storeB=store;
    }
    public CatalogueBean getCatalogue(StoreBeans store) throws DAOException, SQLException {
       CatalogueBean cat;
       catalogue= facade.getItems(store);
       cat= new CatalogueBean(store.getid());
       for(Product p : catalogue.getList()){
           System.out.println("id prodotto" + p.getId());
           ProductBean prodB = new ProductBean(p.getId(),p.getName(),p.getDescription(),p.getImg(),p.getPrice(),p.getState());
           cat.addProductBean(prodB);
       }
       return cat;
    }
    public CatalogueBean refreshCatalogue() throws DAOException, SQLException {
        catalogue= facade.getItems(storeB);
        CatalogueBean cat = new CatalogueBean(storeB.getid());
        for(Product p : catalogue.getList()){
            System.out.println("id prodotto" + p.getId());
            ProductBean prodB = new ProductBean(p.getId(),p.getName(),p.getDescription(),p.getImg(),p.getPrice(),p.getState());
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

    public boolean checkLenght() {
        if (catalogue.getList().size()>0){
            return true;
        }
        return false;
    }

    public void goBackScene() throws IOException {
        FXMLLoader fxmlLoader;
        Stage stages = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile;
        fxmlFile = "/com/example/fourpawsstores/utente.fxml";

        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());

        SearchControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza();
        stages.setTitle("4Paws Stores");
        stages.setScene(scene);
        stages.show();
    }

    public void changeProductState(ProductBean prodB) throws DAOException, SQLException {
        int prodid=prodB.getId();
        for (Product p : catalogue.getList()){
            if (p.getId()== prodid){
                String text;
                if(p.getState().equals("disponibile")){text="non disponibile";}else{text="disponibile";}
                System.out.println("sto modificando:"+p.getId()+"con :"+text);
                new AddProductDAO().changeStateProduct(p,text);
            }
        }
    }
    public void deleteProduct(ProductBean prodB) throws DAOException, SQLException {
        int prodid=prodB.getId();
        for (Product p : catalogue.getList()){
            if (p.getId()==prodid){
                new AddProductDAO().deleteProduct(p);

            }
        }
    }
}

