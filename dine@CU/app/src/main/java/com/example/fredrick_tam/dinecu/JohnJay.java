package com.example.fredrick_tam.dinecu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class JohnJay extends AppCompatActivity {

    public Document htmlDocument;
    public String htmlPageUrl = "http://dining.columbia.edu/?quicktabs_homepage_menus_quicktabs=2#quicktabs-homepage_menus_quicktabs";
    public ArrayList<String> menuItemNames = new ArrayList<String>();
    public ArrayList<String> menuItemImage = new ArrayList<String>();
    public AlertDialog alertDialog;
    public String URL;
    public ListView list;
    public String[] nameList;
    public Bitmap[] imageList;
    public CustomListAdapter adapter;
    public FavoritesDBHelper db;
    public ArrayList<Bitmap> mapArray = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.John_Jay);
        db = new FavoritesDBHelper(this);
        setContentView(R.layout.activity_john_jay);

        GetXMLTask JohnJay_XML = new GetXMLTask();
        JohnJay_XML.execute(new String[] { URL });

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
            try {
                htmlDocument = Jsoup.connect(htmlPageUrl).get();
                Elements menu_items = htmlDocument.getElementsByClass("views-field-field-meal-images-fid");
                if (menu_items.size() > 0){


                    for (Element menu_item : menu_items) {
                        Element link = menu_item.select("a").first();
                        if (link != null){
                            menuItemNames.add(link.attr("title"));
                            menuItemImage.add(link.attr("href"));
                            db.addItem(link.attr("title"), "John Jay", link.attr("href"));
                        }
                    }
                } else{
                    menuItemNames.add("This dining hall is currently closed.");
                    menuItemImage.add("https://www.jewishchattanooga.com/wp-content/uploads/Sorry-were-closed-sign.jpg");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap map = null;
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

            adapter=new CustomListAdapter(JohnJay.this, nameList, imageList);
            list=(ListView)findViewById(R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //id is position in array of item clicked
                    String favName = adapter.getItemName(position);
                    int status = db.toggleItem(favName, "John Jay");
                    String verb = "";
                    if(status == 0)
                        verb = " removed from ";
                    else
                        verb = " added to ";

                    alertDialog = new AlertDialog.Builder(JohnJay.this).create();
                    alertDialog.setMessage(favName + verb + " favorites");
                    alertDialog.show();   //

                    new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            alertDialog.hide();
                        }
                    }.start();
                }
            });
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
