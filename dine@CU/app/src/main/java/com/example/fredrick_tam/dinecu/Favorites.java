package com.example.fredrick_tam.dinecu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class Favorites extends AppCompatActivity {

    public FavoritesDBHelper db;
    ListView list;
    String[] nameList;
    Bitmap[] imageList;
    ArrayList<String> menuItemNames = new ArrayList<String>();
    public ArrayList<String> menuItemImage = new ArrayList<String>();
    ArrayList<Bitmap> mapArray = new ArrayList<Bitmap>();
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Favorites");
        setContentView(R.layout.activity_favorites);

        GetXMLTask Favorites_XML = new GetXMLTask();
        Favorites_XML.execute(new String[] { });

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

    public class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;

            db = new FavoritesDBHelper(Favorites.this);

            HashMap<String, ArrayList<String[]>> favHashMap = db.getAllFavorites();

            for(String k: favHashMap.keySet()) {
                for(String[] s: favHashMap.get(k)) {
                    System.out.println("Item: " + s[0] + "\tURL: "+s[1]);
                    menuItemNames.add(s[0]);
                    menuItemImage.add(s[1]);
                }
            }

            for (String url : menuItemImage) {
                map = downloadImage(url);
                mapArray.add(map);
            }
            return map;

        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {

            imageList = mapArray.toArray(new Bitmap[mapArray.size()]);
            nameList = menuItemNames.toArray(new String[menuItemNames.size()]);
            for (Bitmap img : imageList){
                Log.d("bitmap", String.valueOf(img));
            }

            adapter = new CustomListAdapter(Favorites.this, nameList, imageList);
            list=(ListView)findViewById(R.id.list);
            list.setAdapter(adapter);
        }


        // Creates Bitmap from InputStream and returns it
        public Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        public InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            java.net.URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

    }
}
