package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.model.bean.CatalogueBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.Catalogue;
import com.example.fourpawsstores.model.domain.FacadeGetCatalogue;

public class CatalogueController {
    Catalogue catalogue;
    FacadeGetCatalogue facade;
    public CatalogueController(){facade=new FacadeGetCatalogue();}
    public CatalogueBean getCatalogue(StoreBeans store) {
       CatalogueBean cat;
       catalogue= facade.getItems(store);

    }
}
