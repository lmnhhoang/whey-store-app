package com.example.wheystore_nhom6.ViewPager_Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.wheystore_nhom6.Ui.hello.Frag_login;
import com.example.wheystore_nhom6.Ui.hello.Frag_register;

public class ViewPager_Adapter_hello extends FragmentStatePagerAdapter {

    public ViewPager_Adapter_hello(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new Frag_login();

            case 1 : return new Frag_register();

            default: return new Frag_login();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position){
            case 0 : title ="Sign In";
            break;
            case 1 : title="Sign Up";
            break;
        }
        return title;
    }
}
