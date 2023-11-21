package com.example.pruebafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;
    Button btn;
    int counter = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.ProgressB);
        btn = findViewById(R.id.btn_ingresar);

        btn.setOnClickListener(view ->{
            pb.setVisibility(View.VISIBLE);
            counter = 0;
            startProgress();
        });
    }

    private void startProgress() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter);
                if (counter == 100) {
                    pb.setVisibility(View.INVISIBLE);
                    Intent otraVentana = new Intent(MainActivity.this, CrudActivity.class);
                    startActivity(otraVentana);
                } else {
                    // Sigue actualizando el progreso
                    startProgress();
                }
            }
        }, 50);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restablecer el progreso cuando se reanuda la actividad
        pb.setVisibility(View.INVISIBLE);
    }
}

