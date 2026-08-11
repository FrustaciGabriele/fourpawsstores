package com.example.fourpawsstores.model.domain;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.example.fourpawsstores.model.dao.GetCatalogueDAO;

import java.sql.SQLException;

public class FacadeGetCatalogue {
    private Catalogue cat;
    public Catalogue getItems(StoreBeans store) throws DAOException, SQLException {
        cat= new GetCatalogueDAO().findCat(store.getid());
        return cat;
    }

  
}
