package com.gyk.muzeyen.gyk_media;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView;
    private Button videoCek;
    private Button videoIzle;
    private  int keyVideo= 30; //aramada skıntı yaşamamak için farklı değerler koyduk
    private Uri videoAdres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView=findViewById(R.id.videoView);
        videoCek= findViewById(R.id.videoCek);
        videoIzle= findViewById(R.id.videoIzle);

        videoCek.setOnClickListener(this); //onClick aşağıdaki method olduğunu söyledik,atadık
        videoIzle.setOnClickListener(this);

        videoIzle.setEnabled(false); //video çekilmediği için şuan butona basmayı engelledik
    }

    @Override
    public void onClick(View v) {
        if(v == videoCek){
            Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);  //fotoğraf çekimine yönlendir dedik
            startActivityForResult(videoIntent,keyVideo);
        }
        else if(v == videoIzle){
            videoView.setVideoURI(videoAdres);
            videoView.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.keyVideo == requestCode && resultCode == RESULT_OK){ //video  çekilip çekilmediğini kontrol ediyoruz
            videoAdres =data.getData();  //video artık hazır
            videoIzle.setEnabled(true); //hazır olduğu için izleme butonunu aktifleştirdik
        }
    }
}
