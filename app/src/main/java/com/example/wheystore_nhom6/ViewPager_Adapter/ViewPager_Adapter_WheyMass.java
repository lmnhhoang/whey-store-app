package com.example.wheystore_nhom6.ViewPager_Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.wheystore_nhom6.Ui.Tab.WheyMass.Frag_mass;
import com.example.wheystore_nhom6.Ui.Tab.WheyMass.Frag_whey;

public class ViewPager_Adapter_WheyMass extends FragmentStatePagerAdapter {

    public ViewPager_Adapter_WheyMass(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 : return new Frag_whey();
            case 1 : return new Frag_mass();
            default: return  new Frag_whey();
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
            case 0 : title="Whey";
            break;
            case 1 : title ="Mass";
            break;
        }
        return title;
    }
}
