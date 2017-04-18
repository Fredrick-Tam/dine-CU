package com.example.fredrick_tam.dinecu;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Dining_Halls extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining__halls);
        //noinspection deprecation
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dining)));

        // making bottom bar




    }
}
