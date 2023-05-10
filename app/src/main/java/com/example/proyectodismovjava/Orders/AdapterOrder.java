package com.example.proyectodismovjava.Orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodismovjava.R;
import com.example.proyectodismovjava.models.Order;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<HolderOrder> {

    private List<Order> listMsg = new ArrayList<>();
    private Context c;

    public AdapterOrder(Context c) {
        this.c = c;
    }

    public void addOrder(Order o) {
        listMsg.add(o);
        notifyItemInserted(listMsg.size());
    }

    @NonNull
    @Override
    public HolderOrder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.user, parent, false);
        return new HolderOrder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrder holder, int position) {
        //holder.getName().setText(listMsg.get(position).getEmail().split("@")[0]);
        int p = position;
        /*holder.getBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersActivity context = (UsersActivity) listMsg.get(p).getContext();
                context.CreateBtn(listMsg.get(p).getEmail().split("@")[0]);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listMsg.size();
    }
}
