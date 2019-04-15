package com.example.muzeyen.gyk_deneme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    private ListView liste;
    private Intent gelen;
    private ArrayAdapter adapter;
    private TextView hosgeldin;
    private String isim_hosgeldin;
    private Context bura = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        liste = findViewById(R.id.liste);
        hosgeldin=findViewById(R.id.hosgeldin);
        gelen=getIntent();
        isim_hosgeldin= gelen.getStringExtra("hosgeldin_yazısı");
        hosgeldin.setText(isim_hosgeldin);
        final String[] ulkeler = {"Türkiye", "Almanya", "Fransa", "İngiltere", "ABD", "Yunanistan", "Fransa", "Hollanda", "Belçika", "Macaristan", "İsviçre", "ABD", "Yunanistan", "Fransa", "Hollanda", "Belçika", "Macaristan", "İsviçre", "ABD", "Yunanistan", "Fransa", "Hollanda", "Belçika", "Macaristan", "İsviçre"};

        adapter = new ArrayAdapter(bura, android.R.layout.simple_list_item_1, ulkeler);  //bağlama işlemi yaptık burada
        liste.setAdapter(adapter);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(bura);
                alertDialog.setMessage(ulkeler[position]);
                alertDialog.setTitle("GYK-2019");
                alertDialog.setNegativeButton("Kapatma", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(bura, "Kapatma tıklandı", Toast.LENGTH_SHORT).show();
                    }
        });
                alertDialog.setNeutralButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }
}
