package com.kamini.photopattern.front;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamini.photopattern.BaseActivity;
import com.kamini.photopattern.R;
import com.kamini.photopattern.model.Gallery;
import com.kamini.photopattern.model.SQLiteGalleryHandler;


public class ImgActivity extends BaseActivity {
    SQLiteGalleryHandler db;
    Gallery img = null;

    private ImageView imgview;
    private EditText title;
    private EditText note;
    private TextView infoImg;
    private int idImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);

        Bundle b = getIntent().getExtras();
        idImg = b.getInt("idImg");

        db = new SQLiteGalleryHandler(this);

        if (idImg == -1){
            img = db.getLast();
        }
        else{
            img = db.getOne(idImg);
        }

        imgview = (ImageView) findViewById(R.id.img);
        imgview.setImageURI(Uri.parse(img.getPath()));
        imgview.invalidate();

        infoImg = (TextView) findViewById(R.id.infoImg);
        infoImg.setText(img.getDadd() + " Latitude : " + String.valueOf(img.getLat()) + " Longitude : " + String.valueOf(img.getLng()));

        title = (EditText) findViewById(R.id.title);
        title.setText(img.getTitle());

        note = (EditText) findViewById(R.id.note);
        note.setText(img.getNote());

        final Button button = (Button) findViewById(R.id.saveBT);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (db.updateOneInfo(String.valueOf(title.getText()), String.valueOf(note.getText()), img.getId())) {
                    startActivity(new Intent(getApplicationContext(), ListGalleryActivity.class));
                }
            }
        });

        final ImageView buttonSend = (ImageView) findViewById(R.id.btSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SendEmailActivity.class);
                Bundle b = new Bundle();
                b.putInt("idImg", img.getId()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        });

        loadMenu();
    }
}
