package com.example.wheystore_nhom6.Ui.hello;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheystore_nhom6.DAO.User_Dao;
import com.example.wheystore_nhom6.Model.user;
import com.example.wheystore_nhom6.R;
import com.google.android.material.textfield.TextInputLayout;

public class Frag_register extends Fragment {

    TextInputLayout userName,pass,pass2,email,phone,adress;
    int check =0;
    TextView create;
    user _user;
    User_Dao user_dao;

    public Frag_register() {

    }



    public static Frag_register newInstance(String param1, String param2) {
        Frag_register fragment = new Frag_register();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_frag_register, container, false);
        user_dao = new User_Dao();
        _user = new user();

        userName = view.findViewById(R.id.edt_userName_Create);
        pass = view.findViewById(R.id.edt_pass_Create);
        pass2 = view.findViewById(R.id.edt_pass2_Create);
        email = view.findViewById(R.id.edt_email_Create);
        adress = view.findViewById(R.id.edt_adress_Create);
        phone = view.findViewById(R.id.edt_phone_Create);
        create = view.findViewById(R.id.tv_Frag_login_login);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getEditText().getText().toString().trim().isEmpty()){
                    userName.setError("moi ban nhap user name");
                    userName.setErrorEnabled(true);
                }else{
                    userName.setErrorEnabled(false);
                    check++;
                }

                if (pass.getEditText().getText().toString().trim().isEmpty()){
                    pass.setError("moi ban nhap user name");
                    pass.setErrorEnabled(true);
                }else{
                    pass.setErrorEnabled(false);
                    check++;
                }

                if (pass2.getEditText().getText().toString().trim().isEmpty()){
                    pass2.setError("moi ban nhap user name");
                    pass2.setErrorEnabled(true);
                }else if (!pass.getEditText().getText().toString().trim().equals(pass2.getEditText().getText().toString().trim())){
                    pass2.setError("pass khong khop");
                    pass2.setErrorEnabled(true);
                }else{
                    pass2.setErrorEnabled(false);
                    check++;
                }

                if (email.getEditText().getText().toString().trim().isEmpty()){
                    email.setError("moi ban nhap user name");
                    email.setErrorEnabled(true);
                }else{
                    email.setErrorEnabled(false);
                    check++;
                }

                if (adress.getEditText().getText().toString().trim().isEmpty()){
                    adress.setError("moi ban nhap dia chỉ");
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

                if (check==6){
                    _user = new user(userName.getEditText().getText().toString().trim(),pass.getEditText().getText().toString().trim(),pass.getEditText().getText().toString().trim(),email.getEditText().getText().toString().trim(),phone.getEditText().getText().toString().trim(),adress.getEditText().getText().toString().trim());
                    user_dao.singUp_User(_user,getContext());
                    check =0;
                }else{
                    Toast.makeText(getContext(), "Khong Thanh Cong", Toast.LENGTH_SHORT).show();
                    check =0;
                }
            }
        });


        return view;
    }
}