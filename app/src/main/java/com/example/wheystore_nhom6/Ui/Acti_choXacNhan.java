package com.example.wheystore_nhom6.Ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

public class Acti_choXacNhan extends AppCompatActivity {

    choXacNhan_Adapter adapter;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    hoaDon_Dao hoaDon_dao;
    List<hoaDon> _list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_cho_xac_nhan);

        recyclerView = findViewById(R.id.recyclerView_Acti_choXacNhan);
        firebaseFirestore = FirebaseFirestore.getInstance();
        LinearLayoutManager manager = new LinearLayoutManager(Acti_choXacNhan.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        _list = new ArrayList<>();
        adapter = new choXacNhan_Adapter(_list,Acti_choXacNhan.this);
        hoaDon_dao = new hoaDon_Dao();
        recyclerView.setAdapter(adapter);
        READ();
    }

    private void READ() {
        firebaseFirestore.collection("HoaDon").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                hoaDon_dao.read_choXacNhan(_list,adapter,"Chờ Xác Nhận");
            }
        });
    }
}