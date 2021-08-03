package com.example.farmmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.MyViewHolder> {
    List<String> incomeCategories;
    public IncomeAdapter(List<String> incomeCategories) {
        this.incomeCategories = incomeCategories;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.placeHolder.setText(incomeCategories.get(position));
    }


    @Override
    public int getItemCount() {
        return incomeCategories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView placeHolder;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            placeHolder = itemView.findViewById(R.id.customRow);
        }
    }
}
