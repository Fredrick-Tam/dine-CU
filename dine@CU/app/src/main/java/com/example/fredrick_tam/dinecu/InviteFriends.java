package com.example.fredrick_tam.dinecu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class InviteFriends extends AppCompatActivity {

    // implementation of OnClickListener class as MyClick
    private class MyClick implements View.OnClickListener {
        public void onClick(View v) {
            switch(v.getId()){
                // start specified activity based on button pressed
                case R.id.button4:
                    //Select spinner object
                    Spinner halls = (Spinner) findViewById(R.id.spinner);

                    // time
                    EditText time  = (EditText) findViewById(R.id.time);

                    //Grab object spinner is on
                    String place = String.valueOf(halls.getSelectedItem());

                    // Get time entered by user
                    String enteredTime = time.getText().toString();

                    // Get meeting message entered by user
                    String meetingMessage = "";

                    if (enteredTime.equals("")) {
                        enteredTime = "2:00pm";
                    }

                    if (meetingMessage.equals("") || meetingMessage.equals("Your message here")) {
                        meetingMessage = "Hey guys, want to grab food at " + place + " at " +
                                enteredTime + "?";
                    }

                    System.out.println(meetingMessage);
                    Uri uri = Uri.parse("smsto:");
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", meetingMessage);
                    startActivity(intent);
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.invite);
        setContentView(R.layout.activity_invite_friends);

        // invite button
        Button invite = (Button)findViewById(R.id.button4);

        // activates sending of invitation message
        InviteFriends.MyClick myClickListener = new InviteFriends.MyClick();
        invite.setOnClickListener(myClickListener);


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
                                Intent intent = new Intent(InviteFriends.this,Favorites.class);
                                startActivity(intent);
                                break;
                            case R.id.action_menu:
                                Intent intent2 = new Intent(InviteFriends.this,Dining_Halls.class);
                                startActivity(intent2);
                                break;
                            case R.id.action_invite:
                                Intent intent3 = new Intent(InviteFriends.this,InviteFriends.class);
                                startActivity(intent3);
                                break;
                        }
                        return true;
                    }
                });
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
                Intent intent4 = new Intent(this,EnergyUsage.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
