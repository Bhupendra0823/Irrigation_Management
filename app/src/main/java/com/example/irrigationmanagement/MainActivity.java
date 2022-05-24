package com.example.irrigationmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();

        Button manu = (Button) findViewById(R.id.manual);
        Button auto = (Button) findViewById(R.id.automatic);
        DAOMan_Input daomi = new DAOMan_Input();
        manu.setOnClickListener(v->
        {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("Manual","1");
            daomi.update("Manual Value",hashMap).addOnSuccessListener(suc->
                    Toast.makeText(this,"Manual Mode Activated",Toast.LENGTH_SHORT).show()).addOnFailureListener(er->
                    Toast.makeText(this,""+er.getMessage(),Toast.LENGTH_SHORT).show());
            Intent i = new Intent(MainActivity.this,Manual.class);
            startActivity(i);
        });

        auto.setOnClickListener(v->
        {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("Manual","0");
            daomi.update("Manual Value",hashMap).addOnSuccessListener(suc->
                    Toast.makeText(this,"Automatic Mode Activated",Toast.LENGTH_SHORT).show()).addOnFailureListener(er->
                    Toast.makeText(this,""+er.getMessage(),Toast.LENGTH_SHORT).show());
            Intent i = new Intent(MainActivity.this,Automatic.class);
            startActivity(i);
        });
    }
}