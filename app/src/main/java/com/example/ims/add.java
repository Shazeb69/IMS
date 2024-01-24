package com.example.ims;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;

public class add extends AppCompatActivity {

    EditText pname, sprice, cprice, astock, edate;
    String product,sellingP, costp, availables, expiryd;

    Button add,back;
    FirebaseDatabase imsd;
    DatabaseReference imsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        add = findViewById(R.id.button2);
        back = findViewById(R.id.button5);
        pname = findViewById(R.id.editTextText);
        astock = findViewById(R.id.editTextText3);
        cprice = findViewById(R.id.editTextText5);
        edate = findViewById(R.id.editTextText6);
        sprice = findViewById(R.id.editTextText7);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = String.valueOf(pname.getText());
                availables = String.valueOf(astock.getText());
                costp = String.valueOf(cprice.getText());
                expiryd = String.valueOf(edate.getText());
                sellingP = String.valueOf(sprice.getText());


                try {


                double costint = Double.parseDouble(costp);
                String[] expirydd = expiryd.split(" ");
                String days = expirydd[0];
                String[] avastock = availables.split(" ");
                String stock = avastock[0];
                int avaint = Integer.parseInt(stock);
                int daysint = Integer.parseInt(days);
                double sellingpp = Double.parseDouble(sellingP);

                if(product.isEmpty()||sellingP.isEmpty()||costp.isEmpty()||availables.isEmpty()||expiryd.isEmpty()||avaint<0||costint<0||daysint<0||sellingpp<0)
                {
                    Toast.makeText(add.this,"Please Provide Valid Details",Toast.LENGTH_SHORT).show();


                }
                else {
                    imsHelper imsh = new imsHelper(product,availables,costp,expiryd,sellingP);
                    imsd = FirebaseDatabase.getInstance();
                    imsr = imsd.getReference("Product");
                    imsr.child(product).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                       if(task.isSuccessful()){
                           if(task.getResult().exists()){
                               Toast.makeText(add.this, "Product Exists", Toast.LENGTH_SHORT).show();
                           }
                           else {
                               imsr.child(product).setValue(imsh);
                               Toast.makeText(add.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                           }
                       }
                       else{
                           Toast.makeText(add.this, "Unable to Connect"+ task.getException().getMessage(),
                                   Toast.LENGTH_SHORT).show();
                       }

                        }
                    });
                }

            }
                catch (Exception e){
                    Toast.makeText(add.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(add.this,MainActivity.class));
                finish();
            }
        });
    }
}