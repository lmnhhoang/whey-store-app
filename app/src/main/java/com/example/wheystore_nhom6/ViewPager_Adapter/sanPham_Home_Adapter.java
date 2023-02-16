package com.example.wheystore_nhom6.ViewPager_Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wheystore_nhom6.DAO.User_Dao;
import com.example.wheystore_nhom6.DAO.sanPham_Dao;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.R;
import com.example.wheystore_nhom6.Ui.Tab.Acti_EditPost;
import com.example.wheystore_nhom6.Ui.View.Acti_ViewPost;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class sanPham_Home_Adapter extends RecyclerView.Adapter<sanPham_Home_Adapter.ViewHoder> implements Filterable {

    List<sanPham> _list;
    List<sanPham> _listOld;
    sanPham _sp;
    sanPham_Dao _sp_Dao;
    Context context;

    public sanPham_Home_Adapter(List<sanPham> _list, Context context) {
        this._list = _list;
        this.context = context;
        this._listOld = _list;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_sanpham,parent,false);
        return new ViewHoder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {
        _sp= _list.get(position);
        _sp_Dao = new sanPham_Dao();
        User_Dao user_dao = new User_Dao();
        if (!user_dao.check_admin()){
            holder.delete.setVisibility(View.GONE);
            holder.edit.setVisibility(View.GONE);
        }
        if (_sp.getName().toString().length()>22){
            holder.title.setText(_sp.getName().substring(0,22)+"...");
        }else{
            holder.title.setText(_sp.getName());
        }

        if (_sp.getElement().toString().length()>15){
            holder.element.setText(_sp.getElement().substring(0,15)+"...more");
        }else{
            holder.element.setText(_sp.getElement());
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.coin.setText(formatter.format(Integer.parseInt(_sp.getCoin()))+" vnd");

        Picasso.get().load(_sp.getImg1()).into(holder.img);

        if (_sp.getCount().equals("0")){
            holder.hetHang.setVisibility(View.VISIBLE);
        }else{
            holder.hetHang.setVisibility(View.GONE);
        }

        if (_sp.getValue().equals("1")){
            holder.type2.setVisibility(View.GONE);
            holder.type3.setVisibility(View.GONE);
            holder.type4.setVisibility(View.GONE);
            holder.type5.setVisibility(View.GONE);
        }
        if (_sp.getValue().equals("2")){
            holder.type3.setVisibility(View.GONE);
            holder.type4.setVisibility(View.GONE);
            holder.type5.setVisibility(View.GONE);
        }
        if (_sp.getValue().equals("3")){
            holder.type4.setVisibility(View.GONE);
            holder.type5.setVisibility(View.GONE);
        }
        if (_sp.getValue().equals("4")){
            holder.type5.setVisibility(View.GONE);
        }

        holder.from.setText(_sp.getBrand());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _sp= _list.get(position);
                onclick(_sp);
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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _sp = _list.get(position);
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_delete);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button yes,no;
                TextView title;
                title = dialog.findViewById(R.id.tv_dialog_title);
                title.setText("Delete "+_sp.getName()+" ?");
                yes = dialog.findViewById(R.id.btn_dialog_yes);
                no = dialog.findViewById(R.id.btn_dialog_no);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _sp_Dao.delete_sanPham(_sp.getID(),context);
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
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Acti_EditPost.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",_sp = _list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    public void onclick(sanPham sp){
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint.toString().isEmpty()){
                    _list = _listOld;
                }else{
                    ArrayList<sanPham> _arr = new ArrayList<>();
                    for (sanPham sp: _listOld) {
                        if (sp.getName().toString().toUpperCase().contains(constraint.toString().toUpperCase())){
                            _arr.add(sp);
                        }
                    }
                    _list = _arr;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values =_list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                _list = (ArrayList<sanPham>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        ImageView img;
        ImageView hetHang;
        TextView title,element,coin,from;
        ImageView type1,type2,type3,type4,type5;
        ImageView delete,edit;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout_itemHome);

            img = itemView.findViewById(R.id.img_itemHome_img1);
            hetHang = itemView.findViewById(R.id.img_itemHome_hetHang);
            title = itemView.findViewById(R.id.tv_itemHome_title);
            element = itemView.findViewById(R.id.tv_itemHome_info);
            coin = itemView.findViewById(R.id.tv_itemHome_coin);
            from = itemView.findViewById(R.id.tv_itemHome_from);

            type1 = itemView.findViewById(R.id.img_itemHome_type_1);
            type2 = itemView.findViewById(R.id.img_itemHome_type_2);
            type3 = itemView.findViewById(R.id.img_itemHome_type_3);
            type4 = itemView.findViewById(R.id.img_itemHome_type_4);
            type5 = itemView.findViewById(R.id.img_itemHome_type_5);

            delete = itemView.findViewById(R.id.img_delete_item);
            edit = itemView.findViewById(R.id.img_edit_item);
        }
    }
}
