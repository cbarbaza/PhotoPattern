package com.kamini.photopattern;


/**
 * Created by kamini on 07/06/15.
 */

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.kamini.photopattern.front.ImgActivity;
import com.kamini.photopattern.front.ListGalleryActivity;
import com.kamini.photopattern.front.MapsActivity;
import com.kamini.photopattern.model.Gallery;
import com.kamini.photopattern.model.SQLiteGalleryHandler;
import com.kamini.photopattern.service.GPSTracker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseActivity extends FragmentActivity{

    private ImageView imagePhoto;
    private ImageView imageList;
    private ImageView imageMaps;

    private Uri imgUri;
    final int REQUEST_IMAGE_CAPTURE = 1;
    SQLiteGalleryHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadMenu(){
        Log.d("1", "LOAD MENU");
        db = new SQLiteGalleryHandler(this);
        // Gestion des images du menu
        imagePhoto = (ImageView) findViewById(R.id.btPhoto);
        imagePhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        imagePhoto.setColorFilter(Color.parseColor("#5F2444"));

                        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(photoFile));
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        imagePhoto.clearColorFilter();
                        break;
                    }
                }
                return true;
            }
        });

        imageList = (ImageView) findViewById(R.id.btList);
        imageList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        imageList.setColorFilter(Color.parseColor("#5F2444"));
                        startActivity(new Intent(getApplicationContext(), ListGalleryActivity.class));
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        imageList.clearColorFilter();
                        break;
                    }
                }
                return true;
            }
        });

        imageMaps = (ImageView) findViewById(R.id.btMaps);
        imageMaps.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        imageMaps.setColorFilter(Color.parseColor("#5F2444"));
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        imageMaps.clearColorFilter();
                        break;
                    }
                }
                return true;
            }
        });
    }

    // callback de l'intent camera
    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data){
        super.onActivityResult(reqCode, resCode, data);
        if(resCode == -1 && reqCode == REQUEST_IMAGE_CAPTURE) {
            imgUri=Uri.parse(mCurrentPhotoPath);

            // recuperation gps
            GPSTracker gps = new GPSTracker(this);

            // creation date format SQL
            String _date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            // inertion des données
            db = new SQLiteGalleryHandler(this);
            Gallery img =  new Gallery(1, "", String.valueOf(imgUri), (float)gps.getLatitude(), (float)gps.getLongitude(),_date, "");
            db.addOne(img);

            Bundle b = new Bundle();
            Intent intent = new Intent(getApplicationContext(), ImgActivity.class);
            intent.putExtra("idImg", -1); //Put your id to your next Intent
            startActivity(intent);
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
}
