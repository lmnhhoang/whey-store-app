package com.example.wheystore_nhom6.Ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wheystore_nhom6.DAO.User_Dao;
import com.example.wheystore_nhom6.DAO.donHang_Dao;
import com.example.wheystore_nhom6.Model.hoaDon;
import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.ViewPager_Adapter.donHang_Adapter;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Acti_DonHang extends AppCompatActivity {

    ArrayList<hoaDon> _list;
    hoaDon _hd;
    donHang_Adapter adapter;
    RecyclerView recyclerView;
    donHang_Dao donHang_dao;
    User_Dao user_dao;
    FirebaseFirestore firebaseFirestore;

    TextView thongTinDonHang, checkSanPham_daGiao,checkSanPham_huy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_don_hang);

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView_donHang);
        LinearLayoutManager manager = new LinearLayoutManager(Acti_DonHang.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        _list = new ArrayList<>();
        _hd = new hoaDon();
        donHang_dao = new donHang_Dao();
        adapter = new donHang_Adapter(_list,Acti_DonHang.this);
        recyclerView.setAdapter(adapter);
        user_dao = new User_Dao();



        thongTinDonHang = findViewById(R.id.tv_Acti_DonHang_thongTinDonHang);
        thongTinDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongTinDonHang.setBackgroundResource(R.drawable.boder_info);
                checkSanPham_daGiao.setBackgroundResource(R.drawable.boder_thongke_old);
                checkSanPham_huy.setBackgroundResource(R.drawable.boder_thongke_old);
                Read("0");
            }
        });
        checkSanPham_daGiao = findViewById(R.id.tv_Acti_DonHang_sanPhamDangGiao);
        checkSanPham_daGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongTinDonHang.setBackgroundResource(R.drawable.boder_thongke_old);
                checkSanPham_daGiao.setBackgroundResource(R.drawable.boder_info);
                checkSanPham_huy.setBackgroundResource(R.drawable.boder_thongke_old);
                Read("2");
            }
        });
        checkSanPham_huy = findViewById(R.id.tv_Acti_DonHang_sanPhamDaHuy);
        checkSanPham_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongTinDonHang.setBackgroundResource(R.drawable.boder_thongke_old);
                checkSanPham_daGiao.setBackgroundResource(R.drawable.boder_thongke_old);
                checkSanPham_huy.setBackgroundResource(R.drawable.boder_info);
                Read("3");
            }
        });

        Boolean checkAdmin = user_dao.check_admin();

        if (checkAdmin!=true){
            thongTinDonHang.setVisibility(View.GONE);
            checkSanPham_huy.setVisibility(View.GONE);
            checkSanPham_daGiao.setBackgroundResource(R.drawable.boder_info);
            Read("2");
            return;
        }
        //Read_SanPham_Admin();
        Read("0");
    }

    private void Read(String type) {
        firebaseFirestore.collection("DonHang").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                donHang_dao.read_HoaDon(_list,adapter,type);
            }
        });
    }


}