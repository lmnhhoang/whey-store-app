package com.example.wheystore_nhom6.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheystore_nhom6.DAO.User_Dao;
import com.example.wheystore_nhom6.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Acti_setting extends AppCompatActivity {

    TextView updateProfile,FAQ,logout;
    User_Dao user_dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_setting);
        anhXa();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_checkpoin);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText email = dialog.findViewById(R.id.edt_dialog_checkpoin_email);
                TextView title = dialog.findViewById(R.id.tv_dialog_checkpoin_title);
                title.setText("mời bạn nhập mật khẩu");
                email.setHint("******");
                email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                Button update = dialog.findViewById(R.id.btn_dialog_checkpoin_done);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(Acti_setting.this, "okkk", Toast.LENGTH_SHORT).show();
                        if (email.getText().toString().isEmpty()){
                            Toast.makeText(Acti_setting.this, "Mời bạn nhập thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            user_dao.checkPassWord(user.getEmail(),email.getText().toString(),Acti_setting.this);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.dialog_checkpoin);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText email = dialog.findViewById(R.id.edt_dialog_checkpoin_email);
                TextView title = dialog.findViewById(R.id.tv_dialog_checkpoin_title);
                title.setText("mời bạn nhập mật khẩu");
                email.setHint("******");
                email.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                Button update = dialog.findViewById(R.id.btn_dialog_checkpoin_done);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (email.getText().toString().isEmpty()){
                            Toast.makeText(Acti_setting.this, "Mời bạn nhập thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            user_dao.deleteUserAuth(user.getEmail(),v.getContext());
                            startActivity(new Intent(Acti_setting.this, Acti_hello.class));
                            finish();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        Dialog dialog = new Dialog(Acti_setting.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.loading);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2222);
                            dialog.dismiss();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(Acti_setting.this, Acti_hello.class));
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }
    public void anhXa(){
        logout = findViewById(R.id.tv_Acti_setting_Logout);
        updateProfile = findViewById(R.id.tv_Acti_setting_updateProfile);
        FAQ = findViewById(R.id.tv_Acti_setting_delete);
        user_dao= new User_Dao();
    }
}