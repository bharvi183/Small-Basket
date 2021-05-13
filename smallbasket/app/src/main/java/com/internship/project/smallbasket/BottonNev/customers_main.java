package com.internship.project.smallbasket.BottonNev;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.internship.project.smallbasket.AllItems.Baby_care;
import com.internship.project.smallbasket.AllItems.Beauty_product;
import com.internship.project.smallbasket.AllItems.Beverages;
import com.internship.project.smallbasket.AllItems.Body_care;
import com.internship.project.smallbasket.AllItems.Chocolate;
import com.internship.project.smallbasket.AllItems.Dairy_products;
import com.internship.project.smallbasket.AllItems.Home_cleaning;
import com.internship.project.smallbasket.AllItems.basket;
import com.internship.project.smallbasket.Categories.Foodgrain;
import com.internship.project.smallbasket.Categories.Snacks;
import com.internship.project.smallbasket.Categories.oils_and_masala;
import com.internship.project.smallbasket.Categories.Fruits_Vegetables;
import com.internship.project.smallbasket.R;


public class customers_main extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private ImageView fv , coldrinks , chocolate ,foodgrain , oil_masalas , cleaning ,dailycleaning , dairy ,snacks , beauty , babycare , bodycare ,dailyfv ,dailyfood , dailydairy;
    private DatabaseReference mcheck ;
    FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();




    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_main);



        bodycare = (ImageView) findViewById(R.id.bodycare);
        babycare = (ImageView) findViewById(R.id.babycare);
        beauty = (ImageView) findViewById(R.id.beauty);
        snacks = (ImageView) findViewById(R.id.snacks);
        dairy = (ImageView) findViewById(R.id.dairy_product);
        dailydairy = findViewById(R.id.dailydairyproduct);
        cleaning = (ImageView) findViewById(R.id.cleanning);
        dailycleaning = findViewById(R.id.dailyhousecleaning);
        oil_masalas = (ImageView) findViewById(R.id.oils_masala);
        fv = (ImageView) findViewById(R.id.fv);
        dailyfv = findViewById(R.id.dailyfv);
        coldrinks = (ImageView) findViewById(R.id.coldrinks);
        chocolate = (ImageView) findViewById(R.id.chocolate);
        foodgrain = (ImageView) findViewById(R.id.foodgrain);
        dailyfood = findViewById(R.id.dailyfoodgrain);


        bodycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Body_care.class));
            }
        });

        babycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Baby_care.class));
            }
        });

        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Beauty_product.class));
            }
        });

        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Snacks.class));
            }
        });

        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Dairy_products.class));
            }
        });
        dailydairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Dairy_products.class));
            }
        });

        cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Home_cleaning.class));
            }
        });
        dailycleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Home_cleaning.class));
            }
        });

        oil_masalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, oils_and_masala.class));
            }
        });
        foodgrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Foodgrain.class));
            }
        });

        dailyfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Foodgrain.class));
            }
        });


        coldrinks = (ImageView) findViewById(R.id.coldrinks);
        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.setSelectedItemId(R.id.home);


        mAuth = FirebaseAuth.getInstance();


        fv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Fruits_Vegetables.class));
            }
        });
        dailyfv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Fruits_Vegetables.class));
            }
        });
        coldrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Beverages.class));
            }
        });
        chocolate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(customers_main.this, Chocolate.class));
            }
        });


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.payment:
                        startActivity(new Intent(customers_main.this, payment.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        mcheck = FirebaseDatabase.getInstance().getReference().child("USERS");
                        mcheck.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(us.getUid())) {
                                    startActivity(new Intent(customers_main.this, profile_ifexist.class));
                                } else {
                                    startActivity(new Intent(customers_main.this, profile.class));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        return true;
                    case R.id.basket:
                        startActivity(new Intent(customers_main.this, basket.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.catagory:
                        startActivity(new Intent(customers_main.this, category.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }

}
