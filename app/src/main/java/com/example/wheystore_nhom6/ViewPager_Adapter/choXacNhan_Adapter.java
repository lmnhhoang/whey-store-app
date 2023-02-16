package com.example.wheystore_nhom6.ViewPager_Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheystore_nhom6.DAO.donHang_Dao;
import com.example.wheystore_nhom6.DAO.hoaDon_Dao;
import com.example.wheystore_nhom6.DAO.sanPham_Dao;
import com.example.wheystore_nhom6.DAO.sanPham_thongKe_DAO;
import com.example.wheystore_nhom6.Model.hoaDon;
import com.example.wheystore_nhom6.Model.sanPham_thongKe;
import com.example.wheystore_nhom6.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class choXacNhan_Adapter extends RecyclerView.Adapter<choXacNhan_Adapter.ViewHoder> {

    List<hoaDon> _list;
    hoaDon_Dao hoaDon_dao;
    donHang_Dao donHang_dao;
    sanPham_Dao sanPham_dao;
    sanPham_thongKe_DAO  thongKe_Dao;
    Context context;
    hoaDon _hd;
    DecimalFormat formatter;

    public choXacNhan_Adapter(List<hoaDon> _list, Context context) {
        this._list = _list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status_hoadon,parent,false);
        return new ViewHoder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {
        hoaDon_dao = new hoaDon_Dao();
        donHang_dao = new donHang_Dao();
        sanPham_dao = new sanPham_Dao();
        thongKe_Dao = new sanPham_thongKe_DAO();
        //holder.delete.setVisibility(View.INVISIBLE);

        _hd = _list.get(position);
        hoaDon_dao.checkStatus(_hd.getId(),holder.delete,holder.success);
        Date dNow = _hd.getDate();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft =new SimpleDateFormat("dd.MM.yyyy");

        //hoaDon_dao.checkMuaSanPham(holder.coin);
        formatter = new DecimalFormat("###,###,###");

        holder.title.setText(_hd.getTitle());
        holder.coin.setText(formatter.format((Integer.parseInt(_hd.getCoin())*_hd.getValue()))+" vnd");
        holder.soLuong.setText("Số lượng : "+_hd.getValue());
        holder.date_sp.setText(ft.format(dNow));
        Picasso.get().load(_hd.getImg1()).resize(350,350).centerCrop().into(holder.img);
        holder.status.setText("Trạng Thái : "+_hd.getStatus());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _hd = _list.get(position);
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_delete);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button yes,no;
                TextView title;
                title = dialog.findViewById(R.id.tv_dialog_title);
                title.setText("Delete "+_hd.getTitle()+" ?");
                yes = dialog.findViewById(R.id.btn_dialog_yes);
                no = dialog.findViewById(R.id.btn_dialog_no);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hoaDon_dao.delete_hoaDon(_hd.getId(),context);
                        dialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        holder.success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _hd = _list.get(position);
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_thanhcong);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView title = dialog.findViewById(R.id.tv_dialog_thanhCong_title);
                title.setText("Thông Tin Đơn Hàng");
                Button yes = dialog.findViewById(R.id.item_btn_yes);
                Button no = dialog.findViewById(R.id.item_btn_no);
                yes.setText("Đã Nhận Được Đơn Hàng");
                no.setText("Chưa Nhận Được Đơn Hàng");
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hoaDon_dao.update_trangThai_donHang(_hd.getId(),"Thành Công");
                        dialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Quý Khách Vui Lòng Chờ Đến Khi Nhận Được Đơn Hàng", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (_list!=null){
            return _list.size();
        }
        return 0;
    }

    public static class ViewHoder extends RecyclerView.ViewHolder{

        TextView title,coin,soLuong,date_sp,status;
        ImageView img;
        ImageView delete,success;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.tv_item_status_hoaDon_title);
            coin= itemView.findViewById(R.id.tv_item_status_hoaDon_coin);
            soLuong= itemView.findViewById(R.id.tv_item_status_hoaDon_value);
            date_sp= itemView.findViewById(R.id.tv_item_status_hoaDon_date);
            img= itemView.findViewById(R.id.img_item_status_donHang);
            status= itemView.findViewById(R.id.tv_item_status_hoaDon_status);
            delete= itemView.findViewById(R.id.img_item_status_hoaDon_delete);
            success= itemView.findViewById(R.id.img_item_status_hoaDon_success);
        }
    }

}
