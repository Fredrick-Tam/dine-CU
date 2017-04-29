package com.example.fredrick_tam.dinecu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

@SuppressWarnings("ConstantConditions")
public class Dining_Halls extends AppCompatActivity {

    // implementation of OnClickListener class as MyClick
    public class MyClick implements View.OnClickListener {
        public void onClick(View v) {
            switch(v.getId()){
                // start specified activity based on button pressed
                case R.id.button:
                    Intent intent = new Intent(Dining_Halls.this,Ferris.class);
                    startActivity(intent);
                    break;
                case R.id.button2:
                    Intent intent2 = new Intent(Dining_Halls.this,JohnJay.class);
                    startActivity(intent2);
                    break;
                case R.id.button3:
                    Intent intent3 = new Intent(Dining_Halls.this,JJs.class);
                    startActivity(intent3);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.cooking);
        setContentView(R.layout.activity_dining__halls);

        // buttons at bottom for navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_favorites:
                                Intent intent = new Intent(Dining_Halls.this,Favorites.class);
                                startActivity(intent);
                                break;
                            case R.id.action_menu:
                                Intent intent2 = new Intent(Dining_Halls.this,Dining_Halls.class);
                                startActivity(intent2);
                                break;
                            case R.id.action_invite:
                                Intent intent3 = new Intent(Dining_Halls.this,InviteFriends.class);
                                startActivity(intent3);
                                break;
                        }
                        return true;
                    }
                });

        // linking buttons with MyCLick class
        Button ferris, johnjay,jjs;

        ferris = (Button) findViewById(R.id.button);
        johnjay = (Button) findViewById(R.id.button2);
        jjs = (Button) findViewById(R.id.button3);

        MyClick myClickListener = new Dining_Halls.MyClick();

        ferris.setOnClickListener(myClickListener);
        johnjay.setOnClickListener(myClickListener);
        jjs.setOnClickListener(myClickListener);

    }

}
