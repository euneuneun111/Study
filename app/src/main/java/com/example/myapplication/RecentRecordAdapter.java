package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;

import java.util.List;

public class RecentRecordAdapter extends RecyclerView.Adapter<RecentRecordAdapter.ViewHolder> {

    private List<RecentRecord> recentRecordList;
    private OnRecordClickListener onRecordClickListener;

    public interface OnRecordClickListener {
        void onRecordClick(RecentRecord recentRecord);
    }

    public RecentRecordAdapter(List<RecentRecord> recentRecordList, OnRecordClickListener listener) {
        this.recentRecordList = recentRecordList;
        this.onRecordClickListener = listener;
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
        holder.timestampTextView.setText(recentRecord.getTimestamp());

        // String 형태의 URI를 Uri 객체로 변환
        Uri imageUri = Uri.parse(recentRecord.getImageUri());
        holder.imageView.setImageURI(imageUri); // Uri 객체를 사용하여 이미지 설정

        holder.itemView.setOnClickListener(v -> onRecordClickListener.onRecordClick(recentRecord));
    }

    @Override
    public int getItemCount() {
        return recentRecordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timestampTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timestampTextView = itemView.findViewById(R.id.timestamp_text_view);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
