package com.gyk.muzeyen.gezginapp.activities;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.gyk.muzeyen.gezginapp.R;
public class RegisterActivity extends AppCompatActivity {
    private EditText userEmailEt,userPasswordEt,userPasswordEt2;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private String userEmail,userPassword,userConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userEmailEt=findViewById(R.id.user_email_register_et);
        userPasswordEt=findViewById(R.id.user_password_register_et);
        userPasswordEt2=findViewById(R.id.user_confirm_password);
        registerButton=findViewById(R.id.button_register);
        mAuth = FirebaseAuth.getInstance();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail = userEmailEt.getText().toString().trim();
                userPassword=userPasswordEt.getText().toString().trim();
                userConfirmPassword=userPasswordEt2.getText().toString().trim();
                if(!userEmail.isEmpty()&& !userPassword.isEmpty()&&!userConfirmPassword.isEmpty()){
                    if(userPassword.equals(userConfirmPassword)){
                        register();

                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Parolalar eşleşmedi.Tekrar giriniz",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Lütfen tüm alanları doldurunuz",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void register(){
        mAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    Toast.makeText(RegisterActivity.this,"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                    Intent loginIntent =new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (((FirebaseAuthException)e).getErrorCode().equals("ERROR_WEAK_PASSWORD")){
                    Toast.makeText(RegisterActivity.this,"Zayıf Şifre",Toast.LENGTH_SHORT).show();
                }
                else if (((FirebaseAuthException)e).getErrorCode().equals("ERROR_INVALID_EMAIL")){
                    Toast.makeText(RegisterActivity.this,"Geçersiz Email",Toast.LENGTH_SHORT).show();
                }
                else if (((FirebaseAuthException)e).getErrorCode().equals("ERROR_EMAIL_ALREADY_IN_USE")){
                    Toast.makeText(RegisterActivity.this,"Email adresiniz zaten kayıtlı",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}