package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.CatalogueController;
import com.example.fourpawsstores.model.bean.CatalogueBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.Catalogue;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class CatalogueControllerGrafico {
    @FXML
    ImageView Back;
    @FXML
    ImageView ShoppingCart;
    private CatalogueBean catB;
    private CatalogueController controller;

    public void inizializza(StoreBeans store) {
        controller= new CatalogueController();
        catB= controller.getCatalogue(store);
    }
}
