package com.internship.project.smallbasket.AllItems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.internship.project.smallbasket.BottonNev.category;
import com.internship.project.smallbasket.BottonNev.profile;
import com.internship.project.smallbasket.BottonNev.profile_ifexist;
import com.internship.project.smallbasket.BottonNev.customers_main;
import com.internship.project.smallbasket.BottonNev.payment;
import com.internship.project.smallbasket.R;
import com.squareup.picasso.Picasso;

public class basket extends AppCompatActivity {

    private DatabaseReference ProductsRef , mDatabaseRef;
    public RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ImageView addtocart;
    private Button removecart ,placeorder;
    private int total_price;
    private TextView total;
    FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mcheck , mgetchild1 , mgetbasketprice ,mRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String s = "null";

    String values,prodct;







    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(basket.this, customers_main.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.setSelectedItemId(R.id.basket);

        placeorder = (Button) findViewById(R.id.place_order);
        mRef = FirebaseDatabase.getInstance().getReference();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_basket);
        addtocart = (ImageView) findViewById(R.id.addtocart_imagebutton);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(basket.this);
        recyclerView.setLayoutManager(layoutManager);
        removecart = (Button) findViewById(R.id.remove_cart);
        total = (TextView) findViewById(R.id.total);


        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                startActivity(new Intent(basket.this,payment.class));


            }
        });


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.payment:
                        startActivity(new Intent(basket.this, payment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        mcheck = FirebaseDatabase.getInstance().getReference().child("USERS");
                        mcheck.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(us.getUid()))
                                {
                                    startActivity(new Intent(basket.this, profile_ifexist.class));
                                }
                                else
                                {
                                    startActivity(new Intent(basket.this, profile.class));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        return true;
                    case R.id.home:
                        startActivity(new Intent(basket.this, customers_main.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.catagory:
                        startActivity(new Intent(basket.this, category.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStart() {

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");



        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {



                    @Override
                    protected void onBindViewHolder(ProductViewHolder holder, int position, final Products model) {

                        mDatabaseRef.child("basket").child("Basket"+user.getUid()).addValueEventListener(new ValueEventListener() {
                            String t ;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                t = dataSnapshot.getValue().toString();
                                Float fi = Float.parseFloat(t);
                                String d = String.format("%.2f",fi);
                                total.setText(d);;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        ProductsRef.child(model.getPid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            String k;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.hasChild("AddToCart"+user.getUid())) {

                                    mgetchild1 = ProductsRef.child(model.getPid());
                                mgetchild1.child("AddToCart" + user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(DataSnapshot dataSnapshot) {

                                            k = dataSnapshot.getValue().toString();

                                            s=k;
                                            if (!k.equals("0"))
                                            {
                                                holder.txtProductName.setText(model.getPname());
                                                holder.txtProductDescription.setText(model.getDescription());
                                                holder.txtProductPrice.setText(model.getPrice() );
                                                Picasso.with(basket.this).load(model.getImage()).into(holder.imageView);
                                                holder.txtset_noofitems.setText(k);
                                                holder.addtocartimage.setVisibility(View.GONE);
                                                holder.removecart.setVisibility(View.VISIBLE);
                                                mgetbasketprice=ProductsRef.child(model.getPid());

                                            }

                                            else {

                                                holder.layout.setVisibility(View.GONE);
                                            }
                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError databaseError) {}
                                });



                            }
                            else
                            {
                                    holder.layout.setVisibility(View.GONE);
                            }

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) { }
                        });


                        holder.removecart.setOnClickListener(new View.OnClickListener() {
                            String s1;
                            @Override
                            public void onClick(View v) {

                                mgetchild1 = ProductsRef.child(model.getPid());
                                String noofitem = holder.txtset_noofitems.getText().toString();
                                int int_noofitem = Integer.parseInt(noofitem);
                                String price = holder.txtProductPrice.getText().toString();
                                float int_price = Float.parseFloat(price);
                                mDatabaseRef.child("basket").child("Basket"+user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    String d;
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        d= dataSnapshot.getValue().toString();
                                        float val = Float.parseFloat(d);
                                        float set = val - (int_noofitem*int_price);
                                        String d = String.format("%.2f",set);
                                        mDatabaseRef.child("basket").child("Basket"+user.getUid()).setValue(d);



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                ProductsRef.child(model.getPid()).child("AddToCart"+user.getUid()).removeValue();
                                holder.txtset_noofitems.setText("0");
                                Toast.makeText(basket.this,"Removed",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                    @Override
                    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
