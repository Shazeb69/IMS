package com.example.ims;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Stock extends AppCompatActivity {

    TextView stock,sold,need,cost,selling;
    EditText productname;
    FirebaseDatabase imsd;
    DatabaseReference imsr;

    Button clear,search,allview,mainmenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user == null) {
            startActivity(new Intent(Stock.this, Login.class));
        }
        productname = findViewById(R.id.editTextText2);
        stock = findViewById(R.id.textView2);
        need = findViewById(R.id.textView4);
        cost = findViewById(R.id.textView5);
        selling = findViewById(R.id.textView6);
        clear = findViewById(R.id.button3);
        search = findViewById(R.id.button);
        allview = findViewById(R.id.button4);
        mainmenu = findViewById(R.id.button7);

        stock.setText("Stock Left: ");
        need.setText("Need To Order?: ");
        cost.setText("Cost Price: ");
        selling.setText("Selling Price: ");
        stock.setVisibility(View.GONE);
        need.setVisibility(View.GONE);
        cost.setVisibility(View.GONE);
        selling.setVisibility(View.GONE);

        allview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                            AlertDialog.Builder builder = new AlertDialog.Builder(Stock.this);
                            builder.setTitle("All Stock Details");
                            builder.setItems(AllProducts.toArray(new String[AllProducts.size()]),null);
                            builder.setPositiveButton("Done",null);
                            builder.show();
                            fdbq.removeEventListener(this);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Stock.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                catch (Exception e){

                    Toast.makeText(Stock.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                productname.setText("");
                stock.setText("Stock Left: ");
                need.setText("Need To Order?: ");
                cost.setText("Cost Price: ");
                selling.setText("Selling Price: ");
                stock.setVisibility(View.GONE);
                need.setVisibility(View.GONE);
                cost.setVisibility(View.GONE);
                selling.setVisibility(View.GONE);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product,availables,costp,expiryd,sellingP;
                product = String.valueOf(productname.getText());
                if(product.isEmpty())
                {
                    Toast.makeText(Stock.this,"Please Provide Valid Details",Toast.LENGTH_LONG);
                    //finish();
                    return;
                }
                else {
                    try {
                        DatabaseReference frdb = FirebaseDatabase.getInstance().getReference("Product");
                        frdb.child(product).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        DataSnapshot dsfb = task.getResult();
                                        String ava = String.valueOf(dsfb.child("stock").getValue());
                                        String pri = String.valueOf(dsfb.child("price").getValue());
                                        String dte = String.valueOf(dsfb.child("expiry").getValue());
                                        String cpr = String.valueOf(dsfb.child("cost").getValue());
                                        stock.setText("Stock Left: "+ava);
                                        selling.setText("Selling Price: "+pri);
                                        cost.setText("Cost Price: "+cpr);
                                        stock.setVisibility(View.VISIBLE);
                                        need.setVisibility(View.VISIBLE);
                                        cost.setVisibility(View.VISIBLE);
                                        selling.setVisibility(View.VISIBLE);
                                        Integer stocknum = Integer.parseInt(ava);
                                        if (stocknum <= 10){
                                            need.setText("Need To Order?: YES");
                                            need.setTextColor(Color.RED);
                                        }
                                        else {
                                            need.setText("Need To Order?: No");
                                            need.setTextColor(Color.GREEN);
                                        }


                                    } else {
                                        Toast.makeText(Stock.this, "Product Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Stock.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                        Toast.makeText(Stock.this, "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        startActivity(new Intent(Stock.this,Mainmenu.class));
                        finish();
                    }
                });
            }

}