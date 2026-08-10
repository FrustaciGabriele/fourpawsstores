package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.SearchController;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.domain.Coordinate;
import com.example.fourpawsstores.utils.utils;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
//import com.sothawo.mapjfx.*;
//import com.sothawo.mapjfx.event.MapViewEvent;
//import com.sothawo.mapjfx.event.MarkerEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

import java.util.Locale;

public class SearchControllerGrafico {
    @FXML
    private TextField address;
    @FXML
    private WebView webViewMap;
    @FXML
    private TextField ncivic;
    @FXML
    private ImageView Icerca;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Imappa;
    @FXML
    private ImageView Iordini;
    private SearchController search=null;
    private  WebEngine engine;


    public void inizializza() {
        Icerca.setImage(new Image(getClass().getResourceAsStream("/images/cerca.png")));
        Iprofilo.setImage(new Image(getClass().getResourceAsStream("/images/icona.png")));
        Imappa.setImage(new Image(getClass().getResourceAsStream("/images/mapclicked.png")));
        Iordini.setImage(new Image(getClass().getResourceAsStream("/images/package.png")));
        engine = webViewMap.getEngine();
        engine.load(getClass().getResource("/map.html").toExternalForm());

    }

    public void handleEnter(ActionEvent actionEvent) {FindAddress();}
    public void FindAddress() {
        search= new SearchController();
        if(
                address.getText().equals("")){
            utils.showErrorPopup("Inserisci un indirizzo");
        }else {
            addressBean addrBean = new addressBean(address.getText());
            SearchStores(addrBean);
            
        }
    }

    private void SearchStores(addressBean addrBean) {
        ListStoresBean Stores;
        try {
            Stores = search.obtainStores(addrBean);
            aggiungiMarker(Stores);

        } catch (IllegalArgumentException e){
            utils.showErrorPopup(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error:"+ e);
            utils.showErrorPopup("Error");
        }


    }

    public void SeeProfile(MouseEvent mouseEvent) {
    }

    public void openOrders(MouseEvent mouseEvent) {
    }
    public void aggiungiMarker(ListStoresBean Stor){
        for (StoreBeans s : Stor.getList()) {
            System.out.println("lat=" +s.getLat());
            System.out.println("lat=" +s.getLon());
            String js =String.format(Locale.US, "addMarker(%.6f, %.6f, '%s')", s.getLat(), s.getLon(), s.getName());
            engine.executeScript(js);
        }
    }
}
