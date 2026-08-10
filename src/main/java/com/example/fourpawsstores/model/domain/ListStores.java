package com.example.fourpawsstores.model.domain;

import java.util.List;

public class ListStores {
    private List<Store> list;
    public List<Store> getList() {return list;}
    public void addStore(Store store) {list.add(store);}
}
