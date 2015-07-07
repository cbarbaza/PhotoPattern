package com.kamini.photopattern.front;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.kamini.photopattern.BaseActivity;
import com.kamini.photopattern.R;
import com.kamini.photopattern.model.Gallery;
import com.kamini.photopattern.model.SQLiteGalleryHandler;


public class SendEmailActivity extends BaseActivity {

    private Button buttonSend;
    private EditText textTo;
    private EditText textSubject;
    private EditText textMessage;
    private ImageView imgPJ;

    private int idImg;

    SQLiteGalleryHandler db;
    Gallery img = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        Bundle b = getIntent().getExtras();
        idImg = b.getInt("idImg");
        db = new SQLiteGalleryHandler(this);
        img = db.getOne(idImg);

        textTo = (EditText) findViewById(R.id.editTextTo);
//        textTo.setText("barbazaclement@gmail.com"); // TOR TESTING
        textSubject = (EditText) findViewById(R.id.editTextSubject);
        textSubject.setText("Nouvelle photo partagé : " + img.getTitle());
        textMessage = (EditText) findViewById(R.id.editTextMessage);
        textMessage.setText("Nouvelle photo partagé : \n    " + img.getTitle() + "\n\n    " + img.getNote());
        buttonSend = (Button) findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(img);
            }
        });

        imgPJ = (ImageView) findViewById(R.id.imgPJ);
        imgPJ.setImageURI(Uri.parse(img.getPath()));
        imgPJ.invalidate();

        loadMenu();
    }

    public void sendEmail(Gallery img)
    {
        String to = textTo.getText().toString();
        String subject = textSubject.getText().toString();
        String message = textMessage.getText().toString();

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
        Log.d("Image path : ", img.getPath());

        email.putExtra(Intent.EXTRA_STREAM, Uri.parse(img.getPath()));
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choisissez un client de messagerie:"));
    }

}
