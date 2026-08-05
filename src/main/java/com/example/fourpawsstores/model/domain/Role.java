package com.example.fourpawsstores.model.domain;

public enum Role {
    NEGOZIANTE(1),

    UTENTE(2);


    private final int id;

    Role(int id) {
        this.id = id;
    }

    public static Role fromInt(int id) {
        for (Role type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
