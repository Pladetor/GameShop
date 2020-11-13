package com.example.gameshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ShopActivity extends AppCompatActivity {

    public static String[] platforms = {"PS, XBOX, PC"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }
}