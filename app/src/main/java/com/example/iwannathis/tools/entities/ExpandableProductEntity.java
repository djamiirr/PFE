package com.example.iwannathis.tools.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpandableProductEntity extends Article{


    @SerializedName("lib_marque")
    @Expose
    private String brand;

    @SerializedName("couleur")
    @Expose
    private String color;

    @SerializedName("url")
    @Expose
    private String img;



    public int getReserved() {
        return reserved;
    }

    public int dispo(){
        return this.qte - this.reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this, ExpandableProductEntity.class);
    }
}
