package com.example.fredrick_tam.dinecu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class FavoritesDBHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Favorites.db";
    private static final String TABLE_NAME = "favorites";
    private static final String DINING_HALL_NAME = "hall";
    private static final String MEAL_ITEM_NAME = "item";
    private static final String FAVORITE_NAME = "favorite";
    private static final String IMAGE_URL_NAME = "imageurl";
    private static final String ID_NAME = "id";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + ID_NAME +
            " INTEGER PRIMARY KEY, " + MEAL_ITEM_NAME + " TEXT, " + DINING_HALL_NAME+ " TEXT, " +
            IMAGE_URL_NAME + " TEXT, " + FAVORITE_NAME + " INTEGER, " +
            "UNIQUE(" + DINING_HALL_NAME + "," + MEAL_ITEM_NAME + "))";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public FavoritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        System.out.println("Database created, query\n: " + SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean isFavorite(String item, String hall) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor checkIfExist = db.rawQuery("SELECT * FROM "+ TABLE_NAME + " WHERE " + MEAL_ITEM_NAME
                + " = \"" + item + "\" AND " + DINING_HALL_NAME +  " = \"" + hall + "\" AND " +
                FAVORITE_NAME + " = 1", null);

        if(checkIfExist.moveToFirst()) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public int toggleItem(String item, String hall) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + MEAL_ITEM_NAME + " = \"" + item
                + "\" AND " + DINING_HALL_NAME +  " = \"" + hall + "\"";
        Cursor checkIfExist = db.rawQuery(query, null);
        //System.out.println(item + " attempted to toggle for hall " + hall + " with query: " + query);

        if(checkIfExist.moveToFirst()) {
            if(Integer.parseInt(checkIfExist.getString(4)) == 1) {
                System.out.println(item + " removed from favorites");
                db.execSQL("UPDATE " + TABLE_NAME + " SET " + FAVORITE_NAME + " = 0 WHERE " +
                        MEAL_ITEM_NAME + " = \"" + item + "\" AND " + DINING_HALL_NAME +  " = \""
                        + hall + "\"");
                return 0;
            }
            else {
                System.out.println(item + " added to favorites");
                db.execSQL("UPDATE " + TABLE_NAME + " SET " + FAVORITE_NAME + " = 1 WHERE " +
                        MEAL_ITEM_NAME + " = \"" + item + "\" AND " + DINING_HALL_NAME +  " = \""
                        + hall + "\"");
                return 1;
            }
        }
        checkIfExist.close();
        db.close();
        return 0;
    }

    public void addItem(String item, String hall, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE " + MEAL_ITEM_NAME + " = \"" + item
                + "\" AND " + DINING_HALL_NAME +  " = \"" + hall + "\"";
        Cursor checkIfExist = db.rawQuery(query, null);

        if(!checkIfExist.moveToFirst()) {
            //System.out.println(item + " added for hall " + hall + " with query: " + query);
            ContentValues values = new ContentValues();
            values.put(DINING_HALL_NAME, hall);
            values.put(MEAL_ITEM_NAME, item);
            values.put(IMAGE_URL_NAME, url);
            values.put(FAVORITE_NAME, 0);
            db.insert(TABLE_NAME, null, values);
        }

        checkIfExist.close();
        db.close();
    }

    public HashMap<String, ArrayList<String[]>> getAllFavorites() {
        HashMap<String, ArrayList<String[]>> favs = new HashMap<String, ArrayList<String[]>>(); //key = dining hall
        String sql_select = "SELECT * FROM " + TABLE_NAME + " WHERE " + FAVORITE_NAME + " = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_select, null);
        String tempItem, tempHall, tempURL;

        if(cursor.moveToFirst()) {
            while(cursor.isAfterLast() == false) {
                tempItem = cursor.getString(1);
                tempHall = cursor.getString(2);
                tempURL = cursor.getString(3);

                if(!favs.containsKey(tempHall)) {
                    favs.put(tempHall, new ArrayList<String[]>());
                }
                favs.get(tempHall).add(new String[]{tempItem, tempURL});

                cursor.moveToNext();
            }
        }

        db.close();
        return favs;
    }
}
