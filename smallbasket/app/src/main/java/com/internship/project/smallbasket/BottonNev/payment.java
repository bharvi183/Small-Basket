package com.internship.project.smallbasket.BottonNev;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.internship.project.smallbasket.AllItems.basket;
import com.internship.project.smallbasket.R;

import java.util.ArrayList;

public class payment extends AppCompatActivity {

    FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mcheck;
    DatabaseReference myref , mRef ,mforsms;
    String amount;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int i1 ,j1;
    String set;
    String check;
    String s = "";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String pno;

    //.................................................................................................

    EditText  noteEt, nameEt, upiIdEt;
    TextView amountEt;
    Button send , cod;

    final int UPI_PAYMENT = 0;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(payment.this, basket.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);



        mRef = FirebaseDatabase.getInstance().getReference();
        myref = FirebaseDatabase.getInstance().getReference();
        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bnv.setSelectedItemId(R.id.payment);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.basket:
                        startActivity(new Intent(payment.this, basket.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        mcheck = FirebaseDatabase.getInstance().getReference().child("USERS");
                        mcheck.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(us.getUid()))
                                {
                                    startActivity(new Intent(payment.this,profile_ifexist.class));
                                }
                                else
                                {
                                    startActivity(new Intent(payment.this,profile.class));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        return true;


                    case R.id.home:
                        startActivity(new Intent(payment.this, customers_main.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.catagory:
                        startActivity(new Intent(payment.this, category.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue().toString();
                amount=value;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
//        float f = Float.parseFloat(amount);
        myref.child("basket").child("Basket"+user.getUid()).addValueEventListener(new ValueEventListener() {
            String t ;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                t = dataSnapshot.getValue().toString();
                Float fi = Float.parseFloat(t);
                String d = String.format("%.2f",fi);
                amountEt.setText(d);;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("basket").child("Basket"+user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                check = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.child("USERS").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Address")){
                    s="y";
                }
                else {
                    s="n";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cod = (Button) findViewById(R.id.button_cod);
        cod.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //sendmail();

                if (!check.equals("0.00")){
                    if(s.equals("y")){
                        /*mcheck = FirebaseDatabase.getInstance().getReference();
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
                        mcheck.child("USERS").child(user.getUid()).child("Phone No").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                pno = dataSnapshot.getValue().toString();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        mcheck.child("basket").child("Basket" + us.getUid()).setValue("0.00");

                        Toast.makeText(payment.this, "congratulations....", Toast.LENGTH_SHORT).show();*/
                        startActivity(new Intent(payment.this,confirm_order.class));
                    }
                    else {
                        startActivity(new Intent(payment.this,profile.class));
                        Toast.makeText(payment.this,"Please Complete Your Profile..",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(payment.this,"buy any Product to confirm Your Order..",Toast.LENGTH_SHORT).show();
                }


            }
        });
        initializeViews();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the values from the EditTexts
                String amount = amountEt.getText().toString();
                String note = noteEt.getText().toString();
                String name = nameEt.getText().toString();
                String upiId = upiIdEt.getText().toString();
                payUsingUpi(amount, upiId, name, note);
            }
        });
    }

    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(pno, null,"hello", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }




    void initializeViews() {
        send = findViewById(R.id.send);
        amountEt = findViewById(R.id.amount_et);
        noteEt = findViewById(R.id.note);
        nameEt = findViewById(R.id.name);
        upiIdEt = findViewById(R.id.upi_id);
    }

    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(payment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(payment.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                //sendmail();
                final Query userQuery = mRef.orderByChild("AddToCart"+user.getUid());

                userQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        //Get the node from the datasnapshot

                        for (DataSnapshot child: dataSnapshot.getChildren())
                        {
                            String key = child.getKey().toString();
                            String value = child.getValue().toString();
                            if(key.endsWith("PM") || key.endsWith("AM")){
                                //Log.w("ssss",key);
                                mRef.child("Products").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.hasChild("AddToCart"+user.getUid())){
                                            //Log.w("ssss",key);
                                            mRef.child("Products").child(key).child("AddToCart"+user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    int j = Integer.parseInt(dataSnapshot.getValue().toString());

                                                    if(j!=0){
                                                        i1=j;
                                                    }
                                                    else {

                                                    }
                                                    mRef.child("Products").child(key).child("AddToCart"+user.getUid()).removeValue();

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                            mRef.child("Products").child(key).child("no_of_product").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    j1 = Integer.parseInt(dataSnapshot.getValue().toString());
                                                    j1=j1-i1;
                                                    mRef.child("Products").child(key).child("no_of_product").setValue(String.valueOf(j1));

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

                myref.child("basket").child("Basket"+user.getUid()).setValue("0");
                Toast.makeText(payment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();

            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(payment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(payment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean isConnectionAvailable(payment context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {

            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }


}
