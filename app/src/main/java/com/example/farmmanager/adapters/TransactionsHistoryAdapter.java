package com.example.farmmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.R;
import com.example.farmmanager.TransactionsList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TransactionsHistoryAdapter extends RecyclerView.Adapter<TransactionsHistoryAdapter.MyViewHolder> {
    List<TransactionsList> transactions;
    public TransactionsHistoryAdapter(List<TransactionsList> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_custom_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.particulars.setText(transactions.get(position).getParticulars());
        holder.time.setText(transactions.get(position).getDate());
        holder.contact.setText(transactions.get(position).getContact());
        holder.price.setText(transactions.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView particulars, price, contact, time;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            particulars = itemView.findViewById(R.id.particulars_view);
            price = itemView.findViewById(R.id.amount_view);
            contact = itemView.findViewById(R.id.contact_view);
            time = itemView.findViewById(R.id.time_of_transaction_view);
        }
    }
}
