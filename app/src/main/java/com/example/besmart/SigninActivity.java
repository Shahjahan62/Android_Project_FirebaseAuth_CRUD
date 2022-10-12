package com.example.besmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besmart.Classes.FirebaseAuth;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

public class SigninActivity extends AppCompatActivity {
    FirebaseAuth Auth;
    EditText email;
    EditText psw;
    Button signin_btn;
    TextView signup_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        this.Auth=new FirebaseAuth(SigninActivity.this);
        email=(EditText)findViewById(R.id.email);
        psw=(EditText) findViewById(R.id.psw);
        signin_btn=findViewById(R.id.login);
        signup_btn=findViewById(R.id.signup);
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().isEmpty()){
                    email.setError("Email is required");
                }
                else if(psw.getText().toString().trim().isEmpty()){
                    psw.setError("Password is required");
                }
                else{
                    Auth.Login(email.getText().toString().trim(),psw.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent i = new Intent(SigninActivity.this, DashboardActivity.class);
                            i.putExtra("user",Auth.getUser());
                            SigninActivity.this.startActivity(i);
                            SigninActivity.this.finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SigninActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SigninActivity.this, SignupActivity.class);
                SigninActivity.this.startActivity(i);
                SigninActivity.this.finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Auth.CheckSession()!=null) {
            Intent i=new Intent(this,DashboardActivity.class);
            this.startActivity(i);
            this.finish();
        }
    }
}