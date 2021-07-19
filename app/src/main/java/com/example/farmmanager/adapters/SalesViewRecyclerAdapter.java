package com.example.farmmanager.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SalesViewRecyclerAdapter extends RecyclerView.Adapter<SalesViewRecyclerAdapter.MyViewHolder> {
    List<String> records = new ArrayList<>();
    public SalesViewRecyclerAdapter(List<String> list){
        this.records = list;
    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
