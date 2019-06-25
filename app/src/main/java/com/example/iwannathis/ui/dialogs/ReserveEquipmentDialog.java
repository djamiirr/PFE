package com.example.iwannathis.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iwannathis.R;
import com.example.iwannathis.tools.entities.Article;
import com.example.iwannathis.tools.entities.EquipmentProductEntity;
import com.example.iwannathis.tools.entities.ExpandableProductEntity;
import com.example.iwannathis.tools.webservice.ProductsWebService;
import com.example.iwannathis.tools.webservice.callbacks.OnReservationCompleteListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReserveEquipmentDialog extends Dialog {

    private boolean isEquipment;
    private TextView titleTV;
    private Button startDateBtn, endDateBtn;
    private String startDate, endDate;
    private Spinner qteS;
    private Button reserveBtn;
    private Article article;
    private ArrayAdapter<Integer> aa;
    private List<Integer> qte;

    public ReserveEquipmentDialog(@NonNull Context context, Article article) {
        super(context);
        this.article = article;
        isEquipment =  article instanceof EquipmentProductEntity;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_reserve_equipment);

        //init views
        initViews();

        reserveBtn.requestFocus();

        qte = new ArrayList<>();

        if(!isEquipment){
            endDateBtn.setVisibility(View.GONE);
            findViewById(R.id.end_date_tv).setVisibility(View.GONE);
        }

        for (int i = 1; i <= (article.getQte() - article.getReserved()); i++)
            qte.add(i);

        aa = new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_item, qte);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        qteS.setAdapter(aa);

        titleTV.setText("Réserver " + article.getLabel());

        setListener();

    }

    private void setListener() {
        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article art = article instanceof EquipmentProductEntity ? new EquipmentProductEntity() : new ExpandableProductEntity();

                art.setQte(Integer.parseInt(qteS.getSelectedItem().toString()));
                art.setId(article.getId());
                art.dateRes = startDate;
                art.dateRet = endDate;
                new ProductsWebService(getContext()).reserveProduct(art, new OnReservationCompleteListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getContext(), "Réservation réussite", Toast.LENGTH_LONG).show();
                        ReserveEquipmentDialog.this.dismiss();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getContext(), "Une erreur se produite", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clicked", "true");
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(getContext(), android.R.style.ThemeOverlay_Material_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDateBtn.setText(dayOfMonth + "/" + month + "/" + year);
                        startDate = year + "-" + month + "-" + dayOfMonth;
                        reserveBtn.requestFocus();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clicked", "true");
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(getContext(), android.R.style.ThemeOverlay_Material_Dialog_Alert, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDateBtn.setText(dayOfMonth + "/" + month + "/" + year);
                        endDate = year + "-" + month + "-" + dayOfMonth;
                        reserveBtn.requestFocus();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        }

    private void initViews() {
        qteS = findViewById(R.id.qte);
        reserveBtn = findViewById(R.id.reserve_btn);
        startDateBtn = findViewById(R.id.start_date);
        endDateBtn = findViewById(R.id.end_date);
        titleTV = findViewById(R.id.title);
    }



}
