package com.example.fourpawsstores.model.domain;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.dao.DEMODAO;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.example.fourpawsstores.utils.utils;

import java.sql.SQLException;

public class FacadeGetStores {
    private static Coordinate coordinate;
    private static ListStores ListStores;

    public static ListStores getListStores(Coordinate coord) throws DAOException, SQLException {
        if (coordinate == null || (!coordinate.getlat().equals(coord.getlat()) || !coordinate.getlon().equals(coord.getlon()))) {
            if (utils.getMode()==0){
            ListStores = new FindStoresDAO().FindStores(coord);}
            else {
                ListStores= new DEMODAO().findStores(coord);
            }
        }
        return ListStores;
    }
}
