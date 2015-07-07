package com.kamini.photopattern.front;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kamini.photopattern.BaseActivity;
import com.kamini.photopattern.R;
import com.kamini.photopattern.model.Gallery;
import com.kamini.photopattern.model.SQLiteGalleryHandler;
import com.kamini.photopattern.service.GPSTracker;

import java.util.List;


public class MapsActivity extends BaseActivity implements GoogleMap.OnMarkerClickListener {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;

    SQLiteGalleryHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // recuperation du fragment map
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // recuperation gps
        GPSTracker gps = new GPSTracker(this);

        if(!gps.canGetLocation()){
             gps.showSettingsAlert();
        }

        gps.getLocation();


        googleMap = mapFragment.getMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 120));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(3), 2000, null);

        db = new SQLiteGalleryHandler(this);
        setMarker();
        loadMenu();
    }



    public void setMarker(){
        List<Gallery> gallery =  db.getAll();
        int gallerySize = gallery.size();

        for (int i = 0; i < gallerySize; i++){
            googleMap.setOnMarkerClickListener(this);
            //creation d'un marqueur sur la carte
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(gallery.get(i).getLat(), gallery.get(i).getLng()))
                    .title(String.valueOf(gallery.get(i).getId()))
                    .snippet(gallery.get(i).getNote()));

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(getApplicationContext(), ImgActivity.class);
        Bundle b = new Bundle();
        b.putInt("idImg", Integer.parseInt(marker.getTitle())); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        return true;
    }
}
