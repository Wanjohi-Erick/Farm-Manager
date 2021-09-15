package com.example.farmmanager.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.R;
import com.example.farmmanager.UpdateLivestock;
import com.example.farmmanager.models.LivestockModel;

import java.util.List;

public class LIvestockAdapter extends RecyclerView.Adapter<LIvestockAdapter.myViewHolder> {
    List<LivestockModel> livestock;
    public LIvestockAdapter(List<LivestockModel> list) {
        this.livestock = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.textView.setText(livestock.get(position).getLivestockName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateLivestockIntent = new Intent(v.getContext(), UpdateLivestock.class);
                updateLivestockIntent.putExtra("name", livestock.get(position).getLivestockName());
                updateLivestockIntent.putExtra("harvestUnits", livestock.get(position).getHarvestUnits());
                updateLivestockIntent.putExtra("land", livestock.get(position).getLandName());
                v.getContext().startActivity(updateLivestockIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return livestock.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.customRow);
        }
    }
}
