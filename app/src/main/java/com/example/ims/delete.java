package com.example.ims;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class delete extends AppCompatActivity {
    Button btn2,btn5;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        btn2 = findViewById(R.id.button2);
        btn5 = findViewById(R.id.button5);
        name = findViewById(R.id.editTextText);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name=name.getText().toString();
                DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("Product");
                dbr.child(Name).removeValue();
                Toast.makeText(delete.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(delete.this,MainActivity.class));
                finish();
            }
        });
    }
}