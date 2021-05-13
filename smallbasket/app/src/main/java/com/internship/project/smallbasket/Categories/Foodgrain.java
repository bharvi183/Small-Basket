package com.internship.project.smallbasket.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.internship.project.smallbasket.AllItems.Unpacked;
import com.internship.project.smallbasket.AllItems.Packed;
import com.internship.project.smallbasket.Java_files.PagerAdapter;
import com.internship.project.smallbasket.R;

public class Foodgrain extends AppCompatActivity {


    private PagerAdapter viewpageradapter;
    private TabLayout tabLayout;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodgrain);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.pager);
        viewpageradapter = new PagerAdapter(getSupportFragmentManager());

        viewpageradapter.AddFragment(new Packed(),"");
        viewpageradapter.AddFragment(new Unpacked(),"");
        viewpager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(viewpager);

        tabLayout.getTabAt(0).setText("packed");
        tabLayout.getTabAt(1).setText("Unpacked");
    }


}
