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
    public Coordinate( Double lonD, Double latD) {
        this.longitudine=lonD;
        this.latitudine=latD;

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
    public String getAddress(){return address;}
    public static Double getlat(){return latitudine;}
    public static Double getlon(){return longitudine;}
}

