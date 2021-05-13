package com.internship.project.smallbasket.BottonNev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.internship.project.smallbasket.AllItems.basket;
import com.internship.project.smallbasket.R;
import com.internship.project.smallbasket.login_user;
import com.squareup.picasso.Picasso;


public class profile_ifexist extends AppCompatActivity {




    private FirebaseAuth mAuth;
    TextView display_uname , display_uphone , display_uaddress ;
    Button update_btn;
    private Button signout;
    ImageView displayuserphoto;
    private StorageReference userImagesRef;
    private DatabaseReference UsersRef , mchild ,signoutref;
    FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
    BottomNavigationView bnv;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(profile_ifexist.this,customers_main.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ifexist);

        bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.setSelectedItemId(R.id.profile);
        update_btn= (Button) findViewById(R.id.update_button);
        signout = (Button) findViewById(R.id.signout);
        display_uname = (TextView) findViewById(R.id.display_username);
        display_uaddress = (TextView) findViewById(R.id.display_useraddress);
        display_uphone =(TextView) findViewById(R.id.display_userphone);
        displayuserphoto = (ImageView) findViewById(R.id.display_userphoto);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userImagesRef = FirebaseStorage.getInstance().getReference().child("user Images");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("USERS");
        signoutref = FirebaseDatabase.getInstance().getReference();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile_ifexist.this, login_user.class));
            }
        });


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile_ifexist.this,update_profile.class));
            }
        });


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.payment:
                        startActivity(new Intent(profile_ifexist.this, payment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.basket:
                        startActivity(new Intent(profile_ifexist.this, basket.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(profile_ifexist.this, customers_main.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.catagory:
                        startActivity(new Intent(profile_ifexist.this, category.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mchild = UsersRef.child(us.getUid());

        mchild.child("user name").addValueEventListener(new ValueEventListener() {
            String name;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue().toString();
                display_uname.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mchild.child("Phone No").addValueEventListener(new ValueEventListener() {
            String no;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                no = dataSnapshot.getValue().toString();
                display_uphone.setText(no);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mchild.child("Address").addValueEventListener(new ValueEventListener() {
            String no;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                no = dataSnapshot.getValue().toString();
                display_uaddress.setText(no);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mchild.child("image").addValueEventListener(new ValueEventListener() {
            String no;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                no = dataSnapshot.getValue().toString();
                Picasso.with(profile_ifexist.this).load(no).into(displayuserphoto);            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
