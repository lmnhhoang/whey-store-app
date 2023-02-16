package com.example.wheystore_nhom6.DAO;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wheystore_nhom6.Model.hoaDon;
import com.example.wheystore_nhom6.ViewPager_Adapter.choXacNhan_Adapter;
import com.example.wheystore_nhom6.ViewPager_Adapter.hoaDon_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class hoaDon_Dao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void create_HoaDon(hoaDon _hd, Context context) {

        CollectionReference collectionReference = db.collection("HoaDon");
        DocumentReference documentReference = collectionReference.document();
        Map<String, Object> hd = new HashMap<>();

        _hd.setId(documentReference.getId());
        hd.put("ID", documentReference.getId());
        hd.put("idPost", _hd.getIdPost());
        hd.put("UserName", _hd.getUserName());
        hd.put("title", _hd.getTitle());
        hd.put("value", _hd.getValue());
        hd.put("coin", _hd.getCoin());
        hd.put("img1", _hd.getImg1());
        hd.put("phone", _hd.getPhone());
        hd.put("adress", _hd.getAdress());
        hd.put("email", _hd.getEmail());
        hd.put("date", _hd.getDate());
        documentReference.set(_hd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Đã Thêm Vào Hóa Đơn", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Hãy Thử Lại", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void read_HoaDon(List<hoaDon> _list, hoaDon_Adapter adapter,String type){
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//        firebaseFirestore.collection("HoaDon")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                        if (task.isSuccessful()) {
//                            if (_list!=null){
//                                _list.clear();
//                            }
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                hoaDon hd = document.toObject(hoaDon.class);
//                                _list.add(hd);
//
//                                if (type.equals("no")){
//                                    for (int i = 0; i < _list.size(); i++) {
//                                        if (!_list.get(i).getEmail().equals(user.getEmail())||_list.get(i).getStatus().equals("1")){
//                                            _list.remove(i);
//                                        }
//                                    }
//                                }
//                                if (type.equals("yes")){
//                                    for (int i = 0; i < _list.size(); i++) {
//                                        if (!_list.get(i).getEmail().equals(user.getEmail())||_list.get(i).getStatus().equals("0")){
//                                            _list.remove(i);
//                                        }
//                                    }
//                                }
//                            }
//                            adapter.notifyDataSetChanged();
//                        } else {
//                            Log.e("TAG", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//    }

    public void delete_hoaDon(String ID, Context context) {
        CollectionReference collectionReference = db.collection("HoaDon");
        collectionReference.document(ID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Xóa Hóa Đơn Thành Công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Xóa Hóa Đơn Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public long check_status(String index,ImageView daDatHang,ImageView thanhCong,ImageView huy) {
        DocumentReference docRef = db.collection("HoaDon").document(index);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    String key = (String) document.getData().get("status");
                    if (key.equals("0")){
                        daDatHang.setVisibility(View.GONE);
                        thanhCong.setVisibility(View.GONE);
                        huy.setVisibility(View.GONE);
                    }
                    if (key.equals("1")){
                        daDatHang.setVisibility(View.VISIBLE);
                        thanhCong.setVisibility(View.GONE);
                        huy.setVisibility(View.GONE);
                    }
                    if(key.equals("3")){
                        daDatHang.setVisibility(View.GONE);
                        daDatHang.setVisibility(View.GONE);
                        huy.setVisibility(View.VISIBLE);
                    }

                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
        return -1;
    }

    public void update_trangThai_donHang(String index, String status){
        DocumentReference washingtonRef = db.collection("HoaDon").document(index);
        washingtonRef.update("status", status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("TAG", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "Error updating document", e);
                    }
                });
    }

    public void checkMuaSanPham(TextView tv, int giatien) {
        FirebaseFirestore.getInstance().collection("HoaDon").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //Log.e("TAG", "onComplete: "+document.getData().get("status"));
                        if (document.getData().get("status").toString().equals("1")) {
                            tv.setText("Đã mua");
                            Log.e("TAG", "Đã mua: " );
                        }

                        if (document.getData().get("status").toString().equals("0")) {
                            Log.e("TAG", "chưa mua: " );
                            tv.setText(giatien + " $");
                            //tv.setTextColor(Color.parseColor("#FFF7B3"));
                        }
                    }
                } else {

                }
            }
        });
    }

    public void read_HoaDon_tuyChon(List<hoaDon> _list, hoaDon_Adapter adapter,String type){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("HoaDon")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            if (_list!=null){
                                _list.clear();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                hoaDon hd = document.toObject(hoaDon.class);
                                if (document.getData().get("email").toString().equals(user.getEmail())&&document.getData().get("status").toString().equals(type)) {
                                    _list.add(hd);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void read_choXacNhan(List<hoaDon> _list, choXacNhan_Adapter adapter, String type){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("HoaDon")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.e("TAG", "cooooooooo");
                        if (task.isSuccessful()) {
                            if (_list!=null){
                                _list.clear();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                hoaDon hd = document.toObject(hoaDon.class);
                                if (document.getData().get("email").toString().equals(user.getEmail())&&document.getData().get("status").toString().equals(type)) {
                                    _list.add(hd);
                                }

                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void checkStatus(String ID, ImageView delete,ImageView success) {
        DocumentReference docRef = db.collection("HoaDon").document(ID);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String status = (String) Objects.requireNonNull(document.getData()).get("status");
                    Log.e("TAG", "onComplete: "+status );
                    if (status.equals("Chờ Lấy Hàng")){
                        success.setVisibility(View.VISIBLE);
                    }
                    if (status.equals("Đơn Hàng Bị Hủy")){
                        delete.setVisibility(View.VISIBLE);
                    }
                    if (status.equals("Thành Công")){
                        delete.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
    }
}
