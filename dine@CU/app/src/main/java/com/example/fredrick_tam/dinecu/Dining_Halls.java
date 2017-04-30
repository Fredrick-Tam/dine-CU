package com.example.fredrick_tam.dinecu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_information:
                Intent intent4 = new Intent(Dining_Halls.this,EnergyUsage.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        Button ferris, johnjay,jjs, energy;

        ferris = (Button) findViewById(R.id.button);
        johnjay = (Button) findViewById(R.id.button2);
        jjs = (Button) findViewById(R.id.button3);
        MyClick myClickListener = new Dining_Halls.MyClick();

        ferris.setOnClickListener(myClickListener);
        johnjay.setOnClickListener(myClickListener);
        jjs.setOnClickListener(myClickListener);


    }

}
