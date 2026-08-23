package com.example.fourpawsstores.model.domain;

public class Profile {
    private static String username;
    private static String nome;
    private static String indirizzo;
    private static String idCarta;
    private static int IdStore;
    private static int valutazione;
    private Profile() {}
    public static void setUsername(String user){username=user;}
    public static void setName(String name){nome=name;}
    public static void setCredit(String carta){idCarta=carta;}
    public static String getUsername(){return username;}

}
