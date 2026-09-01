package com.example.fourpawsstores.model.domain;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.dao.DEMODAO;
import com.example.fourpawsstores.model.dao.FindStoresDAO;
import com.example.fourpawsstores.utils.utils;
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
    public static Coordinate addressConvert(addressBean addrBean) throws IOException {
        String address=addrBean.getIndirizzo();

        String indirizzoEcoded = encodeValue(address);
        URL url = new URL("https://photon.komoot.io/api/?q=" + indirizzoEcoded);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "FourPawsStoresApp/1.0");

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }
        rd.close();

        JsonObject obj = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonArray features = obj.getAsJsonArray("features");
        JsonObject first = features.get(0).getAsJsonObject();
        JsonObject geometry = first.getAsJsonObject("geometry");
        JsonArray coords = geometry.getAsJsonArray("coordinates");

        double lon = coords.get(0).getAsDouble();
        double lat = coords.get(1).getAsDouble();
        System.out.println("lat:"+ lat );
        System.out.println("lat:"+ lon );

        return new Coordinate(address,lon,lat);
    }
    private static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}
