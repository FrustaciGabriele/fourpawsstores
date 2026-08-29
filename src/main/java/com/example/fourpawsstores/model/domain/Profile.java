package com.example.fourpawsstores.model.domain;

public class Profile {
    private static String username;
    private static String nome;
    private static String idCarta;
    private static int IdStore;

    private Profile() {}
    public static void setUsername(String user){username=user;}
    public static void setName(String name){nome=name;}
    public static void setCredit(String carta){idCarta=carta;}
    public static String getUsername(){return username;}

    public static int getStoreId() {return IdStore;
    }

    public static void setIdStore(int anInt) { IdStore=anInt;
    }

    public static String getName() { return nome;
    }

    public static String getCardNum() {return idCarta;
    }
}
