package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentRecordAdapter extends RecyclerView.Adapter<RecentRecordAdapter.ViewHolder> {

    private List<RecentRecord> recentRecordList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(RecentRecord recentRecord);
    }

    public RecentRecordAdapter(List<RecentRecord> recentRecordList, OnItemClickListener listener) {
        this.recentRecordList = recentRecordList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentRecord recentRecord = recentRecordList.get(position);
        holder.bind(recentRecord, listener);
    }

    @Override
    public int getItemCount() {
        return recentRecordList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView timestampTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.disease_image); // item_recent_record.xml의 ImageView ID
            timestampTextView = itemView.findViewById(R.id.post_time); // item_recent_record.xml의 TextView ID
        }

        public void bind(RecentRecord recentRecord, OnItemClickListener listener) {
            // 이미지 URI를 사용하여 이미지 로드
            imageView.setImageURI(recentRecord.getImageUri());
            timestampTextView.setText(recentRecord.getTimestamp());

            itemView.setOnClickListener(v -> listener.onItemClick(recentRecord));
        }
    }
}

