package com.gyk.muzeyen.ulkeler;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView customList;
    private Context context= this;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customList = findViewById(R.id.custom_list);  //listview atadık
        final ArrayList<Ulke> ulkeList = new ArrayList<>(); //arraylistte istediğin boyutta oluşturabiliyorsun
        ulkeList.add(new Ulke(R.drawable.tr,"Türkiye","+90"));
        ulkeList.add(new Ulke(R.drawable.fr,"Fransa","+33"));
        ulkeList.add(new Ulke(R.drawable.al,"Almanya","+49"));
        ulkeList.add(new Ulke(R.drawable.urg,"Uruguay","+598"));

        CustomAdaptor adaptor = new CustomAdaptor(context,ulkeList);   //oluşturduğumuz kendi adaptoru çağırarak bağlama yaptık
        customList.setAdapter(adaptor);   //adaptorü listviewe atama yaptık

        customList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    intent = new Intent(context,WebActivity.class);
                    //putextra methodu ile intente veri ekledik daha sonra getintent ile çekeceğiz
                    intent.putExtra("ulke_bayrak",ulkeList.get(position).bayrak); //string isimleriyle çekme işlemi yapacağız ileride
                    intent.putExtra("ulke_isim",ulkeList.get(position).isim);
                    startActivity(intent);

            }
        });

    }
}
