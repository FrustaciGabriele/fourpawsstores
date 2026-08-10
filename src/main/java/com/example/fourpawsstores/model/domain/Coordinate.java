package com.example.fourpawsstores.model.domain;

import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.bean.coordinateBean;
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

public class Coordinate {
    private String address;
    private static Double longitudine;
    private static Double latitudine;

    public Coordinate(String addr, Double lonD, Double latD) {
        this.address=addr;
        this.longitudine=lonD;
        this.latitudine=latD;

    }

    public static Coordinate addressConvert(addressBean addrBean) throws IOException {
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

        Double latD = firstResult.get("lat").getAsDouble();
        Double lonD = firstResult.get("lon").getAsDouble();

        return new Coordinate(address,lonD,latD);
    }
    private static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
    public String getAddress(){return address;}
    public static Double getlat(){return latitudine;}
    public static Double getlon(){return longitudine;}
}

