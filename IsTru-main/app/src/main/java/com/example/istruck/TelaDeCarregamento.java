package com.example.istruck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class TelaDeCarregamento extends AppCompatActivity {

    private final Timer timer = new Timer();
    TimerTask timertask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_carregamento);

        getSupportActionBar().hide();

        timertask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gotoMainActivity();
                    }
                });
            }
        };
        timer.schedule(timertask, 3500);
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(TelaDeCarregamento.this,FormLogin.class);
        startActivity(intent);
    }
}