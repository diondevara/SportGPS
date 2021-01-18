package com.example.pepega;

public class MyKomen {
    private final String date;
    private final String komentar;
    public MyKomen(String date, String komentar) {
        this.date = date;
        this.komentar = komentar;
    }

    public String getDate() {
        return date;
    }

    public String getKomentar() {
        return komentar;
    }
}
