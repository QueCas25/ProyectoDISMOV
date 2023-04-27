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

public class PantallaPrincipal extends AppCompatActivity {

    int admin;
    private Switch mLanguageSwitch;
    private Button Servicios, Productos, Contacto, Perfil, Chat, Videollamada, CerrarSesion;

    private TextView Usuario, Titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Usuario = findViewById(R.id.TituloMenuUsuario);
        Servicios = findViewById(R.id.BotonServiciosUsuario);
        Productos = findViewById(R.id.BotonProductosUsuario);
        Contacto = findViewById(R.id.BotonContactoUsuario);
        Perfil = findViewById(R.id.BotonPerfilUsuario);
        Chat = findViewById(R.id.BotonChatUsuario);
        Videollamada = findViewById(R.id.BotonVideollamadaUsuario);
        CerrarSesion = findViewById(R.id.BotonCerrarSesionUsuario);

        /*Comienzan funciones al aplanar botones*/
        Servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, ActividadDelChat.class);
                intent.putExtra("admin", 1);
            }
        });

        Productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, ActividadDelChat.class);
                intent.putExtra("admin", 1);


            }
        });

        Contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, ActividadContacto.class);
                startActivity(intent);

            }
        });

        Perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, ActividadDelChat.class);
                intent.putExtra("admin", 1);

            }
        });

        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, ActividadDelChat.class);
                startActivity(intent);
            }
        });

        Videollamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, VideoLlamada.class);

                startActivity(intent);
            }
        });

        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, InicioDeSesion.class);
                startActivity(intent);
            }
        });

        /*Terminan funciones al aplanar botones*/


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

    /* Funcion de Cambio de idioma */
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = res.getConfiguration();
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        Usuario.setText(res.getString(R.string.TituloUsuarioEnMenu));
        Servicios.setText(res.getString(R.string.btnServicios));
        Productos.setText(res.getString(R.string.BotonProductos));
        Contacto.setText(res.getString(R.string.btnContacto));
        Perfil.setText(res.getString(R.string.btnPerfil));
        Chat.setText(getString(R.string.BotonChat));
        Videollamada.setText(getString(R.string.BotonVideoLlamada));
        CerrarSesion.setText(getString(R.string.BotonRegresar));

    }

}