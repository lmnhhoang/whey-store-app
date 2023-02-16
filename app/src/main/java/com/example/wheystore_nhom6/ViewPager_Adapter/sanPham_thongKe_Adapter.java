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

import com.example.wheystore_nhom6.DAO.sanPham_thongKe_DAO;
import com.example.wheystore_nhom6.Model.sanPham_thongKe;
import com.example.wheystore_nhom6.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class sanPham_thongKe_Adapter extends RecyclerView.Adapter<sanPham_thongKe_Adapter.ViewHoder> {

    List<sanPham_thongKe> _list;
    sanPham_thongKe _sp;
    sanPham_thongKe_DAO thongKeDao;
    Context context;

    public sanPham_thongKe_Adapter(List<sanPham_thongKe> _list, Context context) {
        this._list = _list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongke_giatri,parent,false);
        return new ViewHoder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {


        _sp = _list.get(position);
        thongKeDao = new sanPham_thongKe_DAO();

        Date dNow = _sp.getDate();
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

        Picasso.get().load(_sp.getImg()).centerCrop().resize(350,350).into(holder.img);
        holder.title.setText(_sp.getTitle());
        holder.xuat.setText("Số lượng xuất : "+_sp.getXuat());
        holder.coin.setText("Giá : "+_sp.getCoin());
        holder.date.setText("Ngày: "+ft.format(dNow));

    }

    @Override
    public int getItemCount() {
        if (_list!=null){
            return _list.size();
        }
        return 0;
    }

    public class ViewHoder extends RecyclerView.ViewHolder{

        TextView stt,title,xuat,view,coin,date;
        ImageView img;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            stt = itemView.findViewById(R.id.tv_itemThongKe_id);
            title = itemView.findViewById(R.id.tv_itemThongKe_name);
            img = itemView.findViewById(R.id.tv_itemThongKe_img);
            xuat = itemView.findViewById(R.id.tv_itemThongKe_xuat);
            view = itemView.findViewById(R.id.tv_itemThongKe_view);
            coin = itemView.findViewById(R.id.tv_itemThongKe_coin);
            date = itemView.findViewById(R.id.tv_itemThongKe_date);
        }
    }
}
