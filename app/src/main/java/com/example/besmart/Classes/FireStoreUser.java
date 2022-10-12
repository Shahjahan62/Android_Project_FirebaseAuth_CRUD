package com.example.besmart.Classes;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.HashMap;

public class FireStoreUser {
   private FirebaseFirestore db;

    public FireStoreUser() {
        db=FirebaseFirestore.getInstance();
    }
    public Task<Void> AddUser(String email, String full_name, String ph_no,String user_id){
        HashMap<String,Object> user=new HashMap<>();
        String doc_id = db.collection("User").document().getId();
        user.put("doc_id",doc_id);
        user.put("email",email);
        user.put("full_name",full_name);
        user.put("ph_no",ph_no);
        user.put("user_id",user_id);
        return db.collection("User").document(doc_id).set(user);
    }
    public Task<QuerySnapshot> GetUser(String user_id){
        return db.collection("User").whereEqualTo("user_id",user_id).get();
    }
    public Task<Void> UpdateUser(String email, String full_name, String ph_no,String user_id, String doc_id){
        HashMap<String,Object> user=new HashMap<>();
        user.put("doc_id",doc_id);
        user.put("email",email);
        user.put("full_name",full_name);
        user.put("ph_no",ph_no);
        user.put("user_id",user_id);
        return db.collection("User").document(doc_id).update(user);
    }
    public Task<DocumentSnapshot> GetById(String doc_id){
        return  db.collection("User").document(doc_id).get();
    }
}
