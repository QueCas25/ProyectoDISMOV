package com.example.proyectodismovjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.proyectodismovjava.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

        private TextInputEditText usernameField, emailField, passwordField, confirmPasswordField;
        private Button registerButton;
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
            emailField = findViewById(R.id.email_field);
            passwordField = findViewById(R.id.password_field);
            confirmPasswordField = findViewById(R.id.confirm_password_field);
            registerButton = findViewById(R.id.register_button);

            // Handle register button click
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = usernameField.getText().toString().trim();
                    String email = emailField.getText().toString().trim();
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

            TextView textView = findViewById(R.id.textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), InicioDeSesion.class);
                    startActivity(intent);
                }
            });
        }

}