package com.example.proyectodismovjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;


public class PantallaPrincipal extends AppCompatActivity {
    int admin;
    private Switch mLanguageSwitch;
    private Button Regresar, VideoLlamada, Chat, Carrito, Producto;
    private TextView Titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);


        Regresar = findViewById(R.id.Volver);
        VideoLlamada = findViewById(R.id.contact_btn3);
        Chat = findViewById(R.id.contact_btn);
        Producto = findViewById(R.id.contact_btn4);
        Carrito = findViewById(R.id.contact_btn2);

        Button Chat = findViewById(R.id.contact_btn);
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, ActividadDelChat.class);
                startActivity(intent);
            }
        });

        Button Volver = findViewById(R.id.Volver);
        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, InicioDeSesion.class);
                startActivity(intent);
            }
        });

        Button VideoCall= findViewById(R.id.contact_btn3);
        VideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, VideoLlamada.class);
                startActivity(intent);
            }
        });

        Button Carritos = findViewById(R.id.contact_btn2);
        Carritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, Productos.class);
                startActivity(intent);
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

        Producto.setText(res.getString(R.string.BotonProductos));
        Carrito.setText(res.getString(R.string.CarritoUsuario));
        VideoLlamada.setText(getString(R.string.BotonVideoLlamada));
        Chat.setText(getString(R.string.BotonChat));
        Regresar.setText(getString(R.string.BotonRegresar));

    }

}