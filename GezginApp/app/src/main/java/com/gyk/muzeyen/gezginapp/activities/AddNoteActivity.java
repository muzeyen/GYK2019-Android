package com.gyk.muzeyen.gezginapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gyk.muzeyen.gezginapp.R;

public class AddNoteActivity extends AppCompatActivity {

    EditText userNote;
    Button ekleButton;
    Button notaDonButton;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        userNote =findViewById(R.id.userNote_edittext);
        ekleButton= findViewById(R.id.notEkle_button);
        notaDonButton= findViewById(R.id.notaDon_button);
        mAuth= FirebaseAuth.getInstance();
        ekleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);   //klavye kapanıyor
            }
        });
        notaDonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed(); //geri butonu tıklanmış gibi hareket et
            }
        });
    }
    private void addNote(){

        FirebaseDatabase database = FirebaseDatabase.getInstance(); //Sitede oluşturduğum dataları getir
        //belirli bir yere eklemek istediğim için bir referans belirliyoruz. Kısaca bir klasör oluşturuyoruz
        //getUid ile kişiye özel olmasını sağladım
        DatabaseReference ref = database.getReference().child(mAuth.getUid()).child("Notlarim"); //Notlarım ismi varsa ona bakacak ve varsa onun altına ekleyecek yoksa yeniden oluşturup ekleyecek
        String noteId = ref.push().getKey();  //notlarim için bir primary numarası belirliyoruz. push ile ekliyoruz keyini noteIdye atayıp daha sona ulaşmasında kolaylık sağlıyoruz
        String gelenNot = userNote.getText().toString(); //Notu atadım gelen nota
        if(gelenNot.length()>0){
            ref.child(noteId).child("Notlarim").setValue(gelenNot);
            Toast.makeText(this,"Not eklendi", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Not ekleme başarısız,Not ekleme alanı boş", Toast.LENGTH_LONG).show();
        }

        userNote.setText("");
    }
}
