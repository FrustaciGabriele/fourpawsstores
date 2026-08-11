package com.example.fourpawsstores.model.domain;

import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.example.fourpawsstores.model.dao.GetCatalogueDAO;

public class FacadeGetCatalogue {
    private Catalogue cat;
    public Catalogue getItems(StoreBeans store) {
        cat= new GetCatalogueDAO().findCat(store.getid());
        return cat;
    }

  
}
