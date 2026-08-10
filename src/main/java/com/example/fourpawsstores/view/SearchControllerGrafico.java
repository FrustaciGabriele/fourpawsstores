package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.SearchController;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.utils.utils;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.stage.Popup;
import javafx.stage.Stage;
import netscape.javascript.JSObject;


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
    private ListStoresBean Stores;


    public void inizializza() {
        Icerca.setImage(new Image(getClass().getResourceAsStream("/images/cerca.png")));
        Iprofilo.setImage(new Image(getClass().getResourceAsStream("/images/icona.png")));
        Imappa.setImage(new Image(getClass().getResourceAsStream("/images/mapclicked.png")));
        Iordini.setImage(new Image(getClass().getResourceAsStream("/images/package.png")));
        engine = webViewMap.getEngine();
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("javaApp", this);
            }
        });
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
        try {
            Stores = search.obtainStores(addrBean);
            engine.executeScript("centerMap(" + Stores.getLatB() + "," + Stores.getLonB() + ")");
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
            String js =String.format(Locale.US, "addMarker(%.6f, %.6f,%d, '%s')", s.getLat(), s.getLon(), s.getid(), s.getName());
            engine.executeScript(js);
        }
    }

    @SuppressWarnings("unused")
    public void onMarkerClicked(int id) {
        StoreBeans store = Stores.getById(id);
       if(store!=null){
        openStorePopup(store);}
       else  {utils.showErrorPopup("Error");}
    }

    private void openStorePopup(StoreBeans store) {
        Popup popup = new Popup();

        Stage owner = ApplicazioneStage.getStage();

        // Crea l'overlay nero
        Rectangle overlay = new Rectangle(owner.getWidth() - 50, owner.getHeight() - 80, Color.WHITE);
        //overlay.setOpacity(0.3);

        // Crea il pulsante di chiusura
        Button buttonClose = new Button("X");
        buttonClose.setOnAction(e -> popup.hide());
        buttonClose.setStyle("-fx-alignment: center-right;");

        popup.getContent().addAll(overlay, buttonClose);
        popup.show(owner);
    }

}
