package com.example.farmmanager.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.R;
import com.example.farmmanager.RecordSalesActivity;

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
        switch (position){
            case 0:
                holder.placeHolder.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), RecordSalesActivity.class);
                    v.getContext().startActivity(intent);
                });
                break;
            case 1:
                holder.placeHolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO Intent intent = new Intent(v.getContext(), TrainingActivity.class);
                        //v.getContext().startActivity(intent);
                    }
                });
                break;
            case 2:
                holder.placeHolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO Intent intent = new Intent(v.getContext(), LeaseActivity.class);
                       //v.getContext().startActivity(intent);
                    }
                });
                break;
        }
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
