package com.example.besmart.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.besmart.Classes.FIreStoreNotes;
import com.example.besmart.Classes.FirebaseAuth;
import com.example.besmart.Fragments.Notes_Fragment;
import com.example.besmart.R;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Notes_Adapter extends BaseAdapter {
    ArrayList<HashMap<String,Object>> notes=new ArrayList<HashMap<String,Object>>();
    Context ctx;
    int viewid;
    FirebaseAuth firebaseAuth;
    FIreStoreNotes fIreStoreNotes=new FIreStoreNotes();
    LayoutInflater inflater;
    public Notes_Adapter(ArrayList<HashMap<String,Object>> notes, Context ctx, int viewid){
        this.notes=notes;
        this.ctx=ctx;
        this.viewid=viewid;
        this.inflater=LayoutInflater.from(this.ctx);
        firebaseAuth=new FirebaseAuth(ctx);
    }
    @Override
    public int getCount() {
        return (notes!=null)?notes.size():0;
    }

    @Override
    public Object getItem(int i) {
        return notes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=inflater.inflate(this.viewid,null,false);
        TextView title=v.findViewById(R.id.ti_title);
        TextView date=v.findViewById(R.id.ti_date);
        Log.d("Here",notes.get(i).get("title").toString());
        title.setText(notes.get(i).get("title").toString());
        date.setText(notes.get(i).get("date").toString());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder addbuilder = new android.app.AlertDialog.Builder(ctx);
                View alertview = inflater.inflate(R.layout.setnotes_dialog,null,false);
                addbuilder.setView(alertview);
                AlertDialog dialogue = addbuilder.create();
                dialogue.show();

                EditText title = alertview.findViewById(R.id.title);
                EditText description = alertview.findViewById(R.id.description);
                TextView date=alertview.findViewById(R.id.date);
                Button savenote = alertview.findViewById(R.id.savenote);
                Button delete=alertview.findViewById(R.id.delete_note);
                Button  cancel = alertview.findViewById(R.id.cancel);
                title.setText(notes.get(i).get("title").toString());
                description.setText(notes.get(i).get("description").toString());
                date.setText(notes.get(i).get("date").toString());
                date.setEnabled(false);
                savenote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(title.getText().toString().trim().isEmpty()){
                            title.setError("Title Required");
                        }
                        else if(description.getText().toString().trim().isEmpty()){
                            description.setError("Description Required");
                        }
                        else {
                            fIreStoreNotes.UpdateANote(title.getText().toString().trim(),date.getText().toString().trim(),description.getText().toString().trim(),notes.get(i).get("doc_id").toString(),firebaseAuth.getUser()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ctx, "A Note Utedated", Toast.LENGTH_SHORT).show();
                                    notes.get(i).put("title",title.getText().toString().trim());
                                    notes.get(i).put("description",description.getText().toString().trim());
                                    dialogue.dismiss();
                                }
                            });
                            dialogue.dismiss();
                        }
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fIreStoreNotes.DeleteANote(notes.get(i).get("doc_id").toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ctx, "A Note Deleted", Toast.LENGTH_SHORT).show();
                                dialogue.dismiss();
                            }
                        });
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogue.dismiss();
                    }
                });
            }
            });
        return v;
    }
}
