package com.example.besmart.Classes;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.besmart.DashboardActivity;
import com.example.besmart.SigninActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuth {
    private com.google.firebase.auth.FirebaseAuth mAuth;
    private Context ctx;
    public FirebaseAuth(Context ctx){
        this.mAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
        this.ctx = ctx;
    }

    public Task<AuthResult> Login(String email, String password){
       return this.mAuth.signInWithEmailAndPassword(email,password);
    }
    public Task<AuthResult> Signup(String email,String password){
        return this.mAuth.createUserWithEmailAndPassword(email,password);
    }
    public FirebaseUser CheckSession(){
        if(this.mAuth.getCurrentUser() != null){
            Intent i = new Intent(ctx, DashboardActivity.class);
            ctx.startActivity(i);

        }
        return this.mAuth.getCurrentUser();
    }
    public String getUser(){
        return this.mAuth.getUid();
    }
    public void EndSession(){
        this.mAuth.signOut();
    }
}
