package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.dao.AddProductDAO;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Product;
import com.example.fourpawsstores.model.domain.Profile;
import com.example.fourpawsstores.model.domain.Store;
import com.example.fourpawsstores.utils.utils;
import com.example.fourpawsstores.view.CatalogueStoreControllerGrafico;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

public class AddProductController {
    public  void newProduct(ProductBean prodB) throws DAOException, SQLException {
        Product newProduct= new Product(prodB.getNameB(), prodB.getDescriptionB(), prodB.getImage(), prodB.getPriceB());
        new AddProductDAO().AddProduct(newProduct);
    }

    public boolean checkName(String text) {
        int lenght =text.length();
        if(lenght==0||lenght>45){
            return false;
        }
        return true;
    }

    public boolean checkDescription(String text) {
        int lenght= text.length();
        if (lenght==0||lenght>200){
            return false;
        }
        return true;
    }

    public boolean checkPrice(String text) {
        try {
            BigDecimal price = new BigDecimal(text).setScale(2, RoundingMode.HALF_UP);
            if(price.compareTo(BigDecimal.ZERO)<=0){
            return false;
            }else{
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void goBackScene() throws IOException, DAOException, SQLException {
        FXMLLoader fxmlLoader;
        Stage stages = ApplicazioneStage.getStage();
        Scene scene;

        String fxmlFile;
        fxmlFile = "/com/example/fourpawsstores/negoziante.fxml";

        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());

        Store store= new FindStoresDAO().findStoreById(Profile.getStoreId());
        StoreBeans storeB= new StoreBeans(store.getid(),store.getName(),store.getDescription(),store.getImage(),store.getAddress(),store.getLat(),store.getLon(),store.getIdCatalog(),store.getTel());
        CatalogueStoreControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza(storeB);
        stages.setTitle("4Paws Stores");
        stages.setScene(scene);
        stages.show();
    }
}
