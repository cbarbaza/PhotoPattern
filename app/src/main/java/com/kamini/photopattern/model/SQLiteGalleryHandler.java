package com.kamini.photopattern.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kamini on 12/05/15.
 */
public class SQLiteGalleryHandler extends SQLiteOpenHelper {

    private static Context appContext;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GalleryPatternDB";
    private static final String TABLE_NAME = "Gallery";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PATH = "path";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_DADD = "dadd";
    private static final String KEY_NOTE = "note";
//    private static final String[] COLONNES = {KEY_ID, KEY_TITLE,KEY_PATH, KEY_LAT, KEY_LNG, KEY_DADD, KEY_NOTE};


    public SQLiteGalleryHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.appContext = context;
    }

    // creation de la table
    @Override
    public void onCreate(SQLiteDatabase arg0) {

        String CREATION_TABLE_EXEMPLE = "CREATE TABLE Gallery ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title VARCHAR(50), "
                + "path VARCHAR(150), "
                + "lat FLOAT, "
                + "lng FLOAT, "
                + "dadd DATE, "
                + "note TEXT )";

        arg0.execSQL(CREATION_TABLE_EXEMPLE);
    }

    // delete la table pour la re créer si besoins
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(arg0);
    }

    // ajout d'une ligne
    public void addOne(Gallery img) {
        //debug
        Log.d("IMG addOne: ", img.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, img.getTitle());
        values.put(KEY_PATH, img.getPath());
        values.put(KEY_LAT, img.getLat());
        values.put(KEY_LNG, img.getLng());
        values.put(KEY_DADD, img.getDadd());
        values.put(KEY_NOTE, img.getNote());
        // insertion
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // supprime une ligne by id
    public void deleteOne(Gallery gallery) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(gallery.getId())});
        db.close();
    }

    // recuperation de tout les image de la galerie
    public List<Gallery> getAll(){
        List<Gallery> gallery = new LinkedList<Gallery>();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + KEY_DADD + " DESC ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        Gallery img = null;
        if (cursor.moveToFirst()) {
            do{
                img = new Gallery();
                img.setId(Integer.parseInt(cursor.getString(0)));
                img.setTitle(String.valueOf(cursor.getString(1)));
                img.setPath(String.valueOf(cursor.getString(2)));
                img.setLat(Float.parseFloat(cursor.getString(3)));
                img.setLng(Float.parseFloat(cursor.getString(4)));
                img.setDadd(String.valueOf(cursor.getString(5)));
                img.setNote(String.valueOf(cursor.getString(6)));
                gallery.add(img);
                Log.d("IMAGE get all: ", img.toString());
            } while (cursor.moveToNext());
        }
        return gallery;

    }

    // recuperation de la derniere image
    public Gallery getOne(int id){
        Gallery img = new Gallery();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id;
        Log.d("DEBUG SQL = ", sql);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            img = new Gallery();
            img.setId(Integer.parseInt(cursor.getString(0)));
            img.setTitle(String.valueOf(cursor.getString(1)));
            img.setPath(String.valueOf(cursor.getString(2)));
            img.setLat(Float.parseFloat(cursor.getString(3)));
            img.setLng(Float.parseFloat(cursor.getString(4)));
            img.setDadd(String.valueOf(cursor.getString(5)));
            img.setNote(String.valueOf(cursor.getString(6)));
            Log.d("IMAGE getone : ", String.valueOf(img.getId()) +" : "+img.toString());
        }
        return img;
    }

    // recuperation de la derniere image
    public Gallery getLast(){
        Gallery img = new Gallery();
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do{
                img = new Gallery();
                img.setId(Integer.parseInt(cursor.getString(0)));
                img.setTitle(String.valueOf(cursor.getString(1)));
                img.setPath(String.valueOf(cursor.getString(2)));
                img.setLat(Float.parseFloat(cursor.getString(3)));
                img.setLng(Float.parseFloat(cursor.getString(4)));
                img.setDadd(String.valueOf(cursor.getString(5)));
                img.setNote(String.valueOf(cursor.getString(6)));
                Log.d("IMAGE getlast : ", img.toString());
            } while (cursor.moveToNext());
        }
        return img;
    }

    public Boolean updateOneInfo(String title, String note, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String strFilter = "id=" + id;
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_NOTE, note);
        try {
            db.update(TABLE_NAME, args, strFilter, null);
        }
        catch (Exception e){
            Toast toast = Toast.makeText(this.appContext, String.valueOf(e), Toast.LENGTH_SHORT);
            toast.show();
            Log.d("update img", String.valueOf(e));
            return false;
        }

        return true;
    }
}
