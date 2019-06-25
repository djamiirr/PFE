package com.example.iwannathis.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.iwannathis.R;
import com.example.iwannathis.tools.entities.Article;
import com.example.iwannathis.tools.entities.EquipReservationEntity;
import com.example.iwannathis.tools.webservice.ProductsWebService;
import com.example.iwannathis.tools.webservice.callbacks.OnEquipmentsHistoryFetchListener;
import com.example.iwannathis.tools.webservice.callbacks.OnReservationClickListener;
import com.example.iwannathis.ui.adapters.EquipHistRVAdapter;
import com.example.iwannathis.ui.adapters.callbacks.OnItemClickListener;
import com.example.iwannathis.ui.dialogs.ReturnEquipmentDialog;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity implements OnReservationClickListener {

    private RecyclerView recyclerView;
    private EquipHistRVAdapter adapter;
    private List<EquipReservationEntity> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recycler_view);
        data = new ArrayList<>();
        adapter = new EquipHistRVAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);


        getHistory();

    }

    public void getHistory() {
        new ProductsWebService(this).getEquipmentHistory(new OnEquipmentsHistoryFetchListener() {
            @Override
            public void onSuccess(List<EquipReservationEntity> reservations) {
                findViewById(R.id.progress).setVisibility(View.GONE);
                data.clear();
                data.addAll(reservations);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                findViewById(R.id.progress).setVisibility(View.GONE);
                Toast.makeText(History.this, "Une erreur se produite", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(EquipReservationEntity reservation) {
        ReturnEquipmentDialog dialog = new ReturnEquipmentDialog(this, reservation.getIdRes(), reservation.getLabel());
        dialog.show();
    }
}
