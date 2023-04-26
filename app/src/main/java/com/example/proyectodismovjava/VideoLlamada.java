package com.example.proyectodismovjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class VideoLlamada extends AppCompatActivity {
    private Switch mLanguageSwitch;
    private TextView Titulo, Cuerpo, Codigo;
    private Button Boton, Regrese;
    int sies = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            Bundle bundle = getIntent().getExtras();


            int adminVariable = getIntent().getIntExtra("admin", 0);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_video_llamada);
            Regrese = findViewById(R.id.ButonRegresa);
            Boton = findViewById(R.id.ButonVideo);
            Cuerpo = findViewById(R.id.VideoInfo);
            Titulo = findViewById(R.id.VideoTitulo);
            Codigo = findViewById(R.id.conferenceName);
            try {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(new URL(""))
                        .setFeatureFlag("welcomepage.enabled",false)
                        .build();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

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

            Button Video = findViewById(R.id.ButonVideo);
            Video.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editText = findViewById(R.id.conferenceName);
                String text = editText.getText().toString();
                if (text.length() > 0) {
                    JitsiMeetConferenceOptions options
                            = new JitsiMeetConferenceOptions.Builder()
                            .setRoom(text)
                            .build();
                    JitsiMeetActivity.launch(VideoLlamada.this, options);
                }
            }
            });

            Button Volver = findViewById(R.id.ButonRegresa);
            Volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adminVariable == sies) {
                        // El correo electronic pertenece a un administrador, abre la actividad de administrador
                        Intent intent = new Intent(VideoLlamada.this, ActividadDelAdministrador.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // El correo electrónico no pertenece a un administrador, regresa a la actividad normal
                        Intent intent = new Intent(VideoLlamada.this, PantallaPrincipal.class);
                        startActivity(intent);
                        finish();
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

                Titulo.setText(getString(R.string.VideoLlamadaTitulo));
                Cuerpo.setText(res.getString(R.string.VideoLlamadaCuerpo));
                Codigo.setHint(getString(R.string.VideoCodigo));
                Boton.setText(getString(R.string.VideoLlamadaBoton));
                Regrese.setText(getString(R.string.VideoLlamadaRegresar));
    }
}