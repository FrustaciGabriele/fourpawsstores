package com.example.fourpawsstores.model.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListStoresBean {
    private List<StoreBeans> Liststores;
    private String addressBean;
    private Double latB;
    private Double lonB;

    public ListStoresBean(String addressB, Double latitudineB, Double longitudineB) {
        this.Liststores=new ArrayList<>();
        this.latB=latitudineB;
        this.lonB=longitudineB;
        this.addressBean=addressB;
    }
    public void addStore(StoreBeans storeB) {
        Liststores.add(storeB);
    }
    public List<StoreBeans> getList(){return Liststores;}
    public String getAddressBean(){return addressBean;}
    public Double getLatB(){return latB;}
    public Double getLonB(){return lonB;}

    public StoreBeans getById(int id) {
        for(StoreBeans s: Liststores){
            if(s.getid()==id){
                return s;
            }
        }
        return null;
    }
}
