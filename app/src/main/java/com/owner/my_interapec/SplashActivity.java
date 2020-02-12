package com.owner.my_interapec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    int duracion = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                screenInicial();
            }
        },duracion);
    }

    public void screenInicial() {
        SharedPreferences preferencias = getSharedPreferences("datousuario", Context.MODE_PRIVATE);
        String isFirst = preferencias.getString("isFirst", "");

        if(isFirst.trim().isEmpty()) {
                startActivity(new Intent(SplashActivity.this, BienvenidaActivity.class));
                finish();
        } else {
             startActivity(new Intent(SplashActivity.this,MainActivity.class));
           // startActivity(new Intent(SplashActivity.this, AddActivity.class));
            finish();
        }

    }
}
