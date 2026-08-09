package com.example.fourpawsstores.model.bean;

public class coordinateBean {
    private String addressB;
    private String longitudine;
    private String latitudine;

    public coordinateBean(String indirizzo, String longitudine, String latitudine) {
        this.addressB = indirizzo;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }


    public void CoordinateBean(String longitudine, String latitudine) {
        this.longitudine = longitudine;
        this.latitudine = latitudine;
    }

    public String getAddress() {
        return addressB;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public String getLatitudine() {
        return latitudine;
    }
}

