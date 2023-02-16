package com.example.wheystore_nhom6.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.ViewPager_Adapter.ViewPager_Adapter_hello;
import com.google.android.material.tabs.TabLayout;

import android.os.Bundle;

public class Acti_hello extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_hello);

        tabLayout = findViewById(R.id.TabLayout_hello);
        viewPager = findViewById(R.id.ViewPager_hello);
        ViewPager_Adapter_hello viewPager_Adapter = new ViewPager_Adapter_hello(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPager_Adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}