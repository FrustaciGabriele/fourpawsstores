package com.example.fourpawsstores.model.domain;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import java.sql.SQLException;

public class FacadeGetStores {
    private static Coordinate coordinate;
    private static ListStores ListStores;

    public ListStores getListStores(Coordinate coord) throws DAOException, SQLException {
        if (this.coordinate == null || (!this.coordinate.getlat().equals(coord.getlat()) || !this.coordinate.getlon().equals(coord.getlon()))) {
            ListStores = new FindStoresDAO().FindStores(coord);
        }
        return ListStores;
    }
}
