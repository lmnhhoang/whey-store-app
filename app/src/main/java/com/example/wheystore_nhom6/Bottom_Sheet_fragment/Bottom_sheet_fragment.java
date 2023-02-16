package com.example.wheystore_nhom6.Bottom_Sheet_fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wheystore_nhom6.DAO.User_Dao;
import com.example.wheystore_nhom6.DAO.sanPham_Dao;
import com.example.wheystore_nhom6.DAO.sanPham_thongKe_DAO;
import com.example.wheystore_nhom6.Model.hoaDon;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.Model.sanPham_thongKe;
import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.Ui.View.Acti_ViewPost;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Date;


public class Bottom_sheet_fragment extends BottomSheetDialogFragment {
    User_Dao user_dao;
    sanPham_Dao sanPham_dao;
    sanPham_thongKe sanPham_thongKe;
    sanPham_thongKe_DAO thongKe_Dao;
    TextView title,coin,send,tang,giam,values,coinSum;
    ImageView img;
    sanPham _sp;
    int value;
    DecimalFormat formatter;
    public  static Bottom_sheet_fragment newInstance(sanPham sp){
        Bottom_sheet_fragment bottom_sheet_fragment = new Bottom_sheet_fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",sp);
        bottom_sheet_fragment.setArguments(bundle);
        return bottom_sheet_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundleRecevi = getArguments();
        if (bundleRecevi!=null){
            _sp = (sanPham) bundleRecevi.get("data");
        }
        user_dao = new User_Dao();
        sanPham_dao = new sanPham_Dao();
        sanPham_thongKe = new sanPham_thongKe();
        thongKe_Dao=  new sanPham_thongKe_DAO();
        formatter = new DecimalFormat("###,###,###");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet,null);
        dialog.setContentView(view);
        initView(view);
        setData();
        value = 1;
        giam.setEnabled(false);

        giam.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                tang.setEnabled(true);
                value = value-1;
                values.setText(String.valueOf(value));
                coinSum.setText(formatter.format(Integer.parseInt(_sp.getCoin())*value) +" vnd");
                if (value==0||value<0){
                    giam.setEnabled(false);
                    send.setEnabled(false);
                }
            }
        });

        tang.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                giam.setEnabled(true);
                send.setEnabled(true);
                value = value+1;
                coinSum.setText(formatter.format(Integer.parseInt(_sp.getCoin())*value) +" vnd");
                if (value==Integer.parseInt(_sp.getCount())){
                    tang.setEnabled(false);
                }
                values.setText(String.valueOf(value));
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                user_dao.checkInfo(_sp,value,date, getContext());
                dialog.dismiss();
            }
        });
        return dialog;
    }
    private void initView(View view){
        img = view.findViewById(R.id.img_bottomSheet_sanPham);
        title = view.findViewById(R.id.tv_bottomSheet_title);
        coin = view.findViewById(R.id.tv_bottomSheet_coin);
        tang = view.findViewById(R.id.tv_bottomSheet_tang);
        values = view.findViewById(R.id.tv_bottomSheet_values);
        giam = view.findViewById(R.id.tv_bottomSheet_giam);
        coinSum = view.findViewById(R.id.tv_bottomSheet_coinSum);
        send = view.findViewById(R.id.tv_bottomSheet_send);
    }
    @SuppressLint("SetTextI18n")
    private void setData(){
        if (_sp==null){
            return;
        }
        Picasso.get().load(_sp.getImg1()).centerCrop().resize(100,100).into(img);
        title.setText(_sp.getName());
        coin.setText(formatter.format(Integer.parseInt(_sp.getCoin()))  +" vnd");
        coinSum.setText(formatter.format(Integer.parseInt(_sp.getCoin())) +" vnd");



    }
}
