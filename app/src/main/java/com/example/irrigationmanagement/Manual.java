package com.example.irrigationmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Manual extends AppCompatActivity {

    TextView mhv;
    TextView mtfv;
    TextView mtcv;
    TextView mmv;
    TextView misv;

    DatabaseReference ref, ref1,ref2;
    FirebaseDatabase mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        ToggleButton on_of_value = findViewById(R.id.on_off);

        DAOmotor dao =new DAOmotor();

        mhv = findViewById(R.id.manhumdivalue);
        mtfv = findViewById(R.id.mantempfahrenvalue);
        mtcv = findViewById(R.id.mantempcelvalue);
        mmv = findViewById(R.id.manmoisturevalue);
        misv = findViewById(R.id.manirrigationstatusvalue);

        mdatabase=FirebaseDatabase.getInstance();

        ref=mdatabase.getReference().child("DHT11");
        ref1=mdatabase.getReference().child("Motor");
        ref2=mdatabase.getReference().child("Soil");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String humi = snapshot.child("Humidity").getValue(String.class);
                String tempfrn = snapshot.child("TempFahren").getValue(String.class);
                String tempcel = snapshot.child("Temperature").getValue(String.class);

                mhv.setText(humi);
                mtfv.setText(tempfrn);
                mtcv.setText(tempcel);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Manual.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String irristat = snapshot.getValue(String.class);

                misv.setText(irristat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Manual.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mois = snapshot.child("Moisture").getValue(String.class);
                mmv.setText(mois);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Manual.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        on_of_value.setOnCheckedChangeListener((compoundButton, b) -> {
            if(on_of_value.isChecked())
            {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Operate Value","1");
                dao.update("Operator",hashMap).addOnSuccessListener(suc->
                        Toast.makeText(Manual.this, "Motor is On", Toast.LENGTH_SHORT).show()).addOnFailureListener(er->
                        Toast.makeText(Manual.this, ""+er.getMessage(), Toast.LENGTH_SHORT).show());
            }
            else
            {
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Operate Value","0");
                dao.update("Operator",hashMap).addOnSuccessListener(suc->
                        Toast.makeText(Manual.this, "Motor is Off", Toast.LENGTH_SHORT).show()).addOnFailureListener(er->
                        Toast.makeText(Manual.this, ""+er.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }
}