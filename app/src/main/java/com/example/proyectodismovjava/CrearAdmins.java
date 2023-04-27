package com.example.proyectodismovjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectodismovjava.activities.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CrearAdmins extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference usersRef, adminsRef;
    private RecyclerView recyclerView;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_admins);

        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("users");
        adminsRef = db.collection("administradores");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearAdmins.this, ActividadDelAdministrador.class);
                startActivity(intent);
            }
        });
        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);

        usersRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore", "Error al obtener los usuarios", error);
                    return;
                }

                List<User> users = new ArrayList<>();
                for (DocumentSnapshot doc : value) {
                    User user = doc.toObject(User.class);
                    users.add(user);
                }

                adapter.setUsers(users);
            }
        });
    }

    private class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

        private List<User> users = new ArrayList<>();

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_item, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            User user = users.get(position);
            holder.emailTextView.setText(user.getEmail());
            holder.nombreTextView.setText(user.getName());
            holder.agregarBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agregarAdmin(user.getEmail());
                }

            });
        }


        @Override
        public int getItemCount() {
            return users.size();
        }

        public void setUsers(List<User> users) {
            this.users = users;
            notifyDataSetChanged();
        }
    }


    private void agregarAdmin(String email) {
        Map<String, Object> admin = new HashMap<>();
        admin.put("email", email);
        adminsRef.add(admin)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CrearAdmins.this, "Nuevo Administrador Creado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error al crear un nuevo Administrador", e);
                        Toast.makeText(CrearAdmins.this, "Error al crear un nuevo Administrador", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView emailTextView;
        public TextView nombreTextView;
        public Button agregarBtn;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            agregarBtn = itemView.findViewById(R.id.agregarBtn);
        }
    }
}