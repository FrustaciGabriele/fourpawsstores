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

    public String getAddress(){return address;}
    public static Double getlat(){return latitudine;}
    public static Double getlon(){return longitudine;}
}

