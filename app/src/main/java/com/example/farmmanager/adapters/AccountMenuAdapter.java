package com.example.farmmanager.adapters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.Login;
import com.example.farmmanager.R;
import com.example.farmmanager.Revenue;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AccountMenuAdapter extends RecyclerView.Adapter<AccountMenuAdapter.MyViewHolder> {

    List<String> accountOptions;

    public AccountMenuAdapter(List<String> menuOptions) {
        this.accountOptions = menuOptions;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        holder.custom_row.setText(accountOptions.get(position));
        holder.itemView.setOnClickListener(v -> {
            switch (position){
                case 0:
                    Toast.makeText(v.getContext(), "Position" + "0", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Intent intent  = new Intent(v.getContext(), Revenue.class);
                    v.getContext().startActivity(intent);
                    break;
                case 2:
                    Toast.makeText(v.getContext(), "Yep", Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    prefs.edit().clear().apply();
                    Intent logoutIntent = new Intent(v.getContext(), Login.class);
                    v.getContext().startActivity(logoutIntent);
                    break;
                default:
                    Toast.makeText(v.getContext(), "finish", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountOptions.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView custom_row;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            custom_row = itemView.findViewById(R.id.customRow);
        }
    }
}
