package com.internship.project.smallbasket.BottonNev;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.internship.project.smallbasket.R;

public class confirm_order extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button sendBtn;
    int j1 , i1;
    String supid;
    DatabaseReference mcheck;
    String s1;
    FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        sendBtn = (Button) findViewById(R.id.cod_button);

        mcheck = FirebaseDatabase.getInstance().getReference();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mcheck = FirebaseDatabase.getInstance().getReference();
                final Query userQuery = mcheck.orderByChild("AddToCart" + us.getUid());

                userQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String key = child.getKey().toString();
                            String value = child.getValue().toString();
                            if (key.endsWith("PM") || key.endsWith("AM")) {
                                mcheck.child("Products").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild("AddToCart" + us.getUid())) {
                                            mcheck.child("Products").child(key).child("AddToCart" + us.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    int j = Integer.parseInt(dataSnapshot.getValue().toString());

                                                    if (j != 0) {
                                                        i1 = j;
                                                    } else {

                                                    }
                                                    mcheck.child("Products").child(key).child("AddToCart" + us.getUid()).removeValue();

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                            mcheck.child("Products").child(key).child("no_of_product").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    j1 = Integer.parseInt(dataSnapshot.getValue().toString());
                                                    j1 = j1 - i1;
                                                    mcheck.child("Products").child(key).child("no_of_product").setValue(String.valueOf(j1));


                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            mcheck.child("Products").child(key).child("supplier_ID").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    supid = dataSnapshot.getValue().toString();
                                                    mcheck.child("Supplier_profile").child(supid).child("Phone No").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                            String s = dataSnapshot.getValue().toString();
                                                            mcheck.child("Products").child(key).child("pname").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    s1 = dataSnapshot.getValue().toString();
                                                                    String msg ="hello dear supplier..." + "\n" + "Our Customer ordered Your- " + "\n" + i1 +" "+ s1 ;

                                                                    //Getting intent and PendingIntent instance
                                                                    Intent intent=new Intent(confirm_order.this,payment.class);
                                                                    PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                                                                    //Get the SmsManager instance and call the sendTextMessage method to send message
                                                                    SmsManager sms=SmsManager.getDefault();
                                                                    sms.sendTextMessage(s, null, msg, pi,null);

                                                                    Toast.makeText(getApplicationContext(), "supplier get Your Request successfully!",
                                                                            Toast.LENGTH_LONG).show();
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });


                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                            mcheck.child("USERS").child(user.getUid()).child("Address").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String add = dataSnapshot.getValue().toString();
                                                    mcheck.child("USERS").child(user.getUid()).child("Phone No").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            String phone = dataSnapshot.getValue().toString();
                                                            mcheck.child("USERS").child(user.getUid()).child("user name").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    String uname = dataSnapshot.getValue().toString();
                                                                    mcheck.child("Products").child(key).child("pname").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            String pname = dataSnapshot.getValue().toString();
                                                                            String msg = "Name : " + uname + "\n" +"Address : "+add + "\n" + "Phone No.: " + phone + "\n" + " wants to buy - "+"\n" +
                                                                                    i1 + " "+ pname ;
                                                                            Intent intent=new Intent(getApplicationContext(),payment.class);
                                                                            PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                                                                            //Get the SmsManager instance and call the sendTextMessage method to send message
                                                                            SmsManager sms=SmsManager.getDefault();
                                                                            sms.sendTextMessage("6354767080", null, msg, pi,null);

                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                mcheck.child("basket").child("Basket" + us.getUid()).setValue("0.00");

                Toast.makeText(confirm_order.this, "congratulations....", Toast.LENGTH_SHORT).show();

                    mcheck.child("USERS").child(user.getUid()).child("user name").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String d = dataSnapshot.getValue().toString();
                            String msg="Dear "+ d +" ,"+"\n"+"congratulations..." + "\n" + "Your order is confirmed and it will reach at your home between upcoming 5 To 6 days" + "\n" + "THANK YOU";

                            mcheck.child("USERS").child(user.getUid()).child("Phone No").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String no = dataSnapshot.getValue().toString();
                                    //Getting intent and PendingIntent instance
                                    Intent intent=new Intent(getApplicationContext(),payment.class);
                                    PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                                    //Get the SmsManager instance and call the sendTextMessage method to send message
                                    SmsManager sms=SmsManager.getDefault();
                                    sms.sendTextMessage(no, null, msg, pi,null);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


            }
        });
    }


   }
