package com.example.wheystore_nhom6.Ui.hello;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.wheystore_nhom6.DAO.User_Dao;
import com.example.wheystore_nhom6.R;
import com.google.android.material.textfield.TextInputLayout;


public class Frag_login extends Fragment {

    TextInputLayout userName,passWord;

    TextView login;
    CheckBox reMemberMe;
    User_Dao user_dao;
    int check = 0;
    SharedPreferences sharedPreferences;
    Dialog dialog;

    public Frag_login() {
    }

    public static Frag_login newInstance(String param1, String param2) {
        Frag_login fragment = new Frag_login();
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
        View view = inflater.inflate(R.layout.fragment_frag_login, container, false);
        login = view.findViewById(R.id.tv_Frag_login_login);
        userName = view.findViewById(R.id.edt_Frag_login_userName);
        passWord = view.findViewById(R.id.edt_Frag_login_passWord);
        user_dao = new User_Dao();

        sharedPreferences = getActivity().getSharedPreferences("Member", Context.MODE_PRIVATE);
        userName.getEditText().setText(sharedPreferences.getString("UserN", ""));
        passWord.getEditText().setText(sharedPreferences.getString("PassW", ""));
        reMemberMe = view.findViewById(R.id.ckb_Frag_login_reMemberMe);
        reMemberMe.setChecked(sharedPreferences.getBoolean("Re", false));


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getEditText().getText().toString().trim().isEmpty()){
                    userName.setError("moi ban nhap tai khoan");
                    userName.setErrorEnabled(true);
                }else{
                    userName.setErrorEnabled(false);
                    check++;
                }

                if (passWord.getEditText().getText().toString().trim().isEmpty()){
                    passWord.setError("moi ban nhap mat khau");
                    passWord.setErrorEnabled(true);
                }else{
                    passWord.setErrorEnabled(false);
                    check++;
                }
                if (check==2){
                    dialog = new Dialog(view.getContext());
                    dialog.setContentView(R.layout.loading);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2222);
                                user_dao.sign_User(userName.getEditText().getText().toString().trim(),passWord.getEditText().getText().toString(),getContext(),dialog);
                                RememberMe(userName.getEditText().getText().toString().trim(),passWord.getEditText().getText().toString().trim(),reMemberMe.isChecked());
                                dialog.dismiss();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    check=0;
                }
            }
        });
        return view;
    }

    public void RememberMe(String userName, String passWord, boolean check) {
        sharedPreferences = getActivity().getSharedPreferences("Member", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (!check) {
            edit.clear();
        } else {
            edit.putString("UserN", userName);
            edit.putString("PassW", passWord);
            edit.putBoolean("Re", check);
        }
        edit.commit();
    }
}