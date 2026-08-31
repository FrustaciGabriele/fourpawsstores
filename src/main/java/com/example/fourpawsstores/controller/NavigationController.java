package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.FourPawsApplication;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.dao.DEMODAO;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Profile;
import com.example.fourpawsstores.model.domain.Store;
import com.example.fourpawsstores.utils.utils;
import com.example.fourpawsstores.view.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class NavigationController {

    public void changeUIStyle() throws IOException {
        utils.switchGUI();
        FXMLLoader fxmlLoader;
        Stage stage= ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        if (utils.getGUI()==0){
            fxmlFile="/com/example/fourpawsstores/Login.fxml";
        }else{
            fxmlFile="/com/example/fourpawsstores/Login2.fxml";
        }
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());
        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void profileScene() throws IOException, DAOException, SQLException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        Parent rootNode;
        if (utils.getGUI()==0){
        fxmlFile="/com/example/fourpawsstores/profiloUtente.fxml";
        fxmlLoader = new FXMLLoader();
        rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        final ClientProfileControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza();
        }else{
            fxmlFile="/com/example/fourpawsstores/profiloUtente2.fxml";
            fxmlLoader = new FXMLLoader();
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
            final ClientProfileControllerGrafico controller=fxmlLoader.getController();
            controller.inizializza();
        }
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
        Parent rootNode;
        if(utils.getGUI()==0){
        fxmlFile="/com/example/fourpawsstores/ordini.fxml";
        fxmlLoader = new FXMLLoader();
        rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        final OrderControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza();}
        else {
            fxmlFile="/com/example/fourpawsstores/ordini2.fxml";
            fxmlLoader = new FXMLLoader();
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
            final OrderControllerGrafico2 controller=fxmlLoader.getController();
            controller.inizializza();
        }

        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());


        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }
    public void goBackToMap() throws IOException {
        FXMLLoader fxmlLoad;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        Parent rootNode;
        String FxmlRole;
        if (utils.getGUI()==0){
        FxmlRole = "/com/example/fourpawsstores/utente.fxml";
        fxmlLoad = new FXMLLoader();
        rootNode = fxmlLoad.load(getClass().getResourceAsStream(FxmlRole));
        SearchControllerGrafico controller=fxmlLoad.getController();
        controller.inizializza();}
        else {
            FxmlRole = "/com/example/fourpawsstores/utente2.fxml";
            fxmlLoad = new FXMLLoader();
            rootNode = fxmlLoad.load(getClass().getResourceAsStream(FxmlRole));
            SearchControllerGrafico2 controller=fxmlLoad.getController();
            controller.inizializza();
        }
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());
        scene.getRoot().requestFocus();
        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show(); stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }
    public void goToOrderSceneStore() throws DAOException, SQLException, IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        Parent rootNode;
        if(utils.getGUI()==0){
        fxmlFile="/com/example/fourpawsstores/ordiniNegoziante.fxml";
        fxmlLoader = new FXMLLoader();
        rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        final StoresOrderControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza();}
        else {
            fxmlFile="/com/example/fourpawsstores/ordiniNegoziante2.fxml";
            fxmlLoader = new FXMLLoader();
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
            final StoresOrderControllerGrafico2 controller=fxmlLoader.getController();
            controller.inizializza();
        }
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());


        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }
    public void goToProfileStore() throws IOException, DAOException, SQLException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        Parent rootNode;
        if (utils.getGUI()==0){
        fxmlFile="/com/example/fourpawsstores/profiloNegozio.fxml";
        fxmlLoader = new FXMLLoader();
        rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        final StoreProfileControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza();}
        else {
            fxmlFile="/com/example/fourpawsstores/profiloNegozio2.fxml";
            fxmlLoader = new FXMLLoader();
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
            final StoreProfileControllerGrafico controller=fxmlLoader.getController();
            controller.inizializza();
        }

        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());
        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }
    public void goBackSceneCatStore() throws IOException, DAOException, SQLException {
        FXMLLoader fxmlLoader;
        Stage stages = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        Parent rootNode;
        if (utils.getGUI()==0){
            fxmlFile = "/com/example/fourpawsstores/negoziante.fxml";
            fxmlLoader = new FXMLLoader();
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
            Store store;
            if(utils.getMode()==0){
            store= new FindStoresDAO().findStoreById(Profile.getStoreId());}
            else {
                store= new DEMODAO().getStore(Profile.getStoreId());
            }
            StoreBeans storeB= new StoreBeans(store.getid(),store.getName(),store.getDescription(),store.getImage(),store.getAddress(),store.getLat(),store.getLon(),store.getIdCatalog(),store.getTel());
            CatalogueStoreControllerGrafico controller=fxmlLoader.getController();
            controller.inizializza(storeB);
        }else{
            fxmlFile = "/com/example/fourpawsstores/negoziante2.fxml";
            fxmlLoader = new FXMLLoader();
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
            Store store;
            if(utils.getMode()==0){
                store= new FindStoresDAO().findStoreById(Profile.getStoreId());}
            else {
                store= new DEMODAO().getStore(Profile.getStoreId());
            }
            StoreBeans storeB= new StoreBeans(store.getid(),store.getName(),store.getDescription(),store.getImage(),store.getAddress(),store.getLat(),store.getLon(),store.getIdCatalog(),store.getTel());
            CatalogueStoreControllerGrafico2 controller=fxmlLoader.getController();
            controller.inizializza(storeB);
        }
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());
        stages.setTitle("4Paws Stores");
        stages.setScene(scene);
        stages.show();
    }

    public void showCatalogue(StoreBeans store) throws IOException, DAOException, SQLException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        Parent rootNode;
        if(utils.getGUI()==0){
            fxmlFile="/com/example/fourpawsstores/catalogo.fxml";
            fxmlLoader = new FXMLLoader();
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
            final CatalogueControllerGrafico controller=fxmlLoader.getController();
            controller.inizializza(store);}
        else {
            fxmlFile="/com/example/fourpawsstores/catalogo2.fxml";
            fxmlLoader = new FXMLLoader();
            rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
            final CatalogueControllerGrafico2 controller=fxmlLoader.getController();
            controller.inizializza(store);
        }
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());
        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();

    }
}
