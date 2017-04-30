package com.example.fredrick_tam.dinecu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Ferris extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.Ferris);
        setContentView(R.layout.activity_ferris);
    }
}
