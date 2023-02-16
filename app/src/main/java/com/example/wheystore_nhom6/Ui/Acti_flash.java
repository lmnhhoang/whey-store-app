package com.example.wheystore_nhom6.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.wheystore_nhom6.MainActivity;
import com.example.wheystore_nhom6.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Acti_flash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_flash);



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    NextLogin();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public  void NextLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            startActivity(new Intent(Acti_flash.this, Acti_hello.class));
            finish();
        }else{
            startActivity(new Intent(Acti_flash.this, MainActivity.class));
            finish();
        }
    }
}