package com.example.iwannathis.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.iwannathis.R;
import com.example.iwannathis.tools.webservice.Links;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.iwannathis.tools.webservice.Links.BASE_URL;
import static com.example.iwannathis.tools.webservice.Links.PRODUCTS;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {
            InputStream instream = new FileInputStream("/sdcard/host.txt");
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            while (( line = buffreader.readLine()) != null) {
                BASE_URL = line + "/pfe/ws/index.php/";
                Links.IMG = line + "/pfe/images/";
                PRODUCTS = BASE_URL + "products/";
                Links.EXPANDABLES = PRODUCTS + "exp/";
                Links.EQUIPMENTS = PRODUCTS + "equip/";
                Links.LOGIN = BASE_URL + "Employee/login/";

            }
            instream.close();
        } catch (Exception e) {
            Log.e("error splash", e.getMessage());
        }

        SharedPreferences sharedPreferences = getSharedPreferences("gest_res", Context.MODE_PRIVATE);

        startActivity(new Intent(this, sharedPreferences.contains("idEmp") ? MainActivity.class : LoginActivity.class));
        finish();
    }
}
