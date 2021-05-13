package com.internship.project.smallbasket.AllItems;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.internship.project.smallbasket.R;

public class PhotoViewerActivity extends AppCompatActivity {

    ImageView full;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);

        full = (ImageView) findViewById(R.id.imageView3);
        String url = getIntent().getStringExtra("image");
        full.setImageURI(Uri.parse(url));

    }
}
