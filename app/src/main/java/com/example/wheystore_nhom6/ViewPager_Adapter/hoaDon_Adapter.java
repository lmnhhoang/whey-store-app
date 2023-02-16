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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class hoaDon_Adapter extends RecyclerView.Adapter<hoaDon_Adapter.ViewHoder> {

    List<hoaDon> _list;
    hoaDon_Dao hoaDon_dao;
    donHang_Dao donHang_dao;
    sanPham_Dao sanPham_dao;
    sanPham_thongKe_DAO  thongKe_Dao;
    Context context;
    hoaDon _hd;
    DecimalFormat formatter;
    sanPham_thongKe sanPham_thongKe;
    public hoaDon_Adapter(List<hoaDon> _list, Context context) {
        this._list = _list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoadon,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {
        hoaDon_dao = new hoaDon_Dao();
        donHang_dao = new donHang_Dao();
        sanPham_dao = new sanPham_Dao();
        thongKe_Dao = new sanPham_thongKe_DAO();

        _hd = _list.get(position);

        Date dNow = _hd.getDate();
        SimpleDateFormat ft =new SimpleDateFormat("dd.MM.yyyy");

        hoaDon_dao.check_status(_hd.getId(),holder.daDatHang,holder.success,holder.huy);
        //hoaDon_dao.checkMuaSanPham(holder.coin);
        formatter = new DecimalFormat("###,###,###");

        holder.title.setText(_hd.getTitle());
        holder.coin.setText(formatter.format((Integer.parseInt(_hd.getCoin())*_hd.getValue()))+" vnd");
        holder.soLuong.setText("Số lượng : "+_hd.getValue());
        holder.date_sp.setText(ft.format(dNow));
        Picasso.get().load(_hd.getImg1()).resize(350,350).centerCrop().into(holder.img);

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

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                _hd = _list.get(position);

//                //update số lượng xuất ra trong thống kê
//                sanPham_thongKe = new sanPham_thongKe("id_autorender", _hd.getImg1(), _hd.getTitle(), String.valueOf(_hd.getValue()), _hd.getCoin(), date);
//                thongKe_Dao.create_thongKe(sanPham_thongKe, context);
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_check_hoadon);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView title,values,weight,coin,nameUser,adress,phone,buy;
                ImageView img = dialog.findViewById(R.id.img_dialog_check_hoaDon);
                Picasso.get().load(_hd.getImg1()).resize(350,350).centerCrop().into(img);
                title = dialog.findViewById(R.id.tv_dialog_title_check_hoaDon);
                values = dialog.findViewById(R.id.tv_dialog_value_check_hoaDon);
                weight = dialog.findViewById(R.id.tv_dialog_weight__check_hoaDon);
                coin = dialog.findViewById(R.id.tv_dialog_coin_check_hoaDon);
                nameUser = dialog.findViewById(R.id.tv_dialog_nameMember_check_hoaDon);
                adress = dialog.findViewById(R.id.tv_dialog_adress_member_check_hoaDon);
                phone = dialog.findViewById(R.id.tv_dialog_phone_member_check_hoaDon);
                buy = dialog.findViewById(R.id.btn_dialog_send_check_hoaDon);
                //
                title.setText(_hd.getTitle());
                values.setText("Số lượng : "+String.valueOf(_hd.getValue()));
                coin.setText(formatter.format(Integer.parseInt(_hd.getCoin()))+" vnd" );
                nameUser.setText("Name : "+_hd.getUserName());
                adress.setText("Adress : "+_hd.getAdress());
                phone.setText("Phone : "+_hd.getPhone());

                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _hd = _list.get(position);

                        Date date = new Date();

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        _hd = new hoaDon(_hd.getId(), _hd.getIdPost(), _hd.getUserName(), _hd.getTitle(), _hd.getCoin(), _hd.getImg1(), _hd.getPhone(), _hd.getAdress(), _hd.getStatus(), _hd.getValue(), user.getEmail(), date);
                        //Đơn Hàng
                        donHang_dao.create_donHang(_hd, context);

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

        LinearLayout linearLayout;
        TextView title,coin,soLuong,date_sp;
        ImageView img;
        ImageView success,huy,daDatHang;
        ImageView delete;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            linearLayout= itemView.findViewById(R.id.LinearLayout_hoaDon);

            title= itemView.findViewById(R.id.tv_item_hoaDon_title);
            coin= itemView.findViewById(R.id.tv_item_hoaDon_coin);
            soLuong= itemView.findViewById(R.id.tv_item_hoaDon_value);
            date_sp= itemView.findViewById(R.id.tv_item_hoaDon_date);
            img= itemView.findViewById(R.id.img_item_donHang);
            success= itemView.findViewById(R.id.img_item_hoaDon_thanhCong);
            daDatHang= itemView.findViewById(R.id.img_item_hoaDon_daDatHang);
            huy= itemView.findViewById(R.id.img_item_hoaDon_false);
            delete= itemView.findViewById(R.id.img_item_hoaDon_delete);
        }
    }

}
