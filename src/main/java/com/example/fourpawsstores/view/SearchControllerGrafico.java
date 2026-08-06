package com.example.fourpawsstores.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
//import com.sothawo.mapjfx.*;
//import com.sothawo.mapjfx.event.MapViewEvent;
//import com.sothawo.mapjfx.event.MarkerEvent;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

public class SearchControllerGrafico {
    @FXML
    private TextField address;
    @FXML
    private WebView webViewMap;

    @FXML
    private TextField ncivic;

    public void inizializzamappa() {
        WebEngine engine = webViewMap.getEngine();
        engine.load(getClass().getResource("/map.html").toExternalForm());
    }
}
