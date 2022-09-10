package com.example.assignment_3;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Model> modelList;
    Adapter(List<Model> modelList){
        this.modelList = modelList;
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        int news = modelList.get(position).getNews();
        holder.setData(news);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView newsName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsName = itemView.findViewById(R.id.newsName);
            itemView.setOnClickListener(this);
        }
        public void setData(int news) {
            newsName.setText("News "+news);
        }

        @Override
        public void onClick(View view){
            FragmentManager fm = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.la, new detailsFragment(modelList.get(getAdapterPosition()).getNews()));
            fragmentTransaction.commit();
        }
    }
}

