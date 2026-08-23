package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.bean.coordinateBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import com.example.fourpawsstores.model.domain.*;
import com.example.fourpawsstores.utils.utils;
import com.example.fourpawsstores.view.CatalogueControllerGrafico;
import com.example.fourpawsstores.view.OrderControllerGrafico;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SearchController {
    ListStores Stores;
    FacadeGetStores facade;
    public SearchController(){facade=new FacadeGetStores();}


    public ListStoresBean obtainStores(addressBean addrBean)  throws DAOException, SQLException, IOException {
        ListStoresBean StoresB;
        coordinateBean coordB;
        Coordinate coordinate= Coordinate.addressConvert(addrBean);
        coordB= new coordinateBean(coordinate.getAddress(), Coordinate.getlon(), Coordinate.getlat());
        Stores= facade.getListStores(coordinate);
        StoresB=new ListStoresBean(coordB.getAddressB(),coordB.getLatitudineB(),coordB.getLongitudineB());
        for (Store store: Stores.getList()){
            StoreBeans storeB=new StoreBeans(store.getid(),store.getName(),store.getDescription(),store.getImage(),store.getAddress(),store.getLat(),store.getLon(),store.getIdCatalog(),store.getTel());
            StoresB.addStore(storeB);
        }

        return StoresB;
    }

    public void showCatalogue(StoreBeans store) throws IOException, DAOException, SQLException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        fxmlFile="/com/example/fourpawsstores/catalogo.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        final CatalogueControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza(store);
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());


        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();

    }

    public void obtainOrders() throws IOException, DAOException, SQLException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        fxmlFile="/com/example/fourpawsstores/ordini.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        final OrderControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza();
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());


        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }
}
