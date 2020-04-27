package com.example.nmg.stunitas.Main.RecyclerHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nmg.stunitas.Data.documents;

public abstract class MainHolder extends RecyclerView.ViewHolder {

    public MainHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void setMessageData(documents data, Context context);
}
