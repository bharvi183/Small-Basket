package com.internship.project.smallbasket.BottonNev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.UploadTask;
import com.internship.project.smallbasket.AllItems.basket;
import com.internship.project.smallbasket.R;
import com.internship.project.smallbasket.login_user;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class profile extends AppCompatActivity {




    private FirebaseAuth mAuth;
    TextView setuser ,display_uname , display_uphone , display_uaddress , d_uname , d_uphone , d_uaddress;
    ImageView photo;
    TextView text;
    Button create_profile , cancle_btn , done_btn , update_btn;
    String u_name , u_address , u_phone ,saveCurrentDate;
    EditText name,phone,address;
    ImageView setuserphoto , displayuserphoto;
    private Uri ImageUri;
    private Button signout;
    private String downloadImageUrl ,productRandomKey;
    private ProgressDialog loadingBar;
    private StorageReference userImagesRef;
    private DatabaseReference UsersRef ,mname , mphone ,maddress, mimgurl , mchild ,mcheck;
    private static final int GalleryPick = 1;
    FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();
    String username;
    BottomNavigationView bnv;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(profile.this,customers_main.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.setSelectedItemId(R.id.profile);
        setuser = (TextView) findViewById(R.id.user_name);
        photo = (ImageView) findViewById(R.id.user_photo);
        create_profile = (Button) findViewById(R.id.create_profile);
        text = (TextView) findViewById(R.id.text);
        cancle_btn = (Button) findViewById(R.id.cancle_button);
        done_btn = (Button)findViewById(R.id.done_button);
        name = (EditText) findViewById(R.id.setuser_name);
        phone = (EditText) findViewById(R.id.setuser_phone);
        address = (EditText) findViewById(R.id.setuser_address);
        setuserphoto = (ImageView) findViewById(R.id.setuser_photo);
        update_btn= (Button) findViewById(R.id.update_button);
        display_uname = (TextView) findViewById(R.id.display_username);
        display_uaddress = (TextView) findViewById(R.id.display_useraddress);
        display_uphone =(TextView) findViewById(R.id.display_userphone);
        displayuserphoto = (ImageView) findViewById(R.id.display_userphoto);
        loadingBar = new ProgressDialog(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Picasso.with(profile.this).load(user.getPhotoUrl()).into(photo);
        setuser.setText(user.getDisplayName());
        signout = (Button) findViewById(R.id.signout);
        userImagesRef = FirebaseStorage.getInstance().getReference().child("user Images");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("USERS");
        mcheck = FirebaseDatabase.getInstance().getReference();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile.this, login_user.class));
            }
        });
        setuserphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });
        create_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout.setVisibility(View.GONE);
                photo.setVisibility(View.GONE);
                setuser.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                create_profile.setVisibility(View.GONE);
                bnv.setVisibility(View.GONE);
                update_btn.setVisibility(View.GONE);
                display_uphone.setVisibility(View.GONE);
                display_uname.setVisibility(View.GONE);
                display_uaddress.setVisibility(View.GONE);

                displayuserphoto.setVisibility(View.GONE);

            }
        });
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.setVisibility(View.VISIBLE);
                setuser.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);
                create_profile.setVisibility(View.VISIBLE);
                bnv.setVisibility(View.VISIBLE);
                signout.setVisibility(View.VISIBLE);
            }
        });
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = name.getText().toString();
                ValidateProductData();

            }
        });
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.payment:
                        startActivity(new Intent(profile.this, payment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.basket:
                        startActivity(new Intent(profile.this, basket.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(profile.this, customers_main.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.catagory:
                        startActivity(new Intent(profile.this, category.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            setuserphoto.setImageURI(ImageUri);
        }
    }
    private void ValidateProductData() {
        u_name = name.getText().toString();
        u_phone = phone.getText().toString();
        u_address = address.getText().toString();


        if(TextUtils.isEmpty(u_name))
        {
            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_SHORT).show();
        }
        else if (ImageUri == null)
        {
            Toast.makeText(this, "Enter Image", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(u_address))
        {
            Toast.makeText(this, "Enter Your Address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(u_phone))
        {
            Toast.makeText(this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if (u_phone.length()>10 || u_phone.length()<10 || u_phone.contains("-") || u_phone.contains("."))
        {
            Toast.makeText(this, "Enter 10 digit Phone Number", Toast.LENGTH_SHORT).show();
        }

        else
        {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation() {
        loadingBar.setMessage("Creating Profile...");
        loadingBar.setCanceledOnTouchOutside(false);



        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        productRandomKey = saveCurrentDate;

        final StorageReference filePath = userImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);
        loadingBar.show();

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e)
            {
                String message = e.toString();
                Toast.makeText(profile.this, "Error: "+ message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(profile.this, "Your Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(profile.this, "got the Your image Url Successfully...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }
    private void SaveProductInfoToDatabase() {

        loadingBar.show();
        mchild = UsersRef.child(us.getUid());
        mname = mchild.child("user name");
        mphone = mchild.child("Phone No");
        maddress = mchild.child("Address");
        mimgurl = mchild.child("image");


        mname.setValue(u_name.toString());
        mphone.setValue(u_phone.toString());
        maddress.setValue(u_address.toString());
        mimgurl.setValue(downloadImageUrl.toString());

        loadingBar.dismiss();
        startActivity(new Intent(profile.this,profile_ifexist.class));

    }


}
