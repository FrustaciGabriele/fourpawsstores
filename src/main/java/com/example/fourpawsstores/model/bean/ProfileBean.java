package com.example.fourpawsstores.model.bean;

import javax.xml.namespace.QName;

public class ProfileBean {
    private static String usernameB;
    private static String nomeB;
    private static String idCartaB;
    private static int IdStoreB;

    private ProfileBean() {}
    private ProfileBean(String username,String name,String cartaNum) {
        usernameB=username;
        nomeB= name;
        idCartaB= cartaNum;
    }
    public ProfileBean(String username, String name, int id) {
        usernameB=username;
        nomeB= name;
        IdStoreB = id;
    }
    public static void setUsername(String user){usernameB=user;}
    public static void setName(String name){nomeB=name;}
    public static void setCredit(String carta){idCartaB=carta;}
    public static String getUsername(){return usernameB;}
    public static String getNameB(){return nomeB;}

    public static int getStoreId() {return IdStoreB;
    }

    public static void setIdStore(int anInt) { IdStoreB=anInt;
    }
}
