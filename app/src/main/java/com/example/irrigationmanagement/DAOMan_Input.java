package com.example.irrigationmanagement;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOMan_Input {
    private final DatabaseReference databseReference;
    public DAOMan_Input()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databseReference=db.getReference(motor.class.getSimpleName());
    }
    public Task<Void> update(String key, HashMap<String,Object> hashMap)
    {
        return databseReference.child(key).updateChildren(hashMap);
    }
}
