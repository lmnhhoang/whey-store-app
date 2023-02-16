package com.example.wheystore_nhom6.Model;

import java.util.Date;

public class hoaDon {
    String id,idPost, userName,title,coin,img1,phone,adress,status;
    int value;
    String email;
    Date date;


    public hoaDon() {
    }

    public hoaDon(String id, String idPost, String userName, String title, String coin, String img1, String phone, String adress, String status, int value, String email, Date date) {
        this.id = id;
        this.idPost = idPost;
        this.userName = userName;
        this.title = title;
        this.coin = coin;
        this.img1 = img1;
        this.phone = phone;
        this.adress = adress;
        this.status = status;
        this.value = value;
        this.email = email;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
