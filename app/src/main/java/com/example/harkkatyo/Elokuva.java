package com.example.harkkatyo;

public class Elokuva {
    private String name;
    private String pv;
    private String clockStart;
    private String clockEnd;

    public Elokuva(String a, String b, String c, String d) {
        name = a;
        pv = b;
        clockStart = c;
        clockEnd = d;
    }

    public String getName() {
        return name;
    }
    public String getPV() {
        return pv;
    }
    public String getClockStart() {
        return clockStart;
    }
    public String getClockEnd() {
        return clockEnd;
    }

}
