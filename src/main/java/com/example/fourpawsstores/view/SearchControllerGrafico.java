package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.SearchController;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.utils.utils;
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


    public void inizializza() {
        Icerca.setImage(new Image(getClass().getResourceAsStream("/images/cerca.png")));
        Iprofilo.setImage(new Image(getClass().getResourceAsStream("/images/icona.png")));
        Imappa.setImage(new Image(getClass().getResourceAsStream("/images/mapclicked.png")));
        Iordini.setImage(new Image(getClass().getResourceAsStream("/images/package.png")));
        WebEngine engine = webViewMap.getEngine();
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
        } catch (IllegalArgumentException e){
            utils.showErrorPopup(e.getMessage());
        } catch (Exception e) {
            utils.showErrorPopup("Error");
        }
    }

    public void SeeProfile(MouseEvent mouseEvent) {
    }

    public void openOrders(MouseEvent mouseEvent) {
    }
}
