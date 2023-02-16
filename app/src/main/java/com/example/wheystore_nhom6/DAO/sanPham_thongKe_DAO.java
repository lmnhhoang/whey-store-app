package com.example.wheystore_nhom6.DAO;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.Model.sanPham_thongKe;
import com.example.wheystore_nhom6.ViewPager_Adapter.sanPham_Home_Adapter;
import com.example.wheystore_nhom6.ViewPager_Adapter.sanPham_thongKe_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class sanPham_thongKe_DAO {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public void create_thongKe(sanPham_thongKe thongKe, Context context){

        CollectionReference collectionReference = firestore.collection("ThongKe");
        DocumentReference documentReference = collectionReference.document();

        Map<String,Object> map = new HashMap<>();
        thongKe.setIdPost(documentReference.getId());
        map.put("ID",documentReference.getId());
        map.put("img1",thongKe.getImg());
        map.put("title",thongKe.getTitle());
        map.put("xuat",thongKe.getXuat());
        map.put("coin",thongKe.getCoin());
        map.put("date",thongKe.getDate());
        documentReference.set(thongKe).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Thêm Thông Tin Thống Kê", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Có Lỗi Xảy Ra!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void readDataThongKe(List<sanPham_thongKe> _list, sanPham_thongKe_Adapter adapter) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("ThongKe")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            if (_list!=null){
                                _list.clear();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                sanPham_thongKe sp = document.toObject(sanPham_thongKe.class);
                                _list.add(sp);
                            }
                            Collections.sort(_list, new Comparator<sanPham_thongKe>() {
                                @Override
                                public int compare(sanPham_thongKe o1, sanPham_thongKe o2) {
                                    if (Integer.parseInt(o1.getXuat())<Integer.parseInt(o2.getXuat())){
                                        return 1;
                                    }else{
                                        if (Integer.parseInt(o1.getXuat())==Integer.parseInt(o2.getXuat())){
                                            return 0;
                                        }else{
                                            return -1;
                                        }
                                    }
                                }
                            });
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void readDataThongKe_GiaoDich(Date ngayBatDau, Date ngayKetThuc, TextView gia, Context context, RecyclerView recyclerView) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("ThongKe")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        List<sanPham_thongKe> _list = new ArrayList<>();


                        ngayKetThuc.setDate(ngayKetThuc.getDate()+1);

                        if (task.isSuccessful()) {
                            if (_list!=null){
                                _list.clear();
                            }
                            int doanhThu = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                sanPham_thongKe a =document.toObject(sanPham_thongKe.class);
                                if (ngayBatDau.compareTo(a.getDate()) <= 0 && a.getDate().compareTo(ngayKetThuc) <= 0&&Integer.parseInt(a.getXuat())>0) {
                                    doanhThu = doanhThu + (Integer.parseInt(a.getXuat())*Integer.parseInt(a.getCoin()));
                                    _list.add(a);
                                }
                            }
                            Collections.sort(_list, new Comparator<sanPham_thongKe>() {
                                @Override
                                public int compare(sanPham_thongKe o1, sanPham_thongKe o2) {
                                    if (o1.getDate().compareTo(o2.getDate()) <= 0) {
                                        return 1;
                                    }
                                    return -1;
                                }
                            });

                            DecimalFormat formatter = new DecimalFormat("###,###,###");

                            gia.setText("Doanh Thu:  " + formatter.format(doanhThu) + " VNĐ");
                            sanPham_thongKe_Adapter adapter = new sanPham_thongKe_Adapter(_list,context);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.e("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void update_thongKe_xuat(String index,String count,String docum){
        DocumentReference washingtonRef = firestore.collection("ThongKe").document(index);
        washingtonRef
                .update(docum, count)
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

    public void delete_thongKe(String index,Context context){
        CollectionReference collectionReference = firestore.collection("ThongKe");
        collectionReference.document(index).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Xóa Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Xóa Sản Phẩm Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void check_count(String index,int count,String docum) {
        DocumentReference docRef = firestore.collection("ThongKe").document(index);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    String key = (String) document.getData().get(docum);
                    int value = Integer.parseInt(key)+count;
                    update_thongKe_xuat(index,String.valueOf(value),docum);
                    Log.e("TAG", "onComplete: "+key );

                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
    }

//    public void update_thongKe(sanPham sp) {
//        DocumentReference docRef = firestore.collection("ThongKe").document(sp.getID());
//        Source source = Source.CACHE;
//        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//
//                    DocumentSnapshot document = task.getResult();
//                    Timestamp date = (Timestamp) document.getData().get("date");
//                    String nhap = (String) document.getData().get("nhap");
//                    String xuat = (String) document.getData().get("xuat");
//                    Date date1 = new Date(date.getSeconds());
//                    Log.e("TAG", "time: "+date1 );
//
//                    sanPham_thongKe sptk = new sanPham_thongKe(sp.getID(),sp.getImg1(),sp.getName(),nhap,xuat,sp.getCoin(),date1);
//                    Edit_thongKe(sptk,sp.getID());
//                } else {
//                    Log.d("TAG", "Cached get failed: ", task.getException());
//                }
//            }
//        });
//    }

    public void Edit_thongKe(sanPham_thongKe _sp,String index){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("ThongKe").document(index)
                .set(_sp)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(context, "Update Success !!!", Toast.LENGTH_SHORT).show();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(context, "Update Faild !!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
