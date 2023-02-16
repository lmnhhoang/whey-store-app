package com.example.wheystore_nhom6.Ui.Tab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.wheystore_nhom6.DAO.User_Dao;
import com.example.wheystore_nhom6.DAO.sanPham_Dao;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.Ui.Acti_DonHang;
import com.example.wheystore_nhom6.ViewPager_Adapter.sanPham_Home_Adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Frag_home extends Fragment {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    List<sanPham> _list;
    sanPham _sp;
    sanPham_Dao _sp_Dao;
    User_Dao user_dao;


    sanPham_Home_Adapter adapter;
    RecyclerView recyclerView_SanPham;

    ImageView hoaDon, donHang,filter;
    TextView more,filter_tv;

    SearchView _SearchView;
    //add san pham
    FloatingActionButton newPostSanPham;


    public Frag_home() {
    }

    public static Frag_home newInstance(String param1, String param2) {
        Frag_home fragment = new Frag_home();
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
        View view =inflater.inflate(R.layout.fragment_frag_home, container, false);


        recyclerView_SanPham = view.findViewById(R.id.recyclerView_Frag_home_SanPham);

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);

        recyclerView_SanPham.setLayoutManager(manager);

        _sp_Dao = new sanPham_Dao();

        _sp = new sanPham();
        _list = new ArrayList<>();
        adapter = new sanPham_Home_Adapter(_list,view.getContext());
        recyclerView_SanPham.setAdapter(adapter);



        hoaDon = view.findViewById(R.id.img_Frag_home_gioHang);
        hoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Acti_HoaDon.class));
            }
        });
        donHang = view.findViewById(R.id.img_Frag_home_donHang);
        donHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Acti_DonHang.class));
            }
        });

        _SearchView = view.findViewById(R.id.searchView_Frag_home_find);
        _SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText.toString().trim());
                return false;
            }
        });
        more = view.findViewById(R.id.tv_Frag_home_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });
        filter = view.findViewById(R.id.img_home_filter);
        filter_tv = view.findViewById(R.id.tv_Frag_home_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFiler();
            }
        });

        newPostSanPham = view.findViewById(R.id.btn_managerSanPham_FloatingActionButton);
        newPostSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Acti_newPost.class));
            }
        });
        user_dao = new User_Dao();
        if (user_dao.check_admin()==false){
            newPostSanPham.setVisibility(View.GONE);
            donHang.setVisibility(View.GONE);
        }

        read();
        return view;
    }


    public void read(){
        firebaseFirestore.collection("SanPham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                _sp_Dao.readDataHome(_list,adapter);
            }
        });
    }

    public void read_more(String type){
        firebaseFirestore.collection("SanPham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                _sp_Dao.read_More(_list,adapter,type);
            }
        });
    }
    public void read_more_filter(String gia){
        firebaseFirestore.collection("SanPham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                _sp_Dao.readDataHome_filter(_list,adapter,gia);
            }
        });
    }

    private void showMenu(){
        PopupMenu popupMenu = new PopupMenu(getContext(),more);
        popupMenu.getMenuInflater().inflate(R.menu.home_more,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mn_all: more.setText("Tất Cả");
                    filter_tv.setText("");
                        read();
                        break;
                    case R.id.mn_Whey: more.setText("Whey");
                        filter_tv.setText("");
                        read_more("whey");
                        break;
                    case R.id.mn_Mass: more.setText("Mass");
                        filter_tv.setText("");
                        read_more("mass");
                        break;
                    case R.id.mn_Bcaa: more.setText("Bcaa");
                        filter_tv.setText("");
                        read_more("bcaa");
                        break;
                    case R.id.mn_Eaa : more.setText("Eaa");
                        filter_tv.setText("");
                        read_more("eaa");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
    private void menuFiler(){
        PopupMenu popupMenu = new PopupMenu(getContext(),filter_tv);
        popupMenu.getMenuInflater().inflate(R.menu.home_filter,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mn_min : filter_tv.setText("Giá Giảm");
                        more.setText("Tất Cả");
                        read_more_filter("giam");
                        break;
                    case R.id.mn_max : filter_tv.setText("Giá Tăng");
                        more.setText("Tất Cả");
                        read_more_filter("tang");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}