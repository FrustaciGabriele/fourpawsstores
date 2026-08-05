package com.example.fourpawsstores.model.domain;

public class Credentials {
    private static String username;
    private static String password;
    private static Role role;
    public static void setUsername(String user) {username=user;
    }

    public static void setPassword(String pass) {password=pass;
    }

    public static void setRole(Role r) {role=r;
    }

    public static Role getRole() {return role;
    }
    public static String getUsername(){return username;}
    public static String getPassword(){return password;}
}
