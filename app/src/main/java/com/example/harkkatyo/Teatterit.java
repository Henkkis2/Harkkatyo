package com.example.harkkatyo;

public class Teatterit {
    private String id;
    private String nimi;

    public Teatterit(String a, String b) {
        id = a;
        nimi = b;
    }

    public String getTeatteriID () {
        return id;
    }
    public String getTeatteriNimi () {
        return nimi;
    }

}
