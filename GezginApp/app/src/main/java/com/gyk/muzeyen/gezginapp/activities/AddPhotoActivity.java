package com.gyk.muzeyen.gezginapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gyk.muzeyen.gezginapp.R;
import com.squareup.picasso.Picasso;

public class AddPhotoActivity extends AppCompatActivity {

    ImageView userPhoto;
    Button fotoSec;
    Button fotoKaydet;
    FirebaseStorage fs;
    ProgressDialog progress;
    FirebaseAuth mAuth;
    private final int IMAGE_REQUEST=10; //sayının önemi yok
    Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        mAuth = FirebaseAuth.getInstance();
        fs= FirebaseStorage.getInstance();

        userPhoto= findViewById(R.id.user_saved_photo);
        fotoSec=findViewById(R.id.select_photo_button);
        fotoKaydet=findViewById(R.id.save_photo_button);

        fotoSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoSec();
            }
        });

        fotoKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoKaydet();
            }
        });
    }

    private void fotoSec(){
        Intent intent = new Intent();
        intent.setType("image/*"); //tüm yıldızlara ulaşma
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Resim seçiniz.."),IMAGE_REQUEST);
    }
    private void yukleniyorGoster(){
        progress = new ProgressDialog(this);
        progress.setMessage("Fotoğraf yükleniyor..");
        progress.setCancelable(false); //onun dışında herhangi bir yere bastıpında kaybolmamasını istiyorum
        progress.show();
    }

    private void fotoKaydet() {
        if(filePath !=null){
            yukleniyorGoster();

            StorageReference sr= fs.getReference();
            sr.child(mAuth.getUid()).child("Foto").putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progress.dismiss();
                    Toast.makeText(AddPhotoActivity.this,"Yükleme başarılı..",Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progress.dismiss();
                    Toast.makeText(AddPhotoActivity.this,"Yükleme başarısız..",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Picasso.with(AddPhotoActivity.this).load(filePath).fit().centerCrop().into(userPhoto);   //seçtiği fotoğrafi userphotoya göm
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
