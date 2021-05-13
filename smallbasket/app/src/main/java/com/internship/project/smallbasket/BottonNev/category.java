package com.internship.project.smallbasket.BottonNev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.internship.project.smallbasket.AllItems.Beverages;
import com.internship.project.smallbasket.AllItems.Body_care;
import com.internship.project.smallbasket.AllItems.Chocolate;
import com.internship.project.smallbasket.AllItems.Dairy_products;
import com.internship.project.smallbasket.AllItems.Home_cleaning;
import com.internship.project.smallbasket.Categories.Foodgrain;
import com.internship.project.smallbasket.Categories.Snacks;
import com.internship.project.smallbasket.Categories.oils_and_masala;
import com.internship.project.smallbasket.AllItems.Baby_care;
import com.internship.project.smallbasket.AllItems.Beauty_product;
import com.internship.project.smallbasket.AllItems.basket;
import com.internship.project.smallbasket.Categories.Fruits_Vegetables;
import com.internship.project.smallbasket.R;

public class category extends AppCompatActivity {

    TextView fv,fruit,vegetable , foodgrain , packed , unpacked , oil_masala , oil , masala ,cleaning , h_cleaning ,snacks, psnacks , bsnacks;
    FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mcheck;
    TextView coldroinks;
    TextView chocolates , dairy , beauty , babycare , bodycare;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(category.this,customers_main.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);
        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.setSelectedItemId(R.id.catagory);

        coldroinks = findViewById(R.id.Beverages);
        chocolates = findViewById(R.id.chocolate);
        dairy = (TextView) findViewById(R.id.dairyproduct);
        beauty = (TextView) findViewById(R.id.beautyproduct);
        babycare = findViewById(R.id.babycare);
        bodycare = findViewById(R.id.bodycare);
        fv = (TextView) findViewById(R.id.fruits_vegetable);
        fruit = (TextView) findViewById(R.id.fruits);
        vegetable = (TextView) findViewById(R.id.vegetable);
        foodgrain = (TextView) findViewById(R.id.foodgrains);
        packed = (TextView) findViewById(R.id.packed);
        unpacked = (TextView) findViewById(R.id.unpacked);
        oil_masala = (TextView) findViewById(R.id.oilandmasala);
        oil = (TextView) findViewById(R.id.oil);
        masala= (TextView) findViewById(R.id.masala);
        cleaning = (TextView) findViewById(R.id.cleanning);
        h_cleaning = (TextView) findViewById(R.id.housecleanninng);
        snacks = (TextView) findViewById(R.id.snacks);
        psnacks=(TextView) findViewById(R.id.packetsnacks);
        bsnacks=(TextView)findViewById(R.id.bakerysnacks);



        bodycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Body_care.class));
            }
        });
        babycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Baby_care.class));
            }
        });
        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Beauty_product.class));
            }
        });
        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Dairy_products.class));
            }
        });
        chocolates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Chocolate.class));
            }
        });
        coldroinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Beverages.class));
            }
        });
        packed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Foodgrain.class));
            }
        });
        unpacked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Foodgrain.class));
            }
        });
        oil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, oils_and_masala.class));
            }
        });
        masala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, oils_and_masala.class));
            }
        });
        h_cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Home_cleaning.class));
            }
        });
        psnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Snacks.class));
            }
        });
        bsnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Snacks.class));
            }
        });
        snacks.setOnClickListener(new View.OnClickListener() {
            int clicker = 0;
            @Override
            public void onClick(View v) {
                if(clicker==0)
                {
                    psnacks.setVisibility(View.VISIBLE);
                    bsnacks.setVisibility(View.VISIBLE);
                    clicker=1;
                }
                else
                {
                    psnacks.setVisibility(View.GONE);
                    bsnacks.setVisibility(View.GONE);
                    clicker=0;
                }
            }
        });
        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Fruits_Vegetables.class));
            }
        });
        vegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category.this, Fruits_Vegetables.class));
            }
        });
        cleaning.setOnClickListener(new View.OnClickListener() {
            int clicker=0;
            @Override
            public void onClick(View v) {
                if(clicker==0)
                {
                    h_cleaning.setVisibility(View.VISIBLE);
                    clicker=1;
                }
                else
                {
                    h_cleaning.setVisibility(View.GONE);
                    clicker=0;
                }
            }
        });
        oil_masala.setOnClickListener(new View.OnClickListener() {
            int clicker=0;
            @Override
            public void onClick(View v) {
                if(clicker==0)
                {
                    oil.setVisibility(View.VISIBLE);
                    masala.setVisibility(View.VISIBLE);
                    clicker=1;
                }
                else
                {
                    oil.setVisibility(View.GONE);
                    masala.setVisibility(View.GONE);
                    clicker=0;
                }
            }
        });
        foodgrain.setOnClickListener(new View.OnClickListener() {
            int clicker=0;
            @Override
            public void onClick(View v) {
                if(clicker==0) {
                    packed.setVisibility(View.VISIBLE);
                    unpacked.setVisibility(View.VISIBLE);
                    clicker=1;
                }
                else {
                    packed.setVisibility(View.GONE);
                    unpacked.setVisibility(View.GONE);
                    clicker=0;
                }
            }
        });
        fv.setOnClickListener(new View.OnClickListener() {
            int clicker = 0;
            @Override
            public void onClick(View v) {
                if(clicker==0) {
                    fruit.setVisibility(View.VISIBLE);
                    vegetable.setVisibility(View.VISIBLE);
                    clicker=1;
                }
                else {
                    fruit.setVisibility(View.GONE);
                    vegetable.setVisibility(View.GONE);
                    clicker=0;
                }
            }
        });
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.payment:
                        startActivity(new Intent(category.this, payment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        mcheck = FirebaseDatabase.getInstance().getReference().child("USERS");
                        mcheck.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(us.getUid()))
                                {
                                    startActivity(new Intent(category.this,profile_ifexist.class));
                                }
                                else
                                {
                                    startActivity(new Intent(category.this,profile.class));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        return true;
                    case R.id.home:
                        startActivity(new Intent(category.this, customers_main.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.basket:
                        startActivity(new Intent(category.this, basket.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
