package com.example.iwannathis.tools.webservice.callbacks;

import com.example.iwannathis.tools.entities.EquipmentProductEntity;
import com.example.iwannathis.tools.entities.ExpandableProductEntity;

import java.util.List;

public interface OnProductsFetchListener {

    void onCpmlete(List<EquipmentProductEntity> equipmentProduct, List<ExpandableProductEntity> expandableProduct);

    void onError();

}
