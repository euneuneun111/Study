package com.example.myapplication;

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
    private List<RecentRecord> records;
    private OnRecordClickListener listener;

    public RecentRecordAdapter(List<RecentRecord> records, OnRecordClickListener listener) {
        this.records = records;
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
        RecentRecord record = records.get(position);
        holder.bind(record, listener);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView timestampTextView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timestampTextView = itemView.findViewById(R.id.timestamp_text_view);
            imageView = itemView.findViewById(R.id.image_view);
        }

        public void bind(RecentRecord record, OnRecordClickListener listener) {
            timestampTextView.setText(record.getTimestamp());
            imageView.setImageURI(Uri.parse(record.getImageUri()));
            itemView.setOnClickListener(v -> listener.onRecordClick(record)); // 클릭 이벤트 처리
        }
    }

    public interface OnRecordClickListener {
        void onRecordClick(RecentRecord record);
    }
}
