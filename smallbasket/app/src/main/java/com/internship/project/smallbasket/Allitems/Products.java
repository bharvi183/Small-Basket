package com.internship.project.smallbasket.AllItems;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

class Products {


    private String pname;
    private String description;
    private String price;
    private String image;
    private String category;
    private String pid;
    private String date;
    private String time;
    private String no_of_product;
    private String psup_name;
    private String AddToCart;


    public Products() {
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNo_of_product(String no_of_product) {
        this.no_of_product = no_of_product;
    }


    public void setPsup_name(String psup_name) {
        this.psup_name = psup_name;
    }

    public String getPname() {
        return pname;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public String getPid() {
        return pid;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getNo_of_product() {
        return no_of_product;
    }


    public String getPsup_name() {
        return psup_name;
    }

    public String getAddToCart() {
        return AddToCart;
    }

    public void setAddToCart(String addToCart) {
        AddToCart = addToCart;
    }

    public Products(String pname, String description, String price, String image, String category, String pid, String date, String time, String no_of_product, String addtocart, String psup_name) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.no_of_product = no_of_product;
        this.psup_name = psup_name;
        this.AddToCart = addtocart;
    }
}
