package com.example.ims;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button view, add, update, delete,  btn ,fnd;
    EditText pname, sprice, cprice, astock, edate;
    String product,sellingP, costp, availables, expiryd;

    FirebaseDatabase imsd;
    DatabaseReference imsr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user == null) {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
        view = findViewById(R.id.vw);
        add = findViewById(R.id.ad);
        update = findViewById(R.id.upd);
        delete = findViewById(R.id.del);
        btn = findViewById(R.id.bt2);
        pname = findViewById(R.id.pn);
        sprice = findViewById(R.id.sp);
        cprice = findViewById(R.id.cp);
        astock = findViewById(R.id.as1);
        edate = findViewById(R.id.ed);
        fnd = findViewById(R.id.find);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                }

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    imsd = FirebaseDatabase.getInstance();
                    imsr = imsd.getReference("Product");
                    Query fdbq = imsr.orderByValue();


                    fdbq.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            List<String> AllProducts = new ArrayList<>();
                            AllProducts.add("-------------------------");
                            for(DataSnapshot fdsnapshot : dataSnapshot.getChildren()){
                                Product p = new Product();
                                p.setCost(fdsnapshot.child("cost").getValue(String.class));
                                p.setExpiry(fdsnapshot.child("expiry").getValue(String.class));
                                p.setPrice(fdsnapshot.child("price").getValue(String.class));
                                p.setProduct(fdsnapshot.child("product").getValue(String.class));
                                p.setStock(fdsnapshot.child("stock").getValue(String.class));

                                AllProducts.add(p.toString());
                                AllProducts.add("-------------------------");

                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("All Product Details");
                            builder.setItems(AllProducts.toArray(new String[AllProducts.size()]),null);
                            builder.setPositiveButton("Done",null);
                            builder.show();
                            fdbq.removeEventListener(this);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                catch (Exception e){

                    Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }




            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,add.class));
                finish();


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, com.example.ims.update.class));
                finish();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,com.example.ims.delete.class));
                finish();
            }
        });

        fnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = String.valueOf(pname.getText());
                availables = String.valueOf(astock.getText());
                costp = String.valueOf(cprice.getText());
                expiryd = String.valueOf(edate.getText());
                sellingP = String.valueOf(sprice.getText());
                if(product.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please Provide Valid Details",Toast.LENGTH_LONG);
                    finish();
                    return;
                }
                else {
                    try {
                        DatabaseReference frdb = FirebaseDatabase.getInstance().getReference("Product");
                        frdb.child(product).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if(task.isSuccessful()){
                                    if(task.getResult().exists()){
                                        DataSnapshot dsfb = task.getResult();
                                        String ava = String.valueOf(dsfb.child("stock").getValue());
                                        String pri = String.valueOf(dsfb.child("price").getValue());
                                        String dte = String.valueOf(dsfb.child("expiry").getValue());
                                        String cpr = String.valueOf(dsfb.child("cost").getValue());
                                        astock.setText(ava);
                                        cprice.setText(pri);
                                        edate.setText(dte);
                                        sprice.setText(cpr);




                                    }else{
                                        Toast.makeText(MainActivity.this, "Product Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}