package com.example.wheystore_nhom6.DAO;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.wheystore_nhom6.Model.hoaDon;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.ViewPager_Adapter.sanPham_Adapter;
import com.example.wheystore_nhom6.ViewPager_Adapter.sanPham_Home_Adapter;
import com.example.wheystore_nhom6.ViewPager_Adapter.sanPham_thongKe_thongTin_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class sanPham_Dao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    sanPham_thongKe_DAO thongKeDao;

    public void create_SanPham(sanPham sp, Context context) {
        thongKeDao = new sanPham_thongKe_DAO();
        CollectionReference collectionReference = db.collection("SanPham");
        DocumentReference documentReference = collectionReference.document();
        Map<String, Object> sanPham = new HashMap<>();
        sp.setID(documentReference.getId());
        sanPham.put("ID", documentReference.getId());
        sanPham.put("name", sp.getName());
        sanPham.put("brand", sp.getBrand());
        sanPham.put("value", sp.getValue());
        sanPham.put("coin", sp.getCoin());
        sanPham.put("img1", sp.getImg1());
        sanPham.put("img2", sp.getImg2());
        sanPham.put("img3", sp.getImg3());
        sanPham.put("phone", sp.getPhone());
        sanPham.put("timeTest", sp.getTimeTest());
        sanPham.put("from", sp.getFrom());
        sanPham.put("element", sp.getElement());
        sanPham.put("count", sp.getCount());
        sanPham.put("xuat", sp.getXuat());
        sanPham.put("messeger", sp.getMesseger());
        sanPham.put("note", sp.getNote());
        sanPham.put("type", sp.getType());
        sanPham.put("view", sp.getView());
        documentReference.set(sp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Thêm Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Thêm Sản Phẩm Khum Thành Công", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void read_data_tab(List<sanPham> _list, sanPham_Adapter adapter, String type) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("SanPham")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            if (_list!=null){
                                _list.clear();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                sanPham sp = document.toObject(sanPham.class);

                                if (document.getData().get("type").equals(type)){
                                    _list.add(sp);
                                }
                                //_list.add(sp);
//                                for (int i = 0; i < _list.size(); i++) {
//                                    if (!_list.get(i).getType().toUpperCase().equals(type.toUpperCase())){
//                                        _list.remove(i);
//                                    }
//                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    public void checkCount(String ID,int value, Button send) {
        DocumentReference docRef = db.collection("SanPham").document(ID);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String count = (String) Objects.requireNonNull(document.getData()).get("count");
                    if (Integer.parseInt(count)<value){
                        send.setEnabled(false);
                    }else {
                        send.setEnabled(true);
                    }
                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
    }


    public void readDataHome(List<sanPham> _list, sanPham_Home_Adapter adapter) {
        CollectionReference collectionReference = db.collection("SanPham");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (_list != null) {
                    _list.clear();
                }
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> _arr = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : _arr) {
                        sanPham sp = d.toObject(sanPham.class);
                        _list.add(sp);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void readDataThongKe(List<sanPham> _list, sanPham_thongKe_thongTin_Adapter adapter,String type) {
        CollectionReference collectionReference = db.collection("SanPham");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (_list != null) {
                    _list.clear();
                }
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> _arr = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : _arr) {
                        sanPham sp = d.toObject(sanPham.class);
                        _list.add(sp);
                        if (type.equals("coin")){
                            Collections.sort(_list, new Comparator<sanPham>() {
                                @Override
                                public int compare(sanPham o1, sanPham o2) {
                                    if (Integer.parseInt(o1.getCoin())<Integer.parseInt(o2.getCoin())){
                                        return 1;
                                    }else{
                                        if (Integer.parseInt(o1.getCoin())==Integer.parseInt(o2.getCoin())){
                                            return 0;
                                        }else{
                                            return  -1;
                                        }
                                    }
                                }
                            });
                        }
                        if (type.equals("view")){
                            Collections.sort(_list, new Comparator<sanPham>() {
                                @Override
                                public int compare(sanPham o1, sanPham o2) {
                                    if (Integer.parseInt(o1.getView())<Integer.parseInt(o2.getView())){
                                        return 1;
                                    }else{
                                        if (Integer.parseInt(o1.getView())==Integer.parseInt(o2.getView())){
                                            return 0;
                                        }else{
                                            return  -1;
                                        }
                                    }
                                }
                            });
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void readDataHome_filter(List<sanPham> _list, sanPham_Home_Adapter adapter,String giaTien) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("SanPham")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (_list!=null){
                                _list.clear();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                sanPham sp = document.toObject(sanPham.class);
                                _list.add(sp);
                            }
                            if (giaTien.equals("giam")){
                                Collections.sort(_list, new Comparator<sanPham>() {
                                    @Override
                                    public int compare(sanPham o1, sanPham o2) {
                                        if (Integer.parseInt(o1.getCoin())>Integer.parseInt(o2.getCoin())){
                                            return 1;
                                        }else{
                                            if (Integer.parseInt(o1.getCoin())==Integer.parseInt(o2.getCoin())){
                                                return 0;
                                            }else{
                                                return  -1;
                                            }
                                        }
                                    }
                                });
                            }
                            if (giaTien.equals("tang")){
                                Collections.sort(_list, new Comparator<sanPham>() {
                                    @Override
                                    public int compare(sanPham o1, sanPham o2) {
                                        if (Integer.parseInt(o1.getCoin())<Integer.parseInt(o2.getCoin())){
                                            return 1;
                                        }else{
                                            if (Integer.parseInt(o1.getCoin())==Integer.parseInt(o2.getCoin())){
                                                return 0;
                                            }else{
                                                return  -1;
                                            }
                                        }
                                    }
                                });
                            }

                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void read_More(List<sanPham> _list, sanPham_Home_Adapter adapter, String type) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("SanPham")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            if (_list!=null){
                                _list.clear();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                sanPham sp = document.toObject(sanPham.class);

                                if (document.getData().get("type").equals(type)){
                                    _list.add(sp);
                                }
//                                _list.add(sp);
//                                for (int i = 0; i < _list.size(); i++) {
//                                    if (!_list.get(i).getType().toUpperCase().equals(type.toUpperCase())){
//                                        _list.remove(i);
//                                    }
//                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.e("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void delete_sanPham(String ID, Context context) {
        CollectionReference collectionReference = db.collection("SanPham");
        collectionReference.document(ID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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



    public void Edit_Post(sanPham _sp,String index, Context context){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("SanPham").document(index)
            .set(_sp)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Update Sản Phẩm Thành Công !!!", Toast.LENGTH_SHORT).show();
                }

            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Update Sản Phẩm Thành Công Thất Bại  !!!", Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void update_sanPham_Success(String index,String count){
        DocumentReference washingtonRef = db.collection("SanPham").document(index);
        washingtonRef
                .update("count", count)
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

    public void update_sanPham_nhap(String index, int count, String docum) {
        DocumentReference docRef = db.collection("SanPham").document(index);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    String key = (String) document.getData().get(docum);

                    int value = Integer.parseInt(key)-count;
                    update_document(index,String.valueOf(value),docum);


                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
    }

    public void update_sanPham_xuat(String index, int count, String docum) {
        DocumentReference docRef = db.collection("SanPham").document(index);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    String key = (String) document.getData().get(docum);

                    int value = Integer.parseInt(key)+count;
                    update_document(index,String.valueOf(value),docum);
                    Log.e("TAG", "onComplete: "+key );

                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
    }

    public void update_document(String index, String count, String document){
        DocumentReference washingtonRef = db.collection("SanPham").document(index);
        washingtonRef
                .update(document, count)
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
