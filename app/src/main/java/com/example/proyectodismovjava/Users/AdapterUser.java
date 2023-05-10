package com.example.proyectodismovjava.Users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodismovjava.R;
import com.example.proyectodismovjava.UsersActivity;
import com.example.proyectodismovjava.models.User;

import java.util.ArrayList;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<HolderUser> {

    private List<User> listMsg = new ArrayList<>();
    private Context c;

    public AdapterUser(Context c) {
        this.c = c;
    }

    public void addUser(User u) {
        listMsg.add(u);
        notifyItemInserted(listMsg.size());
    }

    @NonNull
    @Override
    public HolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.user, parent, false);
        return new HolderUser(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUser holder, int position) {
        User user = listMsg.get(position);
        holder.getName().setText(user.getName());
        holder.getBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersActivity context = (UsersActivity) user.getContext();
                context.CreateBtn(user.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMsg.size();
    }
}
