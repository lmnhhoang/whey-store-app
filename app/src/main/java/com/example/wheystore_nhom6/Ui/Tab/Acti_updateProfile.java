package com.example.wheystore_nhom6.Ui.Tab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheystore_nhom6.DAO.User_Dao;
import com.example.wheystore_nhom6.Model.user;
import com.example.wheystore_nhom6.R;
import com.google.android.material.textfield.TextInputLayout;

public class Acti_updateProfile extends AppCompatActivity {

    TextInputLayout userName,email,pass,pass2,adress,phone;
    TextView update;
    int check = 0;
    User_Dao user_dao ;
    user _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_update_profile);

        user_dao = new User_Dao();

        userName = findViewById(R.id.edt_Acti_updateProfile_userName);
        email = findViewById(R.id.edt_Acti_updateProfile_email);
        pass = findViewById(R.id.edt_Acti_updateProfile_pass);
        pass2 = findViewById(R.id.edt_Acti_updateProfile_pass2);
        adress = findViewById(R.id.edt_Acti_updateProfile_adress);
        phone = findViewById(R.id.edt_Acti_updateProfile_phone);

        user_dao.read_User(userName,email,pass,pass2,adress,phone);
        
        update = findViewById(R.id.tv_Acti_updateProfile_profile);
        
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getEditText().getText().toString().trim().isEmpty()){
                    userName.setError("moi ban nhap tai khoan");
                    userName.setErrorEnabled(true);
                }else{
                    userName.setErrorEnabled(false);
                    check++;
                }

                if (pass.getEditText().getText().toString().trim().isEmpty()){
                    pass.setError("moi ban nhap mat khau");
                    pass.setErrorEnabled(true);
                }else{
                    pass.setErrorEnabled(false);
                    check++;
                }

                if (pass2.getEditText().getText().toString().trim().isEmpty()){
                    pass2.setError("moi ban nhap mat khau");
                    pass2.setErrorEnabled(true);
                }else if (!pass.getEditText().getText().toString().trim().equals(pass2.getEditText().getText().toString().trim())){
                    pass2.setError("pass khong khop");
                    pass2.setErrorEnabled(true);
                }else{
                    pass2.setErrorEnabled(false);
                    check++;
                }

                if (adress.getEditText().getText().toString().trim().isEmpty()){
                    adress.setError("moi ban nhap dia chi");
                    adress.setErrorEnabled(true);
                }else{
                    adress.setErrorEnabled(false);
                    check++;
                }

                if (phone.getEditText().getText().toString().trim().isEmpty()){
                    phone.setError("moi ban nhap so dien thoai");
                    phone.setErrorEnabled(true);
                }else{
                    phone.setErrorEnabled(false);
                    check++;
                }
                
                if (check==5){
                    Toast.makeText(Acti_updateProfile.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                    _user = new user(userName.getEditText().getText().toString(),pass.getEditText().getText().toString(),pass.getEditText().getText().toString(),email.getEditText().getText().toString(),phone.getEditText().getText().toString(),adress.getEditText().getText().toString());
                    user_dao.Edit_User(_user,email.getEditText().getText().toString(),Acti_updateProfile.this);
                    check=0;
                }else{
                    Toast.makeText(Acti_updateProfile.this, "Có Lỗi !!! Hãy Thử lại sau", Toast.LENGTH_SHORT).show();
                    check=0;
                }
            }
        });
        
    }
}