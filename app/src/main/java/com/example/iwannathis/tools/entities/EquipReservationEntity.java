package com.example.iwannathis.tools.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EquipReservationEntity extends EquipmentProductEntity {

    @SerializedName("id_reserv")
    @Expose
    private int idRes;

    public int getIdRes() {
        return idRes;
    }

    public void setIdRes(int idRes) {
        this.idRes = idRes;
    }
}
