package com.example.fredrick_tam.dinecu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Opening extends AppCompatActivity {

    /** Duration of wait 2 secsonds **/
    private final int OPENING_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Opening.this,Dining_Halls.class);
                Opening.this.startActivity(mainIntent);
                Opening.this.finish();

            }
        }, OPENING_DISPLAY_LENGTH);


    }

}
