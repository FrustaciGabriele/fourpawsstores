package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.bean.coordinateBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import com.example.fourpawsstores.model.domain.Coordinate;
import com.example.fourpawsstores.model.domain.FacadeGetStores;
import com.example.fourpawsstores.model.domain.ListStores;
import com.example.fourpawsstores.model.domain.Store;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class SearchController {
    ListStores Stores;
    FacadeGetStores facade;
    public SearchController(){facade=new FacadeGetStores();}
    public ListStoresBean obtainStores(addressBean addrBean)  throws DAOException, SQLException, IOException {
        ListStoresBean StoresB;
        coordinateBean coordB;
        Coordinate coordinate= Coordinate.addressConvert(addrBean);
        coordB= new coordinateBean(coordinate.getAddress(),coordinate.getlon(),coordinate.getlat());
        Stores= facade.getListStores(coordinate);
        StoresB=new ListStoresBean(coordB.getAddressB(),coordB.getLatitudineB(),coordB.getLongitudineB());
        for (Store store: Stores.getList()){
            StoreBeans storeB=new StoreBeans(store.getid(),store.getName(),store.getDescription(),store.getImage(),store.getAddress(),store.getLat(),store.getLon(),store.getIdCatalog());
            StoresB.addStore(storeB);
        }

        return StoresB;
    }


}
