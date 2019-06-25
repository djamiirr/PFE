package com.example.iwannathis.tools.webservice.callbacks;

import com.example.iwannathis.tools.entities.EquipReservationEntity;

import java.util.List;

public interface OnEquipmentsHistoryFetchListener {
    void onSuccess(List<EquipReservationEntity> reservation);
    void onError();
}
