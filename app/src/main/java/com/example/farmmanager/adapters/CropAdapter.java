package com.example.farmmanager.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.R;
import com.example.farmmanager.UpdateCrop;
import com.example.farmmanager.models.CropsModel;

import java.util.List;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.myViewHolder> {
    List<CropsModel> crops;
    public CropAdapter(List<CropsModel> list) {
        this.crops = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.textView.setText(crops.get(position).getCropName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), UpdateCrop.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return crops.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.customRow);
        }
    }
}
