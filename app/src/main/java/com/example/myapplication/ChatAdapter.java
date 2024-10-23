package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_TEXT = 1;
    private static final int VIEW_TYPE_IMAGE = 2;
    private List<String> chatMessages;
    private Context context;

    public ChatAdapter(List<String> chatMessages, Context context) {
        this.chatMessages = chatMessages;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        String message = chatMessages.get(position);
        if (message.startsWith("이미지 업로드 성공:")) {
            return VIEW_TYPE_IMAGE; // 이미지 메시지일 경우
        } else {
            return VIEW_TYPE_TEXT;  // 텍스트 메시지일 경우
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_IMAGE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_message, parent, false); // 이미지 메시지 레이아웃
            return new ImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false); // 텍스트 메시지 레이아웃
            return new TextViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String message = chatMessages.get(position);
        if (holder instanceof ImageViewHolder) {
            String imageUrl = message.substring(9); // "이미지 업로드 성공: " 부분 제거
            ImageViewHolder imageHolder = (ImageViewHolder) holder;
            Glide.with(context).load(imageUrl).into(imageHolder.imageView); // Glide로 이미지 로드
        } else {
            TextViewHolder textHolder = (TextViewHolder) holder;
            textHolder.messageTextView.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(android.R.id.text1);
        }
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_message); // 이미지 뷰 ID
        }
    }
}
