package com.example.to_lettracker;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//API Adapter Class
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private List<APIModel> apiModelList;
    private Context context;

    public CustomAdapter(List<APIModel> apiModelList, Context context) {
        this.apiModelList = apiModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.api_item_list, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        APIModel apiModel = apiModelList.get(position);
        holder.id.setText(apiModel.getId());
        holder.status.setText(apiModel.getIs_active());
        holder.location.setText(apiModel.getLocation());
        holder.flatDescription.setText(apiModel.getFlat_description());
        holder.contactNumber.setText(apiModel.getPhone_number());
        holder.flatSize.setText(apiModel.getFlat_size());
        holder.totalRent.setText(apiModel.getTotal_rent());
        holder.postedOn.setText(apiModel.getPost_time());

    }

    @Override
    public int getItemCount() {
        return apiModelList.size();
    }

    // API ViewHolder Class
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, status, location, flatDescription, contactNumber, flatSize, totalRent, postedOn;
        ImageView photos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            status = itemView.findViewById(R.id.status);
            location = itemView.findViewById(R.id.location);
            flatDescription = itemView.findViewById(R.id.flatDescription);
            contactNumber = itemView.findViewById(R.id.contactNumber);
            flatSize = itemView.findViewById(R.id.flatSize);
            totalRent = itemView.findViewById(R.id.totalRent);
            postedOn = itemView.findViewById(R.id.postedOn);
        }
    }
}
