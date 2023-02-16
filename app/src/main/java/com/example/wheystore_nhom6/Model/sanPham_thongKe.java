package com.example.wheystore_nhom6.Model;

import java.util.Date;

public class sanPham_thongKe {
    String idPost,img,title,xuat;
    String coin;
    Date date;

    public sanPham_thongKe(String idPost, String img, String title, String xuat, String coin, Date date) {
        this.idPost = idPost;
        this.img = img;
        this.title = title;
        this.xuat = xuat;
        this.coin = coin;
        this.date = date;
    }

    public sanPham_thongKe() {
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getXuat() {
        return xuat;
    }

    public void setXuat(String xuat) {
        this.xuat = xuat;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
