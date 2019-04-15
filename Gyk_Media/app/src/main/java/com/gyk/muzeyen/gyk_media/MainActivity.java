package com.gyk.muzeyen.gyk_media;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button fotograf;
    private Button video;
    private Button ses;
    private Button harita;
    private Button arama;
    private Button sms;
    private Context context= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fotograf = findViewById(R.id.fotograf);
        video = findViewById(R.id.video);
        ses= findViewById(R.id.ses);
        harita = findViewById(R.id.harita);
        arama = findViewById(R.id.arama);
        sms = findViewById(R.id.sms);

        fotograf.setOnClickListener(this);
        video.setOnClickListener(this);
        harita.setOnClickListener(this);
        ses.setOnClickListener(this);
        sms.setOnClickListener(this);
        arama.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v==fotograf){
            Intent fotoIntent = new Intent(context,FotoActivity.class);
            startActivity(fotoIntent);
        }
        else if(v==video){
            Intent videoIntent = new Intent(context,VideoActivity.class);
            startActivity(videoIntent);
        }
        else if(v==harita){
            Intent mapIntent = new Intent(context,MapActivity.class);
            startActivity(mapIntent);
        }
        else if(v==ses){
            Intent sesIntent = new Intent(context,AudioActivity.class);
            startActivity(sesIntent);
        }
        else if(v==sms){
            Intent smsIntent = new Intent(context,SmsActivity.class);
            startActivity(smsIntent);
        }
        else if(v==arama){
            Intent aramaIntent = new Intent(context,CallActivity.class);
            startActivity(aramaIntent);
        }
    }
}
