package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.bean.coordinateBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
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
    public ListStoresBean obtainStores(addressBean addrBean)  throws DAOException, SQLException, IOException {
        ListStoresBean Stores;
        coordinateBean coord;
        coord=addressConvert(addrBean);

        return Stores;
    }

    private coordinateBean addressConvert(addressBean addrBean) throws IOException {
        String address=addrBean.getIndirizzo();

        String indirizzoEcoded = encodeValue(address);
        // URL a cui eseguire la richiesta GET
        URL url = new URL("https://nominatim.openstreetmap.org/search?format=geocodejson&q=" + indirizzoEcoded);

        // Apertura della connessione
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Lettura della risposta
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\n');
        }
        rd.close();

        JsonArray results = JsonParser.parseString(response.toString()).getAsJsonArray();
        JsonObject firstResult = results.get(0).getAsJsonObject();

        String latStr = firstResult.get("lat").getAsString();
        String lonStr = firstResult.get("lon").getAsString();

        return new coordinateBean(address,lonStr,latStr);
    }
    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}
