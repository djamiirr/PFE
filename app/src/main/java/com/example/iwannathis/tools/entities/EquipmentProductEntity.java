package com.example.iwannathis.tools.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EquipmentProductEntity extends Article{


    @SerializedName("lib_marque")
    @Expose
    private String brand;

    @SerializedName("url")
    @Expose
    private String img;

    @SerializedName("lib_model")
    @Expose
    private String model;


    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int dispo(){
        return this.qte - this.reserved;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQte() {
        return this.qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this, EquipmentProductEntity.class);
    }
}
