package com.example.iwannathis.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iwannathis.R;
import com.example.iwannathis.tools.webservice.ProductsWebService;
import com.example.iwannathis.tools.webservice.callbacks.OnReservationCompleteListener;

public class ReturnEquipmentDialog extends Dialog {

    private Button returnBtn;
    private TextView titleTV;
    private EditText defET;
    private int id;
    private String title;

    public ReturnEquipmentDialog(@NonNull Context context, int id, String title) {
        super(context);
        this.id = id;
        this.title = title;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_return_equipment);

        //init views
        initViews();

        titleTV.setText("Retourner " + title);

        setListener();

    }

    private void setListener() {
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProductsWebService(getContext()).returnEquip(ReturnEquipmentDialog.this.id, defET.getText().toString(), new OnReservationCompleteListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getContext(), "Retour declaré", Toast.LENGTH_SHORT).show();
                        ReturnEquipmentDialog.this.dismiss();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(getContext(), "Une erreur se produite", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initViews() {
        returnBtn = findViewById(R.id.return_btn);
        titleTV = findViewById(R.id.title);
        defET = findViewById(R.id.def);
    }


}
