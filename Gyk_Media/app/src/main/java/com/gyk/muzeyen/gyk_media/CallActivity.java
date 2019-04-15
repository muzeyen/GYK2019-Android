package com.gyk.muzeyen.gyk_media;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CallActivity extends AppCompatActivity {

    private Button aramaButton;
    private EditText telefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        telefon=findViewById(R.id.phone);
        aramaButton=findViewById(R.id.araButton);

        aramaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = telefon.getText().toString();
                ara(tel);

            }
        });
    }
    public  void ara(String telefon){
        Intent intent = new Intent(Intent.ACTION_DIAL);  //telefon aramada kullanılan ACTION_DIAL

        intent.setData(Uri.parse("tel:"+telefon));
        startActivity(intent);


    }
}
