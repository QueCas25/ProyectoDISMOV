package com.example.proyectodismovjava.Users;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodismovjava.R;

public class HolderUser extends RecyclerView.ViewHolder{

    private ImageView img,btn;
    private TextView name;

    public HolderUser(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.UserName);
        img = (ImageView) itemView.findViewById(R.id.UserImage);
        btn = (ImageView) itemView.findViewById(R.id.ViewBtn);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }
    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
    public ImageView getBtn() {
        return btn;
    }

    public void setBtn(ImageView btn) {
        this.btn = btn;
    }
}
