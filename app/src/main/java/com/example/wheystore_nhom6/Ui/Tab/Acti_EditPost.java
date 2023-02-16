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
import com.example.wheystore_nhom6.DAO.sanPham_thongKe_DAO;
import com.example.wheystore_nhom6.MainActivity;
import com.example.wheystore_nhom6.Model.sanPham;
import com.example.wheystore_nhom6.Model.sanPham_thongKe;
import com.example.wheystore_nhom6.R;
import com.google.android.material.textfield.TextInputLayout;

public class Acti_EditPost extends AppCompatActivity {

    TextInputLayout name,brand,coin,img1,img2,img3,phone,weight,timeTest,from,element,count,messeger,note;
    String _name,_brand,_coin,_img1,_img2,_img3,_phone,_weight,_timeTest,_from,_element,_count,_messeger,_note;
    int _value = 0;
    int _type = 0;
    RadioButton radioButton,radioButton_type;
    sanPham_Dao sp_Dao;
    sanPham_thongKe_DAO thongKe_Dao;
    sanPham_thongKe sanPham_thongKe;
    sanPham sp;

    int check = 0;

    RadioGroup value,type;
    TextView create;
    RadioButton _1,_2,_3,_4,_5;
    RadioButton _whey,_mass,_bcaa,_eaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_edit_post);

        sp_Dao = new sanPham_Dao();
        thongKe_Dao = new sanPham_thongKe_DAO();

        name = findViewById(R.id.edt_edit_name);
        brand = findViewById(R.id.edt_edit_brand);
        //CheckBox
        value = findViewById(R.id.radioGroup_edit_value);
        type = findViewById(R.id.radioGroup_edit_type);
        //check value
        _1 = findViewById(R.id.rdo_1_edit);
        _2 = findViewById(R.id.rdo_2_edit);
        _3 = findViewById(R.id.rdo_3_edit);
        _4 = findViewById(R.id.rdo_4_edit);
        _5 = findViewById(R.id.rdo_5_edit);
        //check type
        _whey = findViewById(R.id.rdo_whey_edit);
        _mass = findViewById(R.id.rdo_mass_edit);
        _bcaa = findViewById(R.id.rdo_bcaa_edit);
        _eaa = findViewById(R.id.rdo_eaa_edit);
        //text
        coin = findViewById(R.id.edt_edit_coin);
        img1 = findViewById(R.id.edt_edit_img1);
        img2 = findViewById(R.id.edt_edit_img2);
        img3 = findViewById(R.id.edt_edit_img3);
        phone = findViewById(R.id.edt_edit_phone);
        weight = findViewById(R.id.edt_edit_weight);
        timeTest = findViewById(R.id.edt_edit_timeTest);
        from = findViewById(R.id.edt_edit_from);
        element = findViewById(R.id.edt_edit_element);
        count = findViewById(R.id.edt_edit_count);
        messeger = findViewById(R.id.edt_edit_massages);
        note = findViewById(R.id.edt_edit_note);

        Bundle bundle = getIntent().getExtras();
        if (bundle==null){
            return;
        }
        sp = new sanPham();
        sp = (sanPham) bundle.get("data");
        name.getEditText().setText(sp.getName());
        brand.getEditText().setText(sp.getBrand());
        coin.getEditText().setText(sp.getCoin());
        img1.getEditText().setText(sp.getImg1());
        img2.getEditText().setText(sp.getImg2());
        img3.getEditText().setText(sp.getImg3());
        phone.getEditText().setText(sp.getPhone());
        weight.getEditText().setText(sp.getWeight());
        timeTest.getEditText().setText(sp.getTimeTest());
        from.getEditText().setText(sp.getFrom());
        element.getEditText().setText(sp.getElement());
        count.getEditText().setText(sp.getCount());
        messeger.getEditText().setText(sp.getMesseger());
        note.getEditText().setText(sp.getNote());


        if (sp.getValue().equals("1")){
            _1.setChecked(true);
        }else if (sp.getValue().equals("2")){
            _2.setChecked(true);
        }else if(sp.getValue().equals("3")){
            _3.setChecked(true);
        }else if(sp.getValue().equals("4")){
            _4.setChecked(true);
        }else{
            _5.setChecked(true);
        }

        if (sp.getType().equals("whey")){
            _whey.setChecked(true);
        }else if (sp.getType().equals("mass")){
            _mass.setChecked(true);
        }else if(sp.getType().equals("bcaa")){
            _bcaa.setChecked(true);
        }else{
            _eaa.setChecked(true);
        }

        create = findViewById(R.id.tv_hello_edit_Post);
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
                    Toast.makeText(Acti_EditPost.this, "danh gai chua duoc chon", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    radioButton = findViewById(_value);
                    Toast.makeText(Acti_EditPost.this, "danh gia : "+radioButton.getText()+" SAO", Toast.LENGTH_SHORT).show();
                    check++;
                }

                _type =type.getCheckedRadioButtonId();

                if (_type<0){
                    Toast.makeText(Acti_EditPost.this, "loai chua duoc chon", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    radioButton_type = findViewById(_type);
                    Log.e("TAG", "radioButton_type: "+radioButton_type.getText());
                    Toast.makeText(Acti_EditPost.this, "loai : "+radioButton.getText(), Toast.LENGTH_SHORT).show();
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

                if (_count.isEmpty()){
                    count.setError("so luong");
                    count.setErrorEnabled(true);
                }else{
                    count.setErrorEnabled(false);
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
                    sp = new sanPham(sp.getID(),_name,_brand,radioButton.getText().toString(),_coin,_img1,_img2,_img3,_phone,_weight,_timeTest,_from,_element,_count,sp.getXuat(),_messeger,_note,radioButton_type.getText().toString(),sp.getView());
                    sp_Dao.Edit_Post(sp,sp.getID(),Acti_EditPost.this);
                    startActivity(new Intent(Acti_EditPost.this, MainActivity.class));
                    check =0;
                    finish();
                    Toast.makeText(Acti_EditPost.this, "Okk", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Acti_EditPost.this, "khong the them san pham", Toast.LENGTH_SHORT).show();
                    check =0;
                }
            }
        });
    }
}