package com.example.iwannathis.tools.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class  Article {


    @SerializedName(value = "id", alternate = {"id_cons", "id_equip"})
    @Expose
    protected int id;

    @SerializedName(value = "label", alternate = {"lib_equip", "lib_cons"})
    @Expose
    protected String label;

    @SerializedName(value = "qte", alternate = {"qte_tot"})
    @Expose
    protected int qte;

    @SerializedName("reserved")
    @Expose
    protected int reserved;


    public String dateRet;
    public String dateRes;
    public String status;


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


    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }
}
