package com.example.farmmanager.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmmanager.R;
import com.example.farmmanager.Web;
import com.kwabenaberko.newsapilib.models.Source;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.MyViewHolder> {
    List<Source> newsArray;
    public NewsRecyclerAdapter(List<Source> newsArray) {
        this.newsArray = newsArray;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_news_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.titleView.setText(newsArray.get(position).getName());
        holder.messageView.setText(newsArray.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadLinkIntent = new Intent(v.getContext(), Web.class);
                loadLinkIntent.putExtra("url", newsArray.get(position).getUrl());
                v.getContext().startActivity(loadLinkIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsArray.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, messageView;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.news_title);
            messageView = itemView.findViewById(R.id.news_message);
        }
    }
}
