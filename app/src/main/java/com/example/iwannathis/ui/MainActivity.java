package com.example.iwannathis.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.iwannathis.R;
import com.example.iwannathis.tools.entities.Article;
import com.example.iwannathis.tools.entities.EquipmentProductEntity;
import com.example.iwannathis.tools.entities.ExpandableProductEntity;
import com.example.iwannathis.tools.webservice.ProductsWebService;
import com.example.iwannathis.tools.webservice.callbacks.OnProductsFetchListener;
import com.example.iwannathis.ui.adapters.EquipmentsRVAdapter;
import com.example.iwannathis.ui.adapters.ExpandablesRVAdapter;
import com.example.iwannathis.ui.adapters.callbacks.OnItemClickListener;
import com.example.iwannathis.ui.dialogs.ReserveEquipmentDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private BottomNavigationView navigation;
    private ProductsWebService productsWebService;
    private EquipmentsRVAdapter equipmentsAdapter;
    private ExpandablesRVAdapter expandablesAdapter;
    private List<EquipmentProductEntity> equipments;
    private List<ExpandableProductEntity> expandables;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initViews
        initViews();


        equipments = new ArrayList<>();
        expandables = new ArrayList<>();
        equipmentsAdapter = new EquipmentsRVAdapter(equipments);
        expandablesAdapter = new ExpandablesRVAdapter(expandables);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(equipmentsAdapter);

        //init products web service
        productsWebService = new ProductsWebService(this);

        //getting products
        productsWebService.getProducts(new OnProductsFetchListener() {
            @Override
            public void onCpmlete(List<EquipmentProductEntity> equipmentProducts, List<ExpandableProductEntity> expandableProduct) {
                equipments.addAll(equipmentProducts);
                equipmentsAdapter.notifyDataSetChanged();
                expandables.addAll(expandableProduct);
                expandablesAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });


        //setting listeners
        setListeners();
    }

    private void setListeners() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        expandablesAdapter.setOnItemClickListener(this);
        equipmentsAdapter.setOnItemClickListener(this);
    }


    private void initViews(){
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress);
        navigation = findViewById(R.id.navigation);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_equipments:
                    recyclerView.setAdapter(equipmentsAdapter);
                    return true;
                case R.id.navigation_expandables:
                    recyclerView.setAdapter(expandablesAdapter);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onClick(Article article) {

        Dialog reserveDialog = new ReserveEquipmentDialog(this, article);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(reserveDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        reserveDialog.show();
        reserveDialog.getWindow().setAttributes(lp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.hist:
                startActivity(new Intent(this, History.class));
                return true;
            case R.id.logout:
                getSharedPreferences("gest_res", Context.MODE_PRIVATE).edit().clear().apply();
                startActivity(new Intent(this, SplashScreen.class));
                finish();
        }
        return true;
    }


}
