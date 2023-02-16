package com.example.wheystore_nhom6.Ui.Tab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wheystore_nhom6.DAO.User_Dao;
import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.Ui.Acti_choLayHang;
import com.example.wheystore_nhom6.Ui.Acti_choXacNhan;
import com.example.wheystore_nhom6.Ui.Acti_donBiHuy;
import com.example.wheystore_nhom6.Ui.Acti_nhanHangThanhCong;
import com.example.wheystore_nhom6.Ui.Acti_setting;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Frag_Profile extends Fragment {

    TextView name,infoMember,setting;
    ImageView choXacNhan,choLayHang,donBiHuy,thongKe,thanhCong;
    User_Dao user_dao;

    public Frag_Profile() {
    }

    public static Frag_Profile newInstance(String param1, String param2) {
        Frag_Profile fragment = new Frag_Profile();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_frag__profile, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        anhXa(view);
        name.setText("Username : "+user.getDisplayName());

        thongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Acti_thongKe.class));
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Acti_setting.class));
            }
        });

        choXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Acti_choXacNhan.class));

            }
        });
        choLayHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Acti_choLayHang.class));

            }
        });
        donBiHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Acti_donBiHuy.class));

            }
        });
        thanhCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Acti_nhanHangThanhCong.class));

            }
        });

        User_Dao user_dao = new User_Dao();
        if (!user_dao.check_admin()){
            thongKe.setVisibility(View.GONE);
            infoMember.setText("Thành Viên");
        }
        infoMember.setText("Admin");
        return view;
    }
    public void anhXa(View view){
        infoMember = view.findViewById(R.id.tv_profile_member);
        thongKe = view.findViewById(R.id.tv_profile_thongKe);
        setting = view.findViewById(R.id.tv_profile_setting);
        choXacNhan = view.findViewById(R.id.tv_profile_choXacNhan);
        choLayHang = view.findViewById(R.id.tv_profile_choLayHang);
        donBiHuy = view.findViewById(R.id.tv_profile_donHangBiHuy);
        thanhCong = view.findViewById(R.id.tv_profile_thanhCong);
        user_dao= new User_Dao();
        name = view.findViewById(R.id.tv_profile_Name);
    }
}