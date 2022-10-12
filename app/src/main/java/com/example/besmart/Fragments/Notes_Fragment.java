package com.example.besmart.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.besmart.Adapters.Notes_Adapter;
import com.example.besmart.Classes.FIreStoreNotes;
import com.example.besmart.Classes.FirebaseAuth;
import com.example.besmart.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Notes_Fragment extends Fragment {
    FirebaseAuth firebaseAuth=new FirebaseAuth(this.getActivity());
    FIreStoreNotes fIreStoreNotes=new FIreStoreNotes();
    ArrayList<HashMap<String,Object>> ls=new  ArrayList<HashMap<String,Object>>();
    FloatingActionButton bt;
   public Notes_Fragment(){}
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_fragment, container, false);
    }
void read(){
    fIreStoreNotes.GetUserNotes(firebaseAuth.getUser()).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
            for (QueryDocumentSnapshot s : queryDocumentSnapshots) {
                HashMap<String, Object> hs = (HashMap) s.getData();
                ls.add(hs);
            }
            ListView lv=getActivity().findViewById(R.id.notes_list);
            Notes_Adapter na=new Notes_Adapter(ls,getActivity(),R.layout.note_component);
            lv.setAdapter(na);
        }
    });
}
    @Override
    public void onStart() {
        super.onStart();
      // read();
         bt= (FloatingActionButton) this.getActivity().findViewById(R.id.add);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder addbuilder = new AlertDialog.Builder(getActivity());
                View alertview = getLayoutInflater().inflate(R.layout.notedetails_dialog,null,false);
                addbuilder.setView(alertview);
                AlertDialog dialogue = addbuilder.create();

                EditText title = alertview.findViewById(R.id.title);
                EditText description = alertview.findViewById(R.id.description);
                Button addproduct = alertview.findViewById(R.id.addnote);
                Button  cancel = alertview.findViewById(R.id.cancel);

                addproduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(title.getText().toString().trim().isEmpty()){
                            title.setError("Title Required");
                        }
                        else if(description.getText().toString().trim().isEmpty()){
                            description.setError("Description Required");
                        }
                        else {
                            fIreStoreNotes.AddNote(title.getText().toString().trim(),description.getText().toString().trim(),firebaseAuth.getUser()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "A new Note Added", Toast.LENGTH_SHORT).show();
                                }
                            });
                            ls=new ArrayList<HashMap<String,Object>>();
                            read();
                            dialogue.dismiss();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogue.dismiss();
                    }
                });
                dialogue.show();


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ls=new ArrayList<HashMap<String,Object>>();
        read();
        Log.d("Resume","Herer");
    }
}
