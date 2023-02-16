package com.example.wheystore_nhom6.Ui.Tab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheystore_nhom6.DAO.sanPham_Dao;
import com.example.wheystore_nhom6.DAO.sanPham_thongKe_DAO;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.Model.sanPham_thongKe;
import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.ViewPager_Adapter.sanPham_thongKe_Adapter;
import com.example.wheystore_nhom6.ViewPager_Adapter.sanPham_thongKe_thongTin_Adapter;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Acti_thongKe extends AppCompatActivity {

    List<sanPham_thongKe> _list;
    List<sanPham> _listSanPham;

    sanPham_thongKe thongKe;
    FirebaseFirestore firestore;
    sanPham_thongKe_Adapter adapter_doanhThu;
    sanPham_thongKe_thongTin_Adapter adapter_sanPham;

    sanPham_thongKe_DAO thongKeDao;
    sanPham_Dao sanPham_dao;

    RecyclerView recyclerView_sanPham_xuat, recyclerView_sanPham;
    TextView all,view,coin,thongKe_giaoDich;

    // thong ke
    TextView tv_doanhthu, tv_ngaybdau, tv_ngaykthuc;
    ImageView img_ngaybdau, img_ngaykthuc;
    Button btn_thongke;
    RecyclerView recyclerView_doanhThu;
    int mYear, mMonth, mDay;
    Date ngaybdau, ngaykthuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_thong_ke);
        _list = new ArrayList<>();
        thongKe = new sanPham_thongKe();
        firestore = FirebaseFirestore.getInstance();
        adapter_doanhThu = new sanPham_thongKe_Adapter(_list,Acti_thongKe.this);
        thongKeDao = new sanPham_thongKe_DAO();
        recyclerView_sanPham_xuat = findViewById(R.id.recyclerView_Acti_thongKe_doanhThu);
        LinearLayoutManager manager = new LinearLayoutManager(Acti_thongKe.this,RecyclerView.VERTICAL,false);
        recyclerView_sanPham_xuat.setLayoutManager(manager);
        recyclerView_sanPham_xuat.setAdapter(adapter_doanhThu);

        sanPham_dao = new sanPham_Dao();
        _listSanPham = new ArrayList<>();
        recyclerView_sanPham = findViewById(R.id.recyclerView_Acti_thongKe_sanPham);
        adapter_sanPham = new sanPham_thongKe_thongTin_Adapter(_listSanPham,Acti_thongKe.this);
        LinearLayoutManager manager_sanPham = new LinearLayoutManager(Acti_thongKe.this,RecyclerView.VERTICAL,false);
        recyclerView_sanPham.setLayoutManager(manager_sanPham);
        recyclerView_sanPham.setAdapter(adapter_sanPham);


        all = findViewById(R.id.tv_Acti_thongKe_coinMax);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setBackgroundResource(R.drawable.boder_info);
                view.setBackgroundResource(R.drawable.boder_thongke_old);
                coin.setBackgroundResource(R.drawable.boder_thongke_old);
                thongKe_giaoDich.setBackgroundResource(R.drawable.boder_thongke_old);
                recyclerView_sanPham.setVisibility(View.VISIBLE);
                recyclerView_sanPham_xuat.setVisibility(View.GONE);
                recyclerView_doanhThu.setVisibility(View.GONE);
                hint();
                readAll();
            }
        });
        view = findViewById(R.id.tv_Acti_thongKe_view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setBackgroundResource(R.drawable.boder_thongke_old);
                view.setBackgroundResource(R.drawable.boder_info);
                coin.setBackgroundResource(R.drawable.boder_thongke_old);
                thongKe_giaoDich.setBackgroundResource(R.drawable.boder_thongke_old);
                recyclerView_sanPham.setVisibility(View.VISIBLE);
                recyclerView_sanPham_xuat.setVisibility(View.GONE);
                recyclerView_doanhThu.setVisibility(View.GONE);
                hint();
                readView();
            }
        });
        coin = findViewById(R.id.tv_Acti_thongKe_mua);
        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setBackgroundResource(R.drawable.boder_thongke_old);
                view.setBackgroundResource(R.drawable.boder_thongke_old);
                coin.setBackgroundResource(R.drawable.boder_info);
                thongKe_giaoDich.setBackgroundResource(R.drawable.boder_thongke_old);
                recyclerView_sanPham.setVisibility(View.GONE);
                recyclerView_sanPham_xuat.setVisibility(View.VISIBLE);
                recyclerView_doanhThu.setVisibility(View.GONE);
                hint();
                readCoin();
            }
        });

        thongKe_giaoDich = findViewById(R.id.tv_Acti_thongKe_HoaDon);
        thongKe_giaoDich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setBackgroundResource(R.drawable.boder_thongke_old);
                view.setBackgroundResource(R.drawable.boder_thongke_old);
                coin.setBackgroundResource(R.drawable.boder_thongke_old);
                thongKe_giaoDich.setBackgroundResource(R.drawable.boder_info);
                unHint();
            }
        });

        tv_doanhthu = findViewById(R.id.tv_doanhthu);
        tv_ngaybdau = findViewById(R.id.tv_Acti_thongKe_thongkeDay1);
        tv_ngaykthuc = findViewById(R.id.tv_Acti_thongKe_thongkeDay2);
        img_ngaybdau = findViewById(R.id.img_ngaybdau);
        img_ngaykthuc = findViewById(R.id.img_ngaykthuc);
        btn_thongke = findViewById(R.id.btn_Acti_thongKe_thongKeDoanhThu);
        recyclerView_doanhThu = findViewById(R.id.RecyclerView_thongKe);
        LinearLayoutManager manager_tk = new LinearLayoutManager(Acti_thongKe.this, RecyclerView.VERTICAL, false);
        recyclerView_doanhThu.setLayoutManager(manager_tk);

        img_ngaybdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(Acti_thongKe.this, 0, setngaybdau, mYear, mMonth, mDay);
                d.show();
            }
        });
        tv_ngaybdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(Acti_thongKe.this, 0, setngaybdau, mYear, mMonth, mDay);
                d.show();
            }
        });
        img_ngaykthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(Acti_thongKe.this, 0, setngaykthuc, mYear, mMonth, mDay);
                d.show();
            }
        });
        tv_ngaykthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(Acti_thongKe.this, 0, setngaykthuc, mYear, mMonth, mDay);
                d.show();
            }
        });
        btn_thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ngaybdau == null || ngaykthuc == null) {
                    Toast.makeText(Acti_thongKe.this, "Bạn cần chọn ngày để thống kê doanh thu", Toast.LENGTH_SHORT).show();
                } else if (ngaybdau.compareTo(ngaykthuc) > 0) {
                    Toast.makeText(Acti_thongKe.this, "Ngày bắt đầu phải là ngày trước ngày kết thúc", Toast.LENGTH_SHORT).show();
                } else {
                    thongKeDao.readDataThongKe_GiaoDich(ngaybdau, ngaykthuc, tv_doanhthu,Acti_thongKe.this, recyclerView_doanhThu);
                }
            }
        });
        hint();
        readAll();

    }
    DatePickerDialog.OnDateSetListener setngaybdau = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfYear) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfYear;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            tv_ngaybdau.setText("Ngày " + mDay + "   Tháng " + (mMonth + 1) + "   Năm " + mYear);
            ngaybdau = c.getTime();
        }
    };
    DatePickerDialog.OnDateSetListener setngaykthuc = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfYear) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfYear;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            tv_ngaykthuc.setText("Ngày " + mDay + "   Tháng " + (mMonth + 1) + "   Năm " + mYear);
            ngaykthuc = c.getTime();
        }
    };

    public void readAll(){
        firestore.collection("SanPham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                sanPham_dao.readDataThongKe(_listSanPham,adapter_sanPham,"coin");
            }
        });
    }

    public void readView(){
        firestore.collection("SanPham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                sanPham_dao.readDataThongKe(_listSanPham,adapter_sanPham,"view");
            }
        });
    }

    public void readCoin(){
        firestore.collection("ThongKe").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                thongKeDao.readDataThongKe(_list, adapter_doanhThu);
                recyclerView_sanPham.setVisibility(View.GONE);
            }
        });
    }

    public void hint(){
        tv_doanhthu.setVisibility(View.GONE);
        tv_ngaybdau.setVisibility(View.GONE);
        tv_ngaykthuc.setVisibility(View.GONE);
        img_ngaybdau.setVisibility(View.GONE);
        img_ngaykthuc.setVisibility(View.GONE);
        btn_thongke.setVisibility(View.GONE);
    }
    public void unHint(){
        tv_doanhthu.setVisibility(View.VISIBLE);
        tv_ngaybdau.setVisibility(View.VISIBLE);
        tv_ngaykthuc.setVisibility(View.VISIBLE);
        img_ngaybdau.setVisibility(View.VISIBLE);
        img_ngaykthuc.setVisibility(View.VISIBLE);
        btn_thongke.setVisibility(View.VISIBLE);
        recyclerView_doanhThu.setVisibility(View.VISIBLE);
        recyclerView_sanPham.setVisibility(View.GONE);
        recyclerView_sanPham_xuat.setVisibility(View.GONE);
    }
}