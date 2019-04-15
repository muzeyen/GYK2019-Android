package com.gyk.muzeyen.gyk_media;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FotoActivity extends AppCompatActivity {

    private Button foto_button;
    private ImageView resim ;
    private int key = 20; //sayının önemi yok

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        foto_button= findViewById(R.id.fotocek);   //clickListener atama yaptık
        resim= findViewById(R.id.foto);

        foto_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //fotoğraf çekimine yönlendir dedik
                startActivityForResult(fotoIntent,key);   //camerada fotoğraf çektikten sonra geri dönmesini sağlıyor. Key sayesinde tanıyor.


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {  //data almaya giderken kullanıyoruz
        super.onActivityResult(requestCode, resultCode, data);

        if(this.key == requestCode && resultCode == RESULT_OK){ //fotoğraf  çekilip çekilmediğini kontrol ediyoruz
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");  //çekilmiş resmi datada saklıyoruz ve onu bitmapde tutuyoruz
            resim.setImageBitmap(bitmap); //resme atadım
        }

    }
}
