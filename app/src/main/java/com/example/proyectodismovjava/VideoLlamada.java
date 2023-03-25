package com.example.proyectodismovjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class VideoLlamada extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_video_llamada);
            try {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(new URL(""))
                        .setFeatureFlag("welcomepage.enabled",false)
                        .build();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        public void onButtonClick(View v) {
            EditText editText = findViewById(R.id.conferenceName);
            String text = editText.getText().toString();
            if (text.length() > 0) {
                JitsiMeetConferenceOptions options
                        = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(text)
                        .build();
                JitsiMeetActivity.launch(this, options);
            }
        }

    }
