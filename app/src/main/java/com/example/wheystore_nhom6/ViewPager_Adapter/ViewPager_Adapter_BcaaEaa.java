package com.example.wheystore_nhom6.ViewPager_Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.wheystore_nhom6.Ui.Tab.BcaaEaa.Frag_Bcaa;
import com.example.wheystore_nhom6.Ui.Tab.BcaaEaa.Frag_Eaa;

public class ViewPager_Adapter_BcaaEaa extends FragmentStatePagerAdapter {

    public ViewPager_Adapter_BcaaEaa(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 : return new Frag_Bcaa();
            case 1 : return new Frag_Eaa();
            default: return  new Frag_Bcaa();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0 : title="Bcaa";
            break;
            case 1 : title ="Eaa";
            break;
        }
        return title;
    }
}
