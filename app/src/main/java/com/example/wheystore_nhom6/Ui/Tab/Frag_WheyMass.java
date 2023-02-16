package com.example.wheystore_nhom6.Ui.Tab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.ViewPager_Adapter.ViewPager_Adapter_WheyMass;
import com.google.android.material.tabs.TabLayout;

public class Frag_WheyMass extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;



    public static Frag_WheyMass newInstance(String param1, String param2) {
        Frag_WheyMass fragment = new Frag_WheyMass();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_wheymass, container, false);
        viewPager = view.findViewById(R.id.wheyMass_ViewPager);
        tabLayout = view.findViewById(R.id.wheyMass_TabLayout);
        ViewPager_Adapter_WheyMass viewPager_adapter = new ViewPager_Adapter_WheyMass(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPager_adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}