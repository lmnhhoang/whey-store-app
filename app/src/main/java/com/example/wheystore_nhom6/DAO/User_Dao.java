package com.example.wheystore_nhom6.DAO;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wheystore_nhom6.MainActivity;
import com.example.wheystore_nhom6.Model.hoaDon;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.Model.user;
import com.example.wheystore_nhom6.Ui.Tab.Acti_updateProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class User_Dao {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser _UserAuth = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth mAuth;
    hoaDon_Dao hoaDon_dao;
    hoaDon _hoaDon;

    public void singUp_User(user _user, Context context){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(_user.getEmail(), _user.getPass())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser Fuser = mAuth.getCurrentUser();
                    Fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(_user.getUserName())
                                .build();
                                Fuser.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            create_User(_user,context);
                                        }
                                    }
                                });
                        }
                    });
                    Toast.makeText(context, "Authentication Success.",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Authentication failed.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void sign_User(String pass, String email, Context context, Dialog dialog){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(pass,email)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            boolean emailVerified = firebaseUser.isEmailVerified();
                            Log.e("TAG", "onComplete: "+emailVerified );
                            if (emailVerified != true) {
                                dialog.dismiss();
                                Toast.makeText(context, "Bạn cần xác nhận email trước khi đăng nhập", Toast.LENGTH_SHORT).show();
                            return;
                            } else {
                                Log.e("TAG", "login onComplete: " );
                                dialog.dismiss();
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Tài Khoản hoặc Mật Khẩu Không Đúng", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }

    public void create_User(user _user,Context context){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> sanPham = new HashMap<>();
        sanPham.put("Email", _user.getEmail());
        sanPham.put("name", _user.getUserName());
        sanPham.put("pass", _user.getPass());
        sanPham.put("phone", _user.getPhone());
        sanPham.put("adress", _user.getAdress());

        db.collection("User").document(_user.getEmail())
            .set(_user)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.e("TAG", "DocumentSnapshot successfully written!");
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("TAG", "Error writing document", e);
                }
            });
    }


//    public void Edit_User(String index,String phone,String adress){
//        DocumentReference washingtonRef = db.collection("User").document(index);
//        washingtonRef
//                .update("phone", phone,"adress",adress)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.e("TAG", "DocumentSnapshot successfully updated!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e("TAG", "Error updating document", e);
//                    }
//                });
//
//    }

    public void Edit_User(user _user,String index, Context context){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("User").document(index)
                .set(_user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        updatePassW_Auth(_user.getPass(),context);
                        update_UserName(_user.getUserName());
                        context.startActivity(new Intent(context,MainActivity.class));
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Update User Thành Công Thất Bại  !!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteUserAuth(String email,Context context){
        _UserAuth = FirebaseAuth.getInstance().getCurrentUser();
        _UserAuth.delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        deleteUser(email);
                        Toast.makeText(context, "Vô hiệu hóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    public void deleteUser(String ID) {
        CollectionReference collectionReference = db.collection("User");
        collectionReference.document(ID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.e("TAG", "Xóa User Thành Công" );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "Xóa User Dell Thành Công" );
            }
        });

    }

    public void updatePassW_Auth(String passNew,Context context) {
        _UserAuth = FirebaseAuth.getInstance().getCurrentUser();
        _UserAuth.updatePassword(passNew)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Cập nhật mật khẩu Thành Công !!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    public void read_User(TextInputLayout userName,TextInputLayout email,TextInputLayout pass,TextInputLayout pass2,TextInputLayout adress,TextInputLayout phone ){
        _UserAuth = FirebaseAuth.getInstance().getCurrentUser();
        List<user> _list = new ArrayList<>();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("User")
                .document(_UserAuth.getEmail()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        user us = value.toObject(user.class);
                        _list.add(us);
                        for (int i = 0; i < _list.size(); i++) {
                            Log.e("TAG", "usuer: "+_list.get(i).getUserName());
                            userName.getEditText().setText(_list.get(i).getUserName());
                            email.getEditText().setText(_list.get(i).getEmail());
                            pass.getEditText().setText(_list.get(i).getPass());
                            pass2.getEditText().setText(_list.get(i).getPass());
                            adress.getEditText().setText(_list.get(i).getAdress());
                            phone.getEditText().setText(_list.get(i).getPhone());
                        }
                    }
                });
    }

    public boolean check_admin(){
        _UserAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (_UserAuth.getEmail().equals("lamnhph18826@fpt.edu.vn")){
            Log.e("TAG", "day la admin: " );
            return true;
        }
        return false;
    }

    public void checkInfo( sanPham sp,int value, Date date, Context context) {
        _UserAuth = FirebaseAuth.getInstance().getCurrentUser();
        String email = Objects.requireNonNull(_UserAuth).getEmail();
        DocumentReference docRef = db.collection("User").document(email);
        Source source = Source.CACHE;
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    String phone = (String) Objects.requireNonNull(document.getData()).get("phone");
                    String adress = (String) document.getData().get("adress");
                    String userName = (String) document.getData().get("userName");

                    hoaDon_dao = new hoaDon_Dao();
                    _hoaDon = new hoaDon("idtutang",sp.getID(),userName,sp.getName(),sp.getCoin(),sp.getImg1(),phone,adress,"0",value,email,date);
                    hoaDon_dao.create_HoaDon(_hoaDon,context);
                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
    }
    public void checkPassWord(String email,String passRep,Context context) {
        DocumentReference docRef = db.collection("User").document(email);
        Source source = Source.CACHE;
         docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();

                    String pass = (String) document.getData().get("pass");
                    if (passRep.equals(pass)){
                        context.startActivity(new Intent(context, Acti_updateProfile.class));
                        Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("TAG", "Cached get failed: ", task.getException());
                }
            }
        });
    }

    public void SendPassReset(String mail,Context context) {
        mAuth.sendPasswordResetEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Mời bạn kiểm tra mail để lấy lại mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void update_UserName(String username){
        _UserAuth = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        _UserAuth.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.e("TAG", "UserName updated.");
                        }
                    }
                });

    }

}
