package com.example.wheystore_nhom6.ViewPager_Adapter;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class donHang_Adapter extends RecyclerView.Adapter<donHang_Adapter.ViewHoder> {

    List<hoaDon> _list;
    hoaDon_Dao hoaDon_dao;
    donHang_Dao donHang_dao;
    sanPham_Dao sanPham_dao;
    sanPham_thongKe_DAO thongKe_dao;
    sanPham_thongKe thongKe;
    Context context;
    hoaDon _hd;

    public donHang_Adapter(List<hoaDon> _list, Context context) {
        this._list = _list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new ViewHoder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {
        sanPham_dao = new sanPham_Dao();
        hoaDon_dao = new hoaDon_Dao();
        donHang_dao = new donHang_Dao();
        _hd = _list.get(position);

        Date dNow = _hd.getDate();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat ft =new SimpleDateFormat("dd.MM.yyyy");

        holder.nameUser.setText(_hd.getUserName());
        holder.adressUser.setText(_hd.getAdress());
        holder.phoneUser.setText(_hd.getPhone());
        holder.title.setText(_hd.getTitle());
        holder.coin.setText(_hd.getCoin());
        holder.count.setText(String.valueOf("Số lượng : "+_hd.getValue()));

        DecimalFormat formatter = new DecimalFormat("###,###,###");

        Log.e("TAG", "onBindViewHolder: "+formatter.format(Integer.parseInt(_hd.getCoin())) );
        holder.date.setText(ft.format(dNow));

        if (_hd.getStatus().equals("2")||_hd.getStatus().equals("3")){
            holder.delete.setVisibility(View.VISIBLE);
            holder.send.setVisibility(View.INVISIBLE);
            Log.e("TAG", "hien xoa: " );
        }else {
            holder.delete.setVisibility(View.INVISIBLE);
            holder.send.setVisibility(View.VISIBLE);
            Log.e("TAG", "an xoa: " );
        }
        int Sum = _hd.getValue()*Integer.parseInt(_hd.getCoin());

        Picasso.get().load(_hd.getImg1()).resize(350,350).centerCrop().into(holder.img);
        holder.coinRe.setText(formatter.format(Sum) +" vnd");
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
                        _hd = _list.get(position);
                        donHang_dao.delete_DonHang(_hd.getId(),v.getContext());
                        dialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "Thao tác bị hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _hd = _list.get(position);
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_thanhcong);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView title = dialog.findViewById(R.id.tv_dialog_thanhCong_title);
                title.setText("Xác Nhận Đơn Hàng");
                Button yes = dialog.findViewById(R.id.item_btn_yes);
                Button no = dialog.findViewById(R.id.item_btn_no);
                sanPham_dao.checkCount(_hd.getIdPost(),_hd.getValue(),yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _hd = _list.get(position);
                        //hoaDon_dao.delete_hoaDon(_hd.getId(), v.getContext());
                        donHang_dao.update_trangThai_donHang(_hd.getId(),"2");
                        hoaDon_dao.update_trangThai_donHang(_hd.getId(),"Chờ Lấy Hàng");
                        thongKe_dao = new sanPham_thongKe_DAO();
                        thongKe = new sanPham_thongKe("id_autorender",_hd.getImg1(),_hd.getTitle(),String.valueOf(_hd.getValue()),_hd.getCoin(),_hd.getDate());
                        thongKe_dao.create_thongKe(thongKe, v.getContext());
                        //update số lượng sản phẩm
                        sanPham_dao.update_sanPham_nhap(_hd.getIdPost(), _hd.getValue(), "count");
                        sanPham_dao.update_sanPham_xuat(_hd.getIdPost(), _hd.getValue(), "xuat");
                        dialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _hd = _list.get(position);
                        hoaDon_dao.update_trangThai_donHang(_hd.getId(),"Đơn Hàng Bị Hủy");
                        donHang_dao.update_trangThai_donHang(_hd.getId(),"3");
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

    public class ViewHoder extends RecyclerView.ViewHolder{

        TextView title,coin,nameUser,phoneUser,adressUser,count,coinRe,date;
        ImageView img;
        ImageView delete,send;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);

            nameUser= itemView.findViewById(R.id.tv_item_donHang_nameUser);
            phoneUser= itemView.findViewById(R.id.tv_item_donHang_phoneUser);
            adressUser= itemView.findViewById(R.id.tv_item_donHang_adressUser);
            title= itemView.findViewById(R.id.tv_item_donHang_title);
            coin= itemView.findViewById(R.id.tv_item_donHang_coin);
            count= itemView.findViewById(R.id.tv_item_donHang_count);
            coinRe= itemView.findViewById(R.id.img_item_donHang_coinRe);
            date= itemView.findViewById(R.id.img_item_donHang_date);
            img= itemView.findViewById(R.id.img_item_donHang);
            delete= itemView.findViewById(R.id.img_item_donHang_delete);
            send= itemView.findViewById(R.id.img_item_donHang_send);
        }
    }

}
