package com.gyk.muzeyen.ulkeler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {

    //private WebView web;
    Intent gelen;
    private ImageView bayrak;
    private TextView isim;
    private  int bayrak_id;
    private  String isim_ulke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        bayrak=findViewById(R.id.bayrak);
        isim=findViewById(R.id.isim);
        gelen= getIntent();   //veriler gelmesini sağladık
        bayrak_id= gelen.getIntExtra("ulke_bayrak",-1);
        isim_ulke=gelen.getStringExtra("ulke_isim");

        bayrak.setImageResource(bayrak_id);   //imageviewe atama yaptık
        isim.setText(isim_ulke);   //textviewe atama yaptık

        //web = findViewById(R.id.web);
        //web.getSettings().setJavaScriptEnabled(true);   //açacağımız sitenin javascript ise desteklemesini istediğimiz için yazdık
        //web.setWebViewClient(new WebViewClient());    //siteyi uygulama içinde açıyoruz
        //web.loadUrl("https://www.haberler.com/");  //açılacak olan websitesi
    }
}
