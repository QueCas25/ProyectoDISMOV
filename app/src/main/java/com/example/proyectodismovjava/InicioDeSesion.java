package com.example.proyectodismovjava;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.CompoundButton;
import java.util.Locale;

public class InicioDeSesion extends AppCompatActivity {

         private TextView appname;
            private FirebaseAuth mAuth;
            private Switch mLanguageSwitch;
            private TextInputLayout mEmailLayout;
            private TextInputLayout mPasswordLayout;
            private TextInputEditText mEmailField;
            private TextInputEditText mPasswordField;
            private Button mLoginButton;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_inicio_de_sesion);

                mAuth = FirebaseAuth.getInstance();

                mEmailLayout = findViewById(R.id.email_layout);
                mPasswordLayout = findViewById(R.id.password_layout);
                mEmailField = findViewById(R.id.email_field);
                mPasswordField = findViewById(R.id.password_field);
                mLoginButton = findViewById(R.id.login_button);
                appname = findViewById(R.id.textView);

                mLoginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = mEmailField.getText().toString();
                        String password = mPasswordField.getText().toString();

                        if (TextUtils.isEmpty(email)) {
                            mEmailLayout.setError("Por favor ingrese su correo electrónico.");
                            return;
                        }

                        if (TextUtils.isEmpty(password)) {
                            mPasswordLayout.setError("Por favor ingrese su contraseña.");
                            return;
                        }


                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(InicioDeSesion.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            if (user != null && user.isEmailVerified()) {
                                                Toast.makeText(InicioDeSesion.this, "Ya entre al punto", Toast.LENGTH_SHORT).show();
                                                // Verificar si el correo electrónico pertenece a un administrador
                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                db.collection("administradores")
                                                        .whereEqualTo("email", email)
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                                                    // El correo electrónico pertenece a un administrador, abre la actividad de administrador
                                                                    Intent intent = new Intent(InicioDeSesion.this, ActividadDelAdministrador.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                } else {
                                                                    // El correo electrónico no pertenece a un administrador, abre la actividad normal
                                                                    Intent intent = new Intent(InicioDeSesion.this, PantallaPrincipal.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            }
                                                        });
                                            } else {
                                                // Correo electrónico no verificado
                                                Toast.makeText(InicioDeSesion.this, "Debe verificar su correo electrónico antes de iniciar sesión.", Toast.LENGTH_SHORT).show();
                                                mAuth.signOut();
                                            }
                                        } else {
                                            // Inicio de sesión fallido
                                            Toast.makeText(InicioDeSesion.this, "Inicio de sesión fallido.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                        TextView textView = findViewById(R.id.textView);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), Registro.class);
                                startActivity(intent);
                            }
                        });
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

                        appname.setText(res.getString(R.string.app_name));
                        mEmailLayout.setHint(getString(R.string.emaillogin));
                    }
                }




