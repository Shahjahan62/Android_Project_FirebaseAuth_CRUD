package com.example.besmart.Classes;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;

public class FIreStoreNotes {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    public Task<Void> AddNote(String title, String description, String user_id){
        HashMap<String,Object> note=new HashMap<>();
        String doc_id = db.collection("Note").document().getId();
        note.put("doc_id",doc_id);
        note.put("title",title);
        note.put("date", new Date().toString());
        note.put("description",description);
        note.put("user_id",user_id);
        return db.collection("Note").document(doc_id).set(note);
    }
    public Task<QuerySnapshot> GetUserNotes(String user_id){
        return db.collection("Note").whereEqualTo("user_id",user_id).get();
    }
    public Task<Void>  DeleteANote(String doc_id){
        return db.collection("Note").document(doc_id).delete();
    }
    public Task<DocumentSnapshot> GetNoteById(String doc_id){
        return db.collection("Note").document(doc_id).get();
    }
    public Task<Void> UpdateANote(String title, String date, String description, String doc_id, String user_id){
        HashMap<String,Object> note=new HashMap<>();
        note.put("doc_id",doc_id);
        note.put("title",title);
        note.put("date", date);
        note.put("description",description);
        note.put("user_id",user_id);
        return db.collection("Note").document(doc_id).update(note);
    }
}
