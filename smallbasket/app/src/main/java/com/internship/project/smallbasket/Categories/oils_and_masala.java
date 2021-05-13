package com.internship.project.smallbasket.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.internship.project.smallbasket.AllItems.Masalas;
import com.internship.project.smallbasket.AllItems.oils;
import com.internship.project.smallbasket.Java_files.PagerAdapter;
import com.internship.project.smallbasket.R;

public class oils_and_masala extends AppCompatActivity {


    private PagerAdapter viewpageradapter;
    private TabLayout tabLayout;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oils_and_masala);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.pager);
        viewpageradapter = new PagerAdapter(getSupportFragmentManager());

        viewpageradapter.AddFragment(new oils(),"");
        viewpageradapter.AddFragment(new Masalas(),"");
        viewpager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(viewpager);

        tabLayout.getTabAt(0).setText("Oil");
        tabLayout.getTabAt(1).setText("Masala");
    }


}
