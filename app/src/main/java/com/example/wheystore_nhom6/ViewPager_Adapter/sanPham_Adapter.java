package com.example.wheystore_nhom6.ViewPager_Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheystore_nhom6.DAO.User_Dao;
import com.example.wheystore_nhom6.DAO.sanPham_Dao;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.Ui.Tab.Acti_EditPost;
import com.example.wheystore_nhom6.Ui.View.Acti_ViewPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class sanPham_Adapter extends RecyclerView.Adapter<sanPham_Adapter.ViewHoder>{

    List<sanPham> _list ;
    sanPham _sp;
    Context context;


    public sanPham_Adapter(List<sanPham> _list, Context context) {
        this._list = _list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {

        _sp = _list.get(position);



        if (_sp==null){
            return;
        }
        if (_sp.getElement().toString().length()>22){
            holder.info.setText(_sp.getElement().substring(0,15)+"...more");
        }else{
            holder.info.setText(_sp.getElement());
        }

        if (_sp.getName().toString().length()>22){
            holder.title.setText(_sp.getName().substring(0,22)+"...");
        }else{
            holder.title.setText(_sp.getName());
        }

        if (_sp.getCount().equals("0")){
            holder.hetHang.setVisibility(View.VISIBLE);
        }else{
            holder.hetHang.setVisibility(View.GONE);
        }


        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.coin.setText(formatter.format(Integer.parseInt(_sp.getCoin()))+" vnd");
        Picasso.get().load(_sp.getImg1()).into(holder.img);

        if (_sp.getValue().equals("1")){
            holder.value2.setVisibility(View.GONE);
            holder.value3.setVisibility(View.GONE);
            holder.value4.setVisibility(View.GONE);
            holder.value5.setVisibility(View.GONE);
        }
        if (_sp.getValue().equals("2")){
            holder.value3.setVisibility(View.GONE);
            holder.value4.setVisibility(View.GONE);
            holder.value5.setVisibility(View.GONE);
        }
        if (_sp.getValue().equals("3")){
            holder.value4.setVisibility(View.GONE);
            holder.value5.setVisibility(View.GONE);
        }
        if (_sp.getValue().equals("4")){
            holder.value5.setVisibility(View.GONE);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _sp = _list.get(position);
                OnClick(_sp);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                _sp= _list.get(position);
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.item_show);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView imageView = dialog.findViewById(R.id.img_view_item);
                Picasso.get().load(_sp.getImg1()).resize(350,350).centerCrop().into(imageView);
                dialog.show();
                return false;
            }
        });

    }

    public void OnClick(sanPham sp){
        Intent intent = new Intent(context, Acti_ViewPost.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",sp);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (_list!=null){
            return _list.size();
        }
        return 0;
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        TextView title,info,coin;
        ImageView img;
        ImageView hetHang;
        ImageView value1,value2,value3,value4,value5;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout_item);
            title =itemView.findViewById(R.id.tv_item_title);
            info =itemView.findViewById(R.id.tv_item_info);
            coin =itemView.findViewById(R.id.tv_item_coin);
            img = itemView.findViewById(R.id.img_item_sanPham);
            hetHang = itemView.findViewById(R.id.img_item_hethang);

            value1 = itemView.findViewById(R.id.img_type_1);
            value2 = itemView.findViewById(R.id.img_type_2);
            value3 = itemView.findViewById(R.id.img_type_3);
            value4 = itemView.findViewById(R.id.img_type_4);
            value5 = itemView.findViewById(R.id.img_type_5);
        }
    }
}
