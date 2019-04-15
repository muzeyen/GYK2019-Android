package com.example.muzeyen.gyk_deneme;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button giris;
    private Context bura = this;
    private String kullanici_adi;
    private Intent nextpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //ilk önce bu fonksiyon çalışır
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        giris = findViewById(R.id.button);


        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullanici_adi= username.getText().toString();
                if (username.getText().toString().equalsIgnoreCase("Müzeyyen") && password.getText().toString().equals("123456")) {
                    nextpage = new Intent(bura, ListActivity.class);
                    nextpage.putExtra("hosgeldin_yazısı","Hoşgeldin "+ kullanici_adi);
                    startActivity(nextpage);
                } else
                    Toast.makeText(bura, "Yanlis kullanici adi ve sifre", Toast.LENGTH_SHORT).show();
            }
        });
    }
}