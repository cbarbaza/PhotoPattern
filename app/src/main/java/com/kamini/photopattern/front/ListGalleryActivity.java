package com.kamini.photopattern.front;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kamini.photopattern.BaseActivity;
import com.kamini.photopattern.R;
import com.kamini.photopattern.model.Gallery;
import com.kamini.photopattern.model.SQLiteGalleryHandler;

import java.util.ArrayList;
import java.util.List;


public class ListGalleryActivity extends BaseActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gallery);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
                Gallery img = (Gallery) parent.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), ImgActivity.class);
                Bundle b = new Bundle();
                b.putInt("idImg", img.getId()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });
        loadMenu();
    }

    // Prepare some dummy data for gridview
    private ArrayList<Gallery> getData() {
        final ArrayList<Gallery> imageItems = new ArrayList<>();
        // inertion des données
        SQLiteGalleryHandler db = new SQLiteGalleryHandler(this);
        List<Gallery> gallery =  db.getAll();

        for (int i = 0; i < gallery.size(); i++) {
                imageItems.add(gallery.get(i));
        }

        return imageItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
