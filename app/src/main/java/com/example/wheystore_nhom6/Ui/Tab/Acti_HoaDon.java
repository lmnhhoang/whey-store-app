package com.example.wheystore_nhom6.Ui.Tab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.wheystore_nhom6.DAO.hoaDon_Dao;
import com.example.wheystore_nhom6.Model.hoaDon;
import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.ViewPager_Adapter.hoaDon_Adapter;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Acti_HoaDon extends AppCompatActivity {

    List<hoaDon> _list;
    hoaDon _hd;
    hoaDon_Adapter adapter;
    RecyclerView recyclerView;
    hoaDon_Dao _hoaDon_dao;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_hoa_don);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerView_Acti_HoaDon);
        LinearLayoutManager manager = new LinearLayoutManager(Acti_HoaDon.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        _list = new ArrayList<>();
        _hd = new hoaDon();
        _hoaDon_dao = new hoaDon_Dao();

        adapter = new hoaDon_Adapter(_list,Acti_HoaDon.this);
        recyclerView.setAdapter(adapter);
        Read_chuaDatHang("0");
    }

    private void Read_chuaDatHang(String type) {
        firebaseFirestore.collection("HoaDon").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                _hoaDon_dao.read_HoaDon_tuyChon(_list,adapter,type);

            }
        });
    }
}