package com.example.besmart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besmart.Classes.FireStoreUser;
import com.example.besmart.Classes.FirebaseAuth;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;

public class SignupActivity extends AppCompatActivity {
TextView add_image,login;
ImageView profile;
EditText email,psw,re_psw,full_name,ph_no;
CheckBox agree;
Button register;
FirebaseAuth Auth;
int Cam_Req_code=1;
int Gallery_Req_code=2;
FireStoreUser fireStoreUser=new FireStoreUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Auth=new FirebaseAuth(this);
        add_image=findViewById(R.id.add_image);
        profile=findViewById(R.id.profile);
        email=findViewById(R.id.email);
        psw=findViewById(R.id.psw);
        re_psw=findViewById(R.id.psw);
        full_name=findViewById(R.id.full_name);
        ph_no=findViewById(R.id.ph_no);
//        agree=findViewById(R.id.agree);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        register.setEnabled(false);
//        agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(agree.isChecked()){
//                    register.setEnabled(true);
//                }
//                else{
//                    register.setEnabled(false);
//                }
//            }
//        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().isEmpty()){
                    email.setError("Email is required");
                }
                else if(psw.getText().toString().trim().length()<6){
                    psw.setError("Password must be of at least 6 length");
                }
//                else if(re_psw.getText().toString().trim().isEmpty()){
//                    re_psw.setError("Repeat the Password");
//                }
                else if(full_name.getText().toString().trim().isEmpty()){
                    full_name.setError("Name is Required");
                }
//                else if(!psw.getText().toString().trim().equals(re_psw.getText().toString().trim())){
//                    psw.setError("Password does not match");
//                    re_psw.setError("Password does not match");
//                }
//                else if(ph_no.getText().toString().trim().isEmpty()){
//                    ph_no.setError("Ph_no is required");
//                }
                else{
                    Auth.Signup(email.getText().toString().trim(),psw.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            fireStoreUser.AddUser(email.getText().toString().trim(),full_name.getText().toString().trim(),ph_no.getText().toString().trim(),Auth.getUser()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(SignupActivity.this, "Account has been created", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(SignupActivity.this, SigninActivity.class);
                                    SignupActivity.this.startActivity(i);
                                    SignupActivity.this.finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });;
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignupActivity.this,SigninActivity.class);
                SignupActivity.this.startActivity(i);
                SignupActivity.this.finish();
            }
        });
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage("Select an Image");
        b.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Cam_Req_code);
            }
        });
        b.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_PICK ,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Gallery_Req_code);
            }
        });

        add_image.setOnClickListener(v -> {
            AlertDialog alrt = b.create();
            alrt.show();
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Cam_Req_code && resultCode==RESULT_OK && data!=null){
            Bitmap bm=(Bitmap) data.getExtras().get("data");
            profile.setImageBitmap(bm);
        }
        else if(requestCode==Gallery_Req_code && resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();
            profile.setImageURI(uri);
        }
    }
}