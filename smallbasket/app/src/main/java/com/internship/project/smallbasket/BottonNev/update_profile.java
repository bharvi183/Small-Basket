package com.internship.project.smallbasket.BottonNev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.internship.project.smallbasket.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class update_profile extends AppCompatActivity {

    Button done, cancle ;
    private Uri ImageUri;
    String username;
    EditText name,phone,address;
    String u_name , u_address , u_phone ,saveCurrentDate;
    private ProgressDialog loadingBar;
    private String downloadImageUrl ,productRandomKey;
    private StorageReference userImagesRef;
    ImageView setuserphoto;
    private static final int GalleryPick = 1;
    private DatabaseReference UsersRef ,mname , mphone ,maddress, mimgurl , mchild ,mcheck;
    FirebaseUser us = FirebaseAuth.getInstance().getCurrentUser();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);


        cancle = (Button) findViewById(R.id.cancle_button);
        done = (Button)findViewById(R.id.done_button);
        name = (EditText) findViewById(R.id.setuser_name);
        phone = (EditText) findViewById(R.id.setuser_phone);
        address = (EditText) findViewById(R.id.setuser_address);
        loadingBar = new ProgressDialog(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setuserphoto = (ImageView) findViewById(R.id.setuser_photo);

        userImagesRef = FirebaseStorage.getInstance().getReference().child("user Images");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("USERS");


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username = name.getText().toString();
                ValidateProductData();

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(update_profile.this,profile_ifexist.class));
            }
        });

        setuserphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });


    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            setuserphoto.setImageURI(ImageUri);
        }
    }


    private void ValidateProductData()
    {
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

    private void StoreProductInformation()
    {
        loadingBar.setMessage("Loading...");
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
                Toast.makeText(update_profile.this, "Error: "+ message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(update_profile.this, "Your Image changed Successfully...", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(update_profile.this, "got the Your image Url Successfully...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase()
    {

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
        startActivity(new Intent(update_profile.this,profile_ifexist.class));

    }


}
