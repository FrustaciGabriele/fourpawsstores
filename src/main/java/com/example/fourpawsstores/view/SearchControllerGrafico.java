package com.example.fourpawsstores.view;

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


    public void inizializza() {
        Icerca.setImage(new Image(getClass().getResourceAsStream("/images/cerca.png")));
        WebEngine engine = webViewMap.getEngine();
        engine.load(getClass().getResource("/map.html").toExternalForm());

    }

    public void handleEnter(ActionEvent actionEvent) {
    }

    public void SearchStores(MouseEvent mouseEvent) {
    }
}
