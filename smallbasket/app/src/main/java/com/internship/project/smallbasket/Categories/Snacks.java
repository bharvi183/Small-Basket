package com.internship.project.smallbasket.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.internship.project.smallbasket.AllItems.Packetsnacks;
import com.internship.project.smallbasket.AllItems.Bakerysnacks;
import com.internship.project.smallbasket.Java_files.PagerAdapter;
import com.internship.project.smallbasket.R;

public class Snacks extends AppCompatActivity {


    private PagerAdapter viewpageradapter;
    private TabLayout tabLayout;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        tabLayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.pager);
        viewpageradapter = new PagerAdapter(getSupportFragmentManager());

        viewpageradapter.AddFragment(new Bakerysnacks(),"");
        viewpageradapter.AddFragment(new Packetsnacks(),"");
        viewpager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(viewpager);

        tabLayout.getTabAt(0).setText("Bakery Snacks");
        tabLayout.getTabAt(1).setText("Packet Snacks");
    }


}
