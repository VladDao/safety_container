package com.plietnov.task.entity;

import java.io.Serializable;

public abstract class Product implements Serializable {

    private int id;
    private String nameOfProduct;

    public Product() {
    }

    public Product(int id, String nameOfProduct) {
        this.id = id;
        this.nameOfProduct = nameOfProduct;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public void setNameOfProduct(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
