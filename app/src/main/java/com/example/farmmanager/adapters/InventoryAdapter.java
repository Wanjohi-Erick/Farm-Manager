package com.example.farmmanager.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.models.InventoryModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder> {
    List<InventoryModel> inventoryCategories;
    public InventoryAdapter(List<InventoryModel> inventoryCategories) {
        this.inventoryCategories = inventoryCategories;
    }

    @NonNull
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
        return inventoryCategories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
