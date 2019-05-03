package com.gyk.muzeyen.gezginapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.gyk.muzeyen.gezginapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmailEt,userPasswordEt;
    private Button loginBtn,registerBtn;
    private FirebaseAuth mAuth;
    ProgressDialog progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmailEt=findViewById(R.id.user_email_login_et);
        userPasswordEt=findViewById(R.id.user_password_login_et);
        loginBtn=findViewById(R.id.button_login);
        registerBtn=findViewById(R.id.button_go_register);

        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yukleniyorGoster();
                String userEmail =userEmailEt.getText().toString().trim();
                String userPassword= userPasswordEt.getText().toString().trim();

                if(!userEmail.isEmpty() && !userPassword.isEmpty()){
                    login(userEmail,userPassword);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Email veya parola boş olamaz",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void yukleniyorGoster(){
        progress = new ProgressDialog(this);
        progress.setMessage("Giriş yapılıyor..");
        progress.setCancelable(false); //onun dışında herhangi bir yere bastıpında kaybolmamasını istiyorum
        progress.show();
    }

    private void login (String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress.dismiss();
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                    Log.d("Email","Başarılı giriş");
                }else{
                    Log.d("Fail","Girişte Hata");
                }

            }
        });

    }
}
