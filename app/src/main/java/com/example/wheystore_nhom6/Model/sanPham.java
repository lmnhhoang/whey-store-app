package com.example.wheystore_nhom6.Model;

import java.io.Serializable;

public class sanPham implements Serializable {
    String ID,name,brand,value,coin,img1,img2,img3,phone,weight,timeTest,from,element,count,xuat,messeger, note,type,view;

    public sanPham() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTimeTest() {
        return timeTest;
    }

    public void setTimeTest(String timeTest) {
        this.timeTest = timeTest;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getXuat() {
        return xuat;
    }

    public void setXuat(String xuat) {
        this.xuat = xuat;
    }

    public String getMesseger() {
        return messeger;
    }

    public void setMesseger(String messeger) {
        this.messeger = messeger;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public sanPham(String ID, String name, String brand, String value, String coin, String img1, String img2, String img3, String phone, String weight, String timeTest, String from, String element, String count, String xuat, String messeger, String note, String type, String view) {
        this.ID = ID;
        this.name = name;
        this.brand = brand;
        this.value = value;
        this.coin = coin;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.phone = phone;
        this.weight = weight;
        this.timeTest = timeTest;
        this.from = from;
        this.element = element;
        this.count = count;
        this.xuat = xuat;
        this.messeger = messeger;
        this.note = note;
        this.type = type;
        this.view = view;
    }
}
