package com.example.proyectodismovjava;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ExtintoresADP extends AppCompatActivity {
    private Button Regresar;
    int sies = 1;
    private Switch mLanguageSwitch;
    private TextView ADPTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extintores_adp);
        int adminVariable = getIntent().getIntExtra("admin", 0);

        ADPTitulo = findViewById(R.id.AcetatoTitulo);
        Regresar = findViewById(R.id.ADP_BotonRegresar);

        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adminVariable == sies) {
                    // El correo electronic pertenece a un administrador, abre la actividad de administrador
                    Intent intent = new Intent(ExtintoresADP.this, Productos.class);
                    intent.putExtra("admin", 1);
                    startActivity(intent);
                    finish();
                } else {
                    // El correo electrónico no pertenece a un administrador, regresa a la actividad normal
                    Intent intent = new Intent(ExtintoresADP.this, Productos.class);

                    startActivity(intent);
                    finish();
                }
            }
        });

        mLanguageSwitch = findViewById(R.id.language_switch);
        mLanguageSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setLocale("en");
                } else {
                    setLocale("es");
                }
            }
        });



    }
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());

        ADPTitulo.setText(res.getString(R.string.AcetatoTitulo));
        Regresar.setText(res.getString(R.string.VideoLlamadaRegresar));
    }


}