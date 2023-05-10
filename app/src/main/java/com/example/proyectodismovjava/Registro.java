package com.example.proyectodismovjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Registro extends AppCompatActivity {
    private Switch mLanguageSwitch;
    private TextView TextoBajo, RegistroTitle, RegistroSubTitle;
    private TextInputEditText usernameField, emailField, passwordField, confirmPasswordField;
    private Button registerButton;
    private TextInputLayout usernameLayout, emailLayout, passwordLayout, confirmPasswordLayout;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Initialize Firebase Auth and Database references
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        // Bind UI elements
        usernameField = findViewById(R.id.username_field);
        usernameLayout = findViewById(R.id.UsuarioRe);
        emailField = findViewById(R.id.email_field);
        emailLayout = findViewById(R.id.CorreoRe);
        passwordField = findViewById(R.id.password_field);
        passwordLayout = findViewById(R.id.ContrasenaRe);
        confirmPasswordField = findViewById(R.id.confirm_password_field);
        confirmPasswordLayout = findViewById(R.id.ConfirmarRe);
        registerButton = findViewById(R.id.register_button);
        TextoBajo = findViewById(R.id.textoBajo);
        RegistroTitle= findViewById(R.id.TituloRegistro);
        RegistroSubTitle= findViewById(R.id.SubTemaRegistro);


        // Handle register button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String confirmPassword = confirmPasswordField.getText().toString();

                // Check if all fields are filled out
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Please confirm your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if password and confirm password match
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // Send verification email to user
                                    user.sendEmailVerification();

                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put("name", username);
                                    userMap.put("email", email);
                                    mDatabase.child("users").child(user.getUid()).setValue(userMap);
                                    db.collection("users")
                                            .document(user.getUid())
                                            .set(userMap)
                                            // Save user data in Firebase Database
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(Registro.this, "Registro exitoso.", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Registro.this, InicioDeSesion.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Registro.this, "Error al guardar los datos del usuario.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    // Registro fallido
                                    Toast.makeText(Registro.this, "Registro fallido.", Toast.LENGTH_SHORT).show();
                                }
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

        TextoBajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InicioDeSesion.class);
                startActivity(intent);
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

        usernameLayout.setHint(getString(R.string.UsuaRe));
        emailLayout.setHint(res.getString(R.string.CorreoReg));
        passwordLayout.setHint(getString(R.string.ContraRe));
        confirmPasswordLayout.setHint(getString(R.string.ConfRe));
        registerButton.setText(getString(R.string.BotonRe));
        TextoBajo.setText(getString(R.string.IniciaRe));
        RegistroTitle.setText(res.getString(R.string.TituloRe));
        RegistroSubTitle.setText(res.getString(R.string.SubTemaDeRegistro));
    }
}
