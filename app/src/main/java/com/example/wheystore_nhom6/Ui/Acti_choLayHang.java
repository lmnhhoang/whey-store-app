package com.example.wheystore_nhom6.Ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.wheystore_nhom6.DAO.donHang_Dao;
import com.example.wheystore_nhom6.DAO.hoaDon_Dao;
import com.example.wheystore_nhom6.Model.hoaDon;
import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.ViewPager_Adapter.choXacNhan_Adapter;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Acti_choLayHang extends AppCompatActivity {

    List<hoaDon> _list;
    choXacNhan_Adapter adapter;
    RecyclerView recyclerView;
    hoaDon_Dao hoaDon_dao;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_cho_lay_hang);

        _list = new ArrayList<>();
        adapter = new choXacNhan_Adapter(_list,Acti_choLayHang.this);
        recyclerView = findViewById(R.id.recyclerView_Acti_choLayHang);
        LinearLayoutManager manager = new LinearLayoutManager(Acti_choLayHang.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        hoaDon_dao = new hoaDon_Dao();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView.setAdapter(adapter);
        READ();

    }

    private void READ() {
        firebaseFirestore.collection("HoaDon").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                hoaDon_dao.read_choXacNhan(_list,adapter,"Chờ Lấy Hàng");
            }
        });
    }

}