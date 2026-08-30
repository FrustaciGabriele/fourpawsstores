package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.SearchController;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.utils.utils;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.util.Locale;
import java.util.Objects;

public class SearchControllerGrafico2 {
    @FXML
    private WebView webMap;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Imappa;
    @FXML
    private ImageView Iordini;
    @FXML
    private TextField street;
    @FXML
    private TextField civic;
    @FXML
    private TextField city;
    private WebEngine engine;
    private String address;
    private ListStoresBean ListStores;
    private SearchController controller;
    public void inizializza(){
        controller= new SearchController();
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Imappa.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/mapclicked.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/package.png"))));
        engine = webMap.getEngine();
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("javaApp", this);
            }
        });
        engine.load(getClass().getResource("/map.html").toExternalForm());
    }
    public void findAddress(MouseEvent mouseEvent) {
        if(street.getText().equals("")|| civic.getText().equals("")|| city.getText().equals("")){
            utils.showErrorPopup("riempi tutti i campi");
        }else {
            address= street.getText() +" "+ civic.getText()+ ","  + city.getText();
            addressBean addrBean = new addressBean(address);
            SearchStores(addrBean);
        }

    }
    private void SearchStores(addressBean addrBean) {
        removeMarkers();
        try {
            ListStores = controller.obtainStores(addrBean);
            engine.executeScript("centerMap(" + ListStores.getLatB() + "," + ListStores.getLonB() + ")");
            aggiungiMarker(ListStores);

        } catch (IllegalArgumentException e){
            utils.showErrorPopup(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error:"+ e);
            utils.showErrorPopup("Error");
        }


    }
    public void aggiungiMarker(ListStoresBean Stor){
        for (StoreBeans s : Stor.getList()) {
            String js =String.format(Locale.US, "addMarker(%.6f, %.6f,%d, '%s')", s.getLat(), s.getLon(), s.getid(), s.getName());
            engine.executeScript(js);
        }
    }
    private void removeMarkers() {
        engine.executeScript("removeMarkers()");
    }
    public void SeeProfile(MouseEvent mouseEvent) {
    }

    public void openOrders(MouseEvent mouseEvent) {
    }

    public void handleEnter(ActionEvent actionEvent) {
    }


}
