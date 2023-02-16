package com.example.wheystore_nhom6.Ui.Tab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheystore_nhom6.DAO.sanPham_Dao;
import com.example.wheystore_nhom6.MainActivity;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.R;
import com.google.android.material.textfield.TextInputLayout;

public class Acti_newPost extends AppCompatActivity {

    TextInputLayout name,brand,coin,img1,img2,img3,phone,weight,timeTest,from,element,count,messeger,note;
    String _name,_brand,_coin,_img1,_img2,_img3,_phone,_weight,_timeTest,_from,_element,_count,_messeger,_note;
    int _value = 0;
    int _type = 0;
    RadioButton radioButton,radioButton_type;
    sanPham_Dao sp_Dao;
    sanPham sp;
    //Date dNow;

    int check = 0;

    RadioGroup value,type;
    TextView create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_new_post);

//        dNow = new Date( );
//        SimpleDateFormat ft =new SimpleDateFormat("dd.MM.yyyy");
//        Log.e("TAG", "date: "+ft.format(dNow) );

        sp_Dao = new sanPham_Dao();

        name = findViewById(R.id.edt_new_name);
        brand = findViewById(R.id.edt_new_brand);
        //CheckBox
        value = findViewById(R.id.radioGroup_value);
        type = findViewById(R.id.radioGroup_type);

        coin = findViewById(R.id.edt_new_coin);
        img1 = findViewById(R.id.edt_new_img1);
        img2 = findViewById(R.id.edt_new_img2);
        img3 = findViewById(R.id.edt_new_img3);
        phone = findViewById(R.id.edt_new_phone);
        weight = findViewById(R.id.edt_new_weight);
        timeTest = findViewById(R.id.edt_new_timeTest);
        from = findViewById(R.id.edt_new_from);
        count = findViewById(R.id.edt_new_count);
        element = findViewById(R.id.edt_new_element);
        messeger = findViewById(R.id.edt_new_massages);
        note = findViewById(R.id.edt_new_note);



        create = findViewById(R.id.tv_Frag_login_login);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _name = name.getEditText().getText().toString().trim();
                _brand = brand.getEditText().getText().toString().trim();
                _coin = coin.getEditText().getText().toString().trim();
                _img1 = img1.getEditText().getText().toString().trim();
                _img2 = img2.getEditText().getText().toString().trim();
                _img3 = img3.getEditText().getText().toString().trim();
                _phone = phone.getEditText().getText().toString().trim();
                _weight = weight.getEditText().getText().toString().trim();
                _timeTest = timeTest.getEditText().getText().toString().trim();
                _from = from.getEditText().getText().toString().trim();
                _element = element.getEditText().getText().toString().trim();
                _count = count.getEditText().getText().toString().trim();
                _messeger = messeger.getEditText().getText().toString().trim();
                _note = note.getEditText().getText().toString().trim();



                _value= value.getCheckedRadioButtonId();
                if (_value<0){
                    Toast.makeText(Acti_newPost.this, "danh gai chua duoc chon", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    radioButton = findViewById(_value);
                    Toast.makeText(Acti_newPost.this, "danh gia : "+radioButton.getText()+" SAO", Toast.LENGTH_SHORT).show();
                    check++;
                }

                _type =type.getCheckedRadioButtonId();

                if (_type<0){
                    Toast.makeText(Acti_newPost.this, "loai chua duoc chon", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    radioButton_type = findViewById(_type);
                    Log.e("TAG", "radioButton_type: "+radioButton_type.getText());
                    Toast.makeText(Acti_newPost.this, "loai : "+radioButton.getText(), Toast.LENGTH_SHORT).show();
                    check++;
                }

                if (_name.isEmpty()){
                    name.setError("ten san pham");
                    name.setErrorEnabled(true);
                }else{
                    name.setErrorEnabled(false);
                    check++;
                }

                if (_brand.isEmpty()){
                    brand.setError("thuong hieu");
                    brand.setErrorEnabled(true);
                }else{
                    brand.setErrorEnabled(false);
                    check++;
                }

                if (_coin.isEmpty()){
                    coin.setError("gia tien");
                    coin.setErrorEnabled(true);
                }else{
                    coin.setErrorEnabled(false);
                    check++;
                }

                if (_img1.isEmpty()){
                    img1.setError("anh 1");
                    img1.setErrorEnabled(true);
                }else{
                    img1.setErrorEnabled(false);
                    check++;
                }

                if (_img2.isEmpty()){
                    img2.setError("anh 2");
                    img2.setErrorEnabled(true);
                }else{
                    img2.setErrorEnabled(false);
                    check++;
                }

                if (_img3.isEmpty()){
                    img3.setError("anh 3");
                    img3.setErrorEnabled(true);
                }else{
                    img3.setErrorEnabled(false);
                    check++;
                }

                if (_phone.isEmpty()){
                    phone.setError("so dien thoai lien he");
                    phone.setErrorEnabled(true);
                }else{
                    phone.setErrorEnabled(false);
                    check++;
                }

                if (_weight.isEmpty()){
                    weight.setError("trong luong");
                    weight.setErrorEnabled(true);
                }else{
                    weight.setErrorEnabled(false);
                    check++;
                }

                if (_count.isEmpty()){
                    count.setError("so luong");
                    count.setErrorEnabled(true);
                }else{
                    count.setErrorEnabled(false);
                    check++;
                }

                if (_timeTest.isEmpty()){
                    timeTest.setError("thoi gian su dung");
                    timeTest.setErrorEnabled(true);
                }else{
                    timeTest.setErrorEnabled(false);
                    check++;
                }

                if (_from.isEmpty()){
                    from.setError("nguon goc");
                    from.setErrorEnabled(true);
                }else{
                    from.setErrorEnabled(false);
                    check++;
                }

                if (_element.isEmpty()){
                    element.setError("thanh phan chinh");
                    element.setErrorEnabled(true);
                }else{
                    element.setErrorEnabled(false);
                    check++;
                }

                if (_messeger.isEmpty()){
                    messeger.setError("noi dung");
                    messeger.setErrorEnabled(true);
                }else{
                    messeger.setErrorEnabled(false);
                    check++;
                }

                if (_note.isEmpty()){
                    note.setError("ghi chu");
                    note.setErrorEnabled(true);
                }else{
                    note.setErrorEnabled(false);
                    check++;
                }


                if (check==16){
                    sp = new sanPham("id_auto_render",_name,_brand,radioButton.getText().toString(),_coin,_img1,_img2,_img3,_phone,_weight,_timeTest,_from,_element,_count,"0",_messeger,_note,radioButton_type.getText().toString(),"0");
                    sp_Dao.create_SanPham(sp,Acti_newPost.this);
                    check =0;
                    startActivity(new Intent(Acti_newPost.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(Acti_newPost.this, "khong the them san pham", Toast.LENGTH_SHORT).show();
                    check =0;
                }

            }
        });


    }
}