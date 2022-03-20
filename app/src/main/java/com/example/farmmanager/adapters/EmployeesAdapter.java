package com.example.farmmanager.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.R;
import com.example.farmmanager.ViewEmployee;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_custom_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        String firstName, lastName, name, contact, id, gender, role, salary;
        firstName = employeeList.get(position).getEmployeeFirstName();
        lastName = employeeList.get(position).getEmployeeLastName();
        name = String.format("%s, %s", lastName, firstName);
        id = employeeList.get(position).getEmployeeID();
        contact = employeeList.get(position).getEmployeeContact();
        gender = employeeList.get(position).getGender();
        role = employeeList.get(position).getRole();
        salary = employeeList.get(position).getSalary();
        holder.nameView.setText(name);
        holder.idView.setText(id);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ViewEmployee.class);
            intent.putExtra("firstName", firstName);
            intent.putExtra("lastName", lastName);
            intent.putExtra("id", id);
            intent.putExtra("contact", contact);
            intent.putExtra("gender", gender);
            intent.putExtra("role", role);
            intent.putExtra("salary", salary);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, idView;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.employee_last_name);
            idView = itemView.findViewById(R.id.employee_id);
        }
    }
}
