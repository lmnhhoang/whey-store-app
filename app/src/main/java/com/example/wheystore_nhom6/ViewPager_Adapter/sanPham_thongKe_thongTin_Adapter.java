package com.example.wheystore_nhom6.ViewPager_Adapter;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheystore_nhom6.DAO.sanPham_Dao;
import com.example.wheystore_nhom6.DAO.sanPham_thongKe_DAO;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class sanPham_thongKe_thongTin_Adapter extends RecyclerView.Adapter<sanPham_thongKe_thongTin_Adapter.ViewHoder> {

    List<sanPham> _list;
    sanPham _sp;
    sanPham_Dao _sp_Dao;
    Context context;

    public sanPham_thongKe_thongTin_Adapter(List<sanPham> _list, Context context) {
        this._list = _list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongke,parent,false);
        return new ViewHoder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {
        _sp = _list.get(position);
        _sp_Dao = new sanPham_Dao();

        SimpleDateFormat ft =new SimpleDateFormat("dd.MM.yyyy");

        Log.e("TAG", "onBindViewHolder: "+position );

        if (position==0){
            holder.stt.setTextColor(Color.rgb(255,95,1));
        }else if (position==1){
            holder.stt.setTextColor(Color.rgb(247,154,15));
        }else if (position==2){
            holder.stt.setTextColor(Color.rgb(242,185,152));
        }else{
            holder.stt.setTextColor(Color.rgb(169,169,169));
        }

        holder.stt.setText(String.valueOf(position+1));

        Picasso.get().load(_sp.getImg1()).centerCrop().resize(350,350).into(holder.img);
        holder.title.setText(_sp.getName());
        holder.nhap.setText("Số lượng : "+_sp.getCount());
        holder.xuat.setText("Số lượng xuất : "+_sp.getXuat());
        holder.coin.setText("Giá : "+_sp.getCoin());
        holder.view.setText("Số lượt tiếp cận : "+_sp.getView());

    }

    @Override
    public int getItemCount() {
        if (_list!=null){
            return _list.size();
        }
        return 0;
    }

    public class ViewHoder extends RecyclerView.ViewHolder{

        TextView stt,title,nhap,xuat,view,coin;
        ImageView img;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            stt = itemView.findViewById(R.id.tv_itemThongKe_id);
            title = itemView.findViewById(R.id.tv_itemThongKe_name);
            img = itemView.findViewById(R.id.tv_itemThongKe_img);
            xuat = itemView.findViewById(R.id.tv_itemThongKe_xuat);
            nhap = itemView.findViewById(R.id.tv_itemThongKe_nhap);
            view = itemView.findViewById(R.id.tv_itemThongKe_view);
            coin = itemView.findViewById(R.id.tv_itemThongKe_coin);
        }
    }
}
