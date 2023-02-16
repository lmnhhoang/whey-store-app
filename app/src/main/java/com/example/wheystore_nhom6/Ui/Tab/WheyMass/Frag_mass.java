package com.example.wheystore_nhom6.Ui.Tab.WheyMass;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wheystore_nhom6.DAO.sanPham_Dao;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.ViewPager_Adapter.sanPham_Adapter;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Frag_mass extends Fragment {


    List<sanPham> _list;
    sanPham_Dao _sp_Dao;
    sanPham_Adapter adapter ;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;

    public Frag_mass() {
    }

    public static Frag_mass newInstance(String param1, String param2) {
        Frag_mass fragment = new Frag_mass();
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
        View view=inflater.inflate(R.layout.fragment_frag_mass, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.recyclerView_Frag_mass);
        //LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        GridLayoutManager manager =new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(manager);
        _list = new ArrayList<>();
        adapter = new sanPham_Adapter(_list,view.getContext());
        recyclerView.setAdapter(adapter);
        _sp_Dao = new sanPham_Dao();


        READ();
        return view;
    }

    private void READ() {
        firebaseFirestore.collection("SanPham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                _sp_Dao.read_data_tab(_list,adapter,"mass");
            }
        });
    }
}