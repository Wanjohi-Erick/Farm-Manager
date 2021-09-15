package com.example.farmmanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.R;
import com.example.farmmanager.models.EmployeesModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.MyViewHolder> {
    List<EmployeesModel> employeeList;

    public EmployeesAdapter(List<EmployeesModel> employeeList) {
        this.employeeList = employeeList;
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
        holder.nameView.setText(employeeList.get(position).getEmployeeName());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.customRow);
        }
    }
}
