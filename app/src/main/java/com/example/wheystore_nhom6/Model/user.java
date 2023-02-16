package com.example.wheystore_nhom6.Model;

public class user {
    String userName,pass,pass2,email,phone,adress;

    public user() {
    }

    public user(String userName, String pass, String pass2, String email, String phone, String adress) {
        this.userName = userName;
        this.pass = pass;
        this.pass2 = pass2;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
    }

    public String getUserName() {
        return userName;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
