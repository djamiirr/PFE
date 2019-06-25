package com.example.iwannathis.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iwannathis.R;
import com.example.iwannathis.tools.webservice.LoginWebService;
import com.example.iwannathis.tools.webservice.callbacks.OnLoginListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mailET, passwdET;
    private TextView loginTV;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init views
        initViews();

    }


    private void initViews(){
        mailET = findViewById(R.id.mail_et);
        passwdET = findViewById(R.id.passwd_et);
        loginTV = findViewById(R.id.login_btn_txt);
        progressBar = findViewById(R.id.login_progress_bar);
    }

    public void login(View v){
        loginTV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        new LoginWebService(this).login(mailET.getText().toString(), passwdET.getText().toString(), new OnLoginListener() {
            @Override
            public void onSuccess() {
                loginTV.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(boolean cnxError) {
                loginTV.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Veuillez  verifier votre " + (cnxError ? "connexion internet" : "email/mot de passe"), Toast.LENGTH_LONG).show();
            }
        });
    }
}
