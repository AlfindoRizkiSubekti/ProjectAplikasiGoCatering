package com.example.catering.Model;

public class Products {
    private String pname, description, price, image, category, pid, date, time, nasi1, nasi2, lauk1, lauk2, lauk2_1, lauk2_2;

    public Products(){

    }

    public Products(String pname, String description, String price, String image, String category, String pid, String date, String time, String nasi1, String nasi2, String lauk1, String lauk2, String lauk2_1, String lauk2_2) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.nasi1 = nasi1;
        this.nasi2 = nasi2;
        this.lauk1 = lauk1;
        this.lauk2 = lauk2;
        this.lauk2_1 = lauk2_1;
        this.lauk2_2 = lauk2_2;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNasi1() {
        return nasi1;
    }

    public void setNasi1(String nasi1) {
        this.nasi1 = nasi1;
    }

    public String getNasi2() {
        return nasi2;
    }

    public void setNasi2(String nasi2) {
        this.nasi2 = nasi2;
    }

    public String getLauk1() {
        return lauk1;
    }

    public void setLauk1(String lauk1) {
        this.lauk1 = lauk1;
    }

    public String getLauk2() {
        return lauk2;
    }

    public void setLauk2(String lauk2) {
        this.lauk2 = lauk2;
    }

    public String getLauk2_1() {
        return lauk2_1;
    }

    public void setLauk2_1(String lauk2_1) {
        this.lauk2_1 = lauk2_1;
    }

    public String getLauk2_2() {
        return lauk2_2;
    }

    public void setLauk2_2(String lauk2_2) {
        this.lauk2_2 = lauk2_2;
    }
}
