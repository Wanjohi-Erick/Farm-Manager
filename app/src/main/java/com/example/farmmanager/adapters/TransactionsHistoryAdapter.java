package com.example.farmmanager.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TransactionsHistoryAdapter extends RecyclerView.Adapter<TransactionsHistoryAdapter.MyViewHolder> {
    List<String> transactions;
    public TransactionsHistoryAdapter(List<String> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //TODO Finish up setting the transactionRecycler
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView particulars, amount, contact, time;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
