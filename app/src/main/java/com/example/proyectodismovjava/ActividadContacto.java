package com.example.proyectodismovjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;

public class ActividadContacto extends AppCompatActivity {
    private Switch mLanguageSwitch;
    private Button Regresar;

    int sies = 1;
    private TextView Titulo, sub1, sub2, sub3, sub4, sub5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        int adminVariable = getIntent().getIntExtra("admin", 0);
        Titulo  = findViewById(R.id.contactotitulo);
        sub1 = findViewById(R.id.subTituloEnLogin);
        sub2 = findViewById(R.id.subTituloEnLogin2);
        sub3 = findViewById(R.id.subTituloEnLogin3);
        sub4 = findViewById(R.id.subTituloEnLogin4);
        sub5 = findViewById(R.id.subTituloEnLogin5);
        Regresar = findViewById(R.id.login_button3);

        Regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminVariable == sies) {
                    // El correo electronic pertenece a un administrador, abre la actividad de administrador
                    Intent intent = new Intent(ActividadContacto.this, ActividadDelAdministrador.class);
                    startActivity(intent);
                    finish();
                } else {
                    // El correo electrónico no pertenece a un administrador, regresa a la actividad normal
                    Intent intent = new Intent(ActividadContacto.this, PantallaPrincipal.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        /*Switch para Cambio de idioma */
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
        Titulo.setText(res.getString(R.string.ContactoTitulo));
        sub1.setText(res.getString(R.string.Datos));
        sub2.setText(res.getString(R.string.Datos2));
        sub3.setText(res.getString(R.string.Datos3));
        sub4.setText(res.getString(R.string.Datos4));
        sub5.setText(res.getString(R.string.Datos5));
        Regresar.setText(getString(R.string.Regresar));

    }
}