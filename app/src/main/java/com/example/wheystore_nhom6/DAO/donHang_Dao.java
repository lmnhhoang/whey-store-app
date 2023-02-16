package com.example.wheystore_nhom6.DAO;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wheystore_nhom6.Model.hoaDon;
import com.example.wheystore_nhom6.ViewPager_Adapter.donHang_Adapter;
import com.example.wheystore_nhom6.ViewPager_Adapter.hoaDon_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class donHang_Dao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void create_donHang(hoaDon _hd, Context context) {
        Map<String, Object> hd = new HashMap<>();
        hd.put("ID", _hd.getId());
        hd.put("idPost", _hd.getIdPost());
        hd.put("UserName", _hd.getUserName());
        hd.put("title", _hd.getTitle());
        hd.put("value", _hd.getValue());
        hd.put("coin", _hd.getCoin());
        hd.put("img1", _hd.getImg1());
        hd.put("phone", _hd.getPhone());
        hd.put("email", _hd.getEmail());
        hd.put("adress", _hd.getAdress());
        hd.put("date", _hd.getDate());

        db.collection("DonHang").document(_hd.getId())
                .set(_hd)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                        // up status
                        DocumentReference washingtonRef = db.collection("HoaDon").document(_hd.getId());
                        washingtonRef
                                .update("status", "Chờ Xác Nhận")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Đặt Hàng Thành Công", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Đặt Hàng Không Thành Công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    public void read_HoaDon(List<hoaDon> _list, donHang_Adapter adapter, String type){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("DonHang")
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
                                if (document.getData().get("status").toString().equals(type)) {
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


    public void delete_DonHang(String ID, Context context) {
        CollectionReference collectionReference = db.collection("DonHang");
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

    public void update_trangThai_donHang(String index, String status){
        DocumentReference washingtonRef = db.collection("DonHang").document(index);
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

}
