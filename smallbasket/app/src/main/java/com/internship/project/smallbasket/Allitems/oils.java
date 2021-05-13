package com.internship.project.smallbasket.AllItems;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.internship.project.smallbasket.R;
import com.squareup.picasso.Picasso;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class oils extends Fragment {

    View view;
    private DatabaseReference ProductsRef , mgetchild1 , mbasket;
    RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private String type = "";
    ImageView addtocart;
    private String m_Text = "";
    String s ="null";
    String value;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_oils, container,false );


        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_menu);
        addtocart = (ImageView) view.findViewById(R.id.addtocart_imagebutton);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(ProductViewHolder holder, int position, final Products model)
                    {


                        ProductsRef.child(model.getPid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            String k;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("AddToCart"+user.getUid())) {

                                    mgetchild1 = ProductsRef.child(model.getPid());
                                    mgetchild1.child("AddToCart" + user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            k = dataSnapshot.getValue().toString();
                                            s=k;
                                            if (!k.equals("0")) {

                                                holder.txtNoofitems.setVisibility(View.VISIBLE);
                                                holder.txtset_noofitems.setVisibility(View.VISIBLE);
                                                holder.txtset_noofitems.setText(k);
                                            }

                                            else {
                                                holder.addtocartimage.setVisibility(View.VISIBLE);
                                                holder.txtNoofitems.setVisibility(View.GONE);
                                                holder.txtset_noofitems.setVisibility(View.GONE);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                                else {
                                    holder.addtocartimage.setVisibility(View.VISIBLE);
                                    holder.txtNoofitems.setVisibility(View.GONE);
                                    holder.txtset_noofitems.setVisibility(View.GONE);
                                }


                            }



                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        if(model.getCategory().equals("oil") && (!model.getNo_of_product().equals("0"))) {
                            holder.txtProductName.setText(model.getPname());
                            holder.txtProductDescription.setText(model.getDescription());
                            holder.txtProductPrice.setText(model.getPrice() );
                            Picasso.with(getActivity()).load(model.getImage()).into(holder.imageView);


                        }
                        else {
                            holder.layout.setVisibility(View.GONE);
                        }

                        holder.addtocartimage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CustomDialogClass cdd = new CustomDialogClass(getActivity());
                                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                cdd.show();

                                cdd.getYesButton().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(holder.txtset_noofitems.getText().toString().equals("0")){

                                            value = cdd.getNumPicker();
                                            Log.d("Picker Value",value);
                                            //Toast.makeText(getContext(), value, Toast.LENGTH_SHORT).show();
                                            mbasket = FirebaseDatabase.getInstance().getReference();
                                            mbasket.child("basket").child("Basket"+user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                String s1;
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    s1 = dataSnapshot.getValue().toString();
                                                    float i = Float.parseFloat(s1);
                                                    int j = Integer.parseInt(value);
                                                    float k = Float.parseFloat(holder.txtProductPrice.getText().toString());
                                                    float set = i + (j*k);
                                                    holder.txtset_noofitems.setText(value);
                                                    ProductsRef.child(model.getPid()).child("AddToCart"+user.getUid()).setValue(value);
                                                    mbasket.child("basket").child("Basket"+user.getUid()).setValue(set);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            Toast.makeText(getActivity().getApplicationContext(),"Add Successfully",Toast.LENGTH_SHORT).show();
                                            cdd.dismiss();
                                        }
                                        else
                                        {
                                            String item = holder.txtset_noofitems.getText().toString();
                                            int x = Integer.parseInt(item);
                                            value = cdd.getNumPicker();
                                            Log.d("Picker Value",value);
                                            mbasket = FirebaseDatabase.getInstance().getReference();
                                            mbasket.child("basket").child("Basket"+user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                String s12;
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    s12 = dataSnapshot.getValue().toString();
                                                    float i1 = Float.parseFloat(s12);
                                                    int j1 = Integer.parseInt(value);
                                                    float k1 = Float.parseFloat(holder.txtProductPrice.getText().toString());
                                                    float set1 = (i1 - (x*k1));
                                                    float settobasket =set1 + (j1*k1);
                                                    holder.txtset_noofitems.setText(value);
                                                    ProductsRef.child(model.getPid()).child("AddToCart"+user.getUid()).setValue(value);
                                                    mbasket.child("basket").child("Basket"+user.getUid()).setValue(settobasket);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            Toast.makeText(getActivity().getApplicationContext(),"Add Successfully",Toast.LENGTH_SHORT).show();
                                            cdd.dismiss();
                                        }                                                             }

                                });
                                cdd.getNo().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cdd.dismiss();
                                        holder.addtocartimage.setVisibility(View.VISIBLE);
                                    }
                                });





                            }
                        });


                    }

                    @Override
                    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



}
