package com.example.irrigationmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Automatic extends AppCompatActivity {

    TextView ahv;
    TextView atfv;
    TextView atcv;
    TextView amv;
    TextView aisv;

    DatabaseReference ref, ref1,ref2;
    FirebaseDatabase mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic);


        ahv = findViewById(R.id.autohumidvalue);
        atfv = findViewById(R.id.autotempfahrenvalue);
        atcv = findViewById(R.id.autotempcelvalue);
        amv = findViewById(R.id.automoisturevalue);
        aisv = findViewById(R.id.autoirrigationstatusvalue);

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


                ahv.setText(humi);
                atfv.setText(tempfrn);
                atcv.setText(tempcel);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Automatic.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String irristat = snapshot.getValue(String.class);
                aisv.setText(irristat);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Automatic.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mois = snapshot.child("Moisture").getValue(String.class);
                amv.setText(mois);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Automatic.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}