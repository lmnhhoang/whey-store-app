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

public class Acti_nhanHangThanhCong extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    List<hoaDon> _list;
    hoaDon_Dao hoaDon_dao;
    choXacNhan_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_nhan_hang_thanh_cong);
        recyclerView = findViewById(R.id.recyclerView_Acti_nhanHangThanhCong);
        firebaseFirestore = FirebaseFirestore.getInstance();
        _list = new ArrayList<>();
        hoaDon_dao = new hoaDon_Dao();
        adapter = new choXacNhan_Adapter(_list,Acti_nhanHangThanhCong.this);
        LinearLayoutManager manager= new LinearLayoutManager(Acti_nhanHangThanhCong.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        READ();
    }
    public void READ(){
        firebaseFirestore.collection("HoaDon").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                hoaDon_dao.read_choXacNhan(_list,adapter,"Thành Công");
            }
        });
    }
}