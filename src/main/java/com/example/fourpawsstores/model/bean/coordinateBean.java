package com.example.fourpawsstores.model.bean;

public class coordinateBean {
    private String addressB;
    private Double longitudine;
    private Double latitudine;


    public coordinateBean(String address, Double lon, Double lat) {
        this.addressB=address;
        this.longitudine=lon;
        this.latitudine=lat;
    }

    public void CoordinateBean(Double longitudine, Double latitudine) {
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public String getAddressB() {
        return addressB;
    }

    public Double getLongitudineB() {
        return longitudine;
    }

    public Double getLatitudineB() {
        return latitudine;
    }


}

