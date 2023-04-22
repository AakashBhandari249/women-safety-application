package com.example.womansafety.Models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOUser {

    DatabaseReference databaseReference;

    public DAOUser(){
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        databaseReference =db.getReference(User.class.getSimpleName());
    }

    public Task<Void> add(User u) {
     return   databaseReference.push().setValue(u);
    }

    public Task<Void> update(String key, HashMap<String,Object>hashMap){
        return   databaseReference.child(key).updateChildren(hashMap);
    }


}
