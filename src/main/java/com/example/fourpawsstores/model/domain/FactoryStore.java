package com.example.fourpawsstores.model.domain;

public class FactoryStore {
    //in questo caso abbiamo solo un tipo di negozio tuttavia creo comunque una factory per rendere il tutto più facilmente aggiornabile in caso di aggiunte future(es. aggiunta veterinari)
    public static Store CreateStore(){
        return new Store();
    }
}
