package com.example.proyectodismovjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodismovjava.R;
import com.example.proyectodismovjava.activities.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user, position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(previousPosition);
        notifyItemChanged(selectedPosition);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView emailTextView;
        private TextView nombreTextView;
        private Button addButton;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            addButton = itemView.findViewById(R.id.agregarBtn);
        }

        void bind(User user, boolean isSelected) {
            emailTextView.setText(user.getEmail());
            nombreTextView.setText(user.getName());
            addButton.setVisibility(isSelected ? View.VISIBLE : View.GONE);
        }
    }

}