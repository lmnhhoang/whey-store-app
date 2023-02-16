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

public class Frag_whey extends Fragment {
    sanPham_Adapter adapter;
    ArrayList<sanPham> _list;
    sanPham_Dao _sp_Dao;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;


    public Frag_whey() {
    }

    public static Frag_whey newInstance(String param1, String param2) {
        Frag_whey fragment = new Frag_whey();
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
        View view =inflater.inflate(R.layout.fragment_frag_whey, container, false);
        firebaseFirestore =FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.frag_whey);
        _sp_Dao = new sanPham_Dao();
        _list = new ArrayList<>();
        adapter = new sanPham_Adapter(_list,view.getContext());

        //LinearLayoutManager Manager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        GridLayoutManager manager =new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        READ();
        return view;
    }

    private void READ(){
        firebaseFirestore.collection("SanPham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                _sp_Dao.read_data_tab(_list,adapter,"whey");
            }
        });
    }
}