package com.example.besmart.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.besmart.Classes.FireStoreUser;
import com.example.besmart.Classes.FirebaseAuth;
import com.example.besmart.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class Profile_Fragment extends Fragment {
    FireStoreUser fireStoreUser=new FireStoreUser();
    FirebaseAuth firebaseAuth=new FirebaseAuth(this.getActivity());
    EditText email,full_name,ph_no;
    Button save_btn;
    String doc_id;
    public Profile_Fragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.profile_fragment,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Context ctx=this.getActivity();
        email=this.getActivity().findViewById(R.id.email);
        full_name=this.getActivity().findViewById(R.id.full_name);
        ph_no=this.getActivity().findViewById(R.id.ph_no);
        save_btn=this.getActivity().findViewById(R.id.save);
        email.setEnabled(false);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(full_name.getText().toString().trim().isEmpty()){
                    full_name.setError("Name can't be empty");
                }
                else if(ph_no.getText().toString().trim().isEmpty()){
                    ph_no.setError("Phone.No is required");
                }
                else{
                    fireStoreUser.UpdateUser(email.getText().toString().trim(),full_name.getText().toString().trim(),ph_no.getText().toString().trim(),firebaseAuth.getUser(),doc_id).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ctx, "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        Task<QuerySnapshot> snap=fireStoreUser.GetUser(firebaseAuth.getUser());
        snap.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot s : queryDocumentSnapshots) {
                    HashMap<String,Object> hs=(HashMap)s.getData();
                    doc_id=hs.get("doc_id").toString();
                    email.setText(hs.get("email").toString());
                    ph_no.setText(hs.get("ph_no").toString());
                    full_name.setText(hs.get("full_name").toString());
                  //  Log.d("DEC",hs.get("full_name").toString());
                    return;

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Context ctx=this.getActivity();
        email=this.getActivity().findViewById(R.id.email);
        full_name=this.getActivity().findViewById(R.id.full_name);
        ph_no=this.getActivity().findViewById(R.id.ph_no);
        save_btn=this.getActivity().findViewById(R.id.save);
        email.setEnabled(false);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(full_name.getText().toString().trim().isEmpty()){
                    full_name.setError("Name can't be empty");
                }
                else if(ph_no.getText().toString().trim().isEmpty()){
                    ph_no.setError("Phone.No is required");
                }
                else{
                    fireStoreUser.UpdateUser(email.getText().toString().trim(),full_name.getText().toString().trim(),ph_no.getText().toString().trim(),firebaseAuth.getUser(),doc_id).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ctx, "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        Task<QuerySnapshot> snap=fireStoreUser.GetUser(firebaseAuth.getUser());
        snap.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot s : queryDocumentSnapshots) {
                    HashMap<String,Object> hs=(HashMap)s.getData();
                    doc_id=hs.get("doc_id").toString();
                    email.setText(hs.get("email").toString());
                    ph_no.setText(hs.get("ph_no").toString());
                    full_name.setText(hs.get("full_name").toString());
                    //  Log.d("DEC",hs.get("full_name").toString());
                    return;

                }
            }
        });
    }
}
