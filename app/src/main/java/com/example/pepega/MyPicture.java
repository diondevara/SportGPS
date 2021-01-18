package com.example.pepega;

public class MyPicture {
    private final String date;
    private final String gambar;
    public MyPicture(String date, String gambar) {
        this.date = date;
        this.gambar = gambar;
    }

    public String getDate() {
        return date;
    }

    public String getPicture() {
        return gambar;
    }
}
