package com.example.pepega;

import android.graphics.Bitmap;

public class MyPicture {
    private final String date;
    private final Bitmap gambar;
    public MyPicture(String date, Bitmap gambar) {
        this.date = date;
        this.gambar = gambar;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getPicture() {
        return gambar;
    }
}
