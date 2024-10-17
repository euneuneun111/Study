package com.example.myapplication;

// RecentRecordAdapter.java
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecentRecordAdapter extends RecyclerView.Adapter<RecentRecordAdapter.RecentRecordViewHolder> {

    private List<RecentRecord> recentRecordList;

    public RecentRecordAdapter(List<RecentRecord> recentRecordList) {
        this.recentRecordList = recentRecordList;
    }

    @NonNull
    @Override
    public RecentRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_disease, parent, false);
        return new RecentRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentRecordViewHolder holder, int position) {
        RecentRecord currentItem = recentRecordList.get(position);
        holder.diseaseImageView.setImageURI(currentItem.getImageUri());
        holder.postTimeTextView.setText(currentItem.getPostTime());
    }

    @Override
    public int getItemCount() {
        return recentRecordList.size();
    }

    public static class RecentRecordViewHolder extends RecyclerView.ViewHolder {
        public ImageView diseaseImageView;
        public TextView postTimeTextView;

        public RecentRecordViewHolder(View itemView) {
            super(itemView);
            diseaseImageView = itemView.findViewById(R.id.disease_image);
            postTimeTextView = itemView.findViewById(R.id.post_time);
        }
    }
}

