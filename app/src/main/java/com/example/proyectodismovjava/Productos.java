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

public class Productos extends AppCompatActivity {
    int sies = 1;
    private Button ExtintorPQS, ExtintorCO2, ExtintorADP, Regresar;
    private Switch mLanguageSwitch;
    private TextView ProductoTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        ExtintorPQS = findViewById(R.id.BotonExtintorPQS);
        ExtintorCO2 = findViewById(R.id.BotonExtintorCO2);
        ExtintorADP = findViewById(R.id.BotonExtintorADP);
        Regresar = findViewById(R.id.BotonRegresar);
        int adminVariable = getIntent().getIntExtra("admin", 0);

        ExtintorPQS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminVariable == sies) {
                    // El correo electronic pertenece a un administrador, abre la actividad de administrador
                    Intent intent = new Intent(Productos.this, ExtintoresPQS.class);
                    intent.putExtra("admin", 1);
                    startActivity(intent);
                    finish();
                } else {
                    // El correo electrónico no pertenece a un administrador, regresa a la actividad normal
                    Intent intent = new Intent(Productos.this, ExtintoresPQS.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ExtintorCO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminVariable == sies) {
                    // El correo electronic pertenece a un administrador, abre la actividad de administrador
                    Intent intent = new Intent(Productos.this, ExtintoresCO2.class);
                    intent.putExtra("admin", 1);
                    startActivity(intent);
                    finish();
                } else {
                    // El correo electrónico no pertenece a un administrador, regresa a la actividad normal
                    Intent intent = new Intent(Productos.this, ExtintoresCO2.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        ExtintorADP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminVariable == sies) {
                    // El correo electronic pertenece a un administrador, abre la actividad de administrador
                    Intent intent = new Intent(Productos.this, ExtintoresADP.class);
                    intent.putExtra("admin", 1);
                    startActivity(intent);
                    finish();
                } else {
                    // El correo electrónico no pertenece a un administrador, regresa a la actividad normal
                    Intent intent = new Intent(Productos.this, ExtintoresADP.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminVariable == sies) {
                    // El correo electronic pertenece a un administrador, abre la actividad de administrador
                    Intent intent = new Intent(Productos.this, ActividadDelAdministrador.class);
                    startActivity(intent);
                    finish();
                } else {
                    // El correo electrónico no pertenece a un administrador, regresa a la actividad normal
                    Intent intent = new Intent(Productos.this, PantallaPrincipal.class);
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

        ProductoTitulo.setText(res.getString(R.string.TituloProductos));
        ExtintorCO2.setText(res.getString(R.string.DioxidoDeCarbonoBtn));
        ExtintorADP.setText(res.getString(R.string.AcetatoBtn));
        ExtintorADP.setText(res.getString(R.string.AcetatoBtn));
        ExtintorADP.setText(res.getString(R.string.BotonRegresar));
    }



}