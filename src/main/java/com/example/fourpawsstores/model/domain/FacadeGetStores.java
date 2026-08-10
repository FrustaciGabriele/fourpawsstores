package com.example.fourpawsstores.model.domain;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.bean.coordinateBean;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class FacadeGetStores {
    private static Coordinate coordinate;
    private static ListStores ListStores;

    public ListStores getListStores(Coordinate coord) throws DAOException, SQLException {
        if (this.coordinate == null || (!this.coordinate.getlat().equals(Coordinate.getlat()) || !this.coordinate.getlon().equals(Coordinate.getlon()))) {
            ListStores = new FindStoresDAO().FindStores(coordinate);
        }
        return ListStores;
    }
}
