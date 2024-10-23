package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private Context context;

    public PostAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // item_board.xml 레이아웃을 inflate
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.titleTextView.setText(post.getP_title());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BoardinfoActivity.class);
            intent.putExtra("post_title", post.getP_title());
            intent.putExtra("post_content", post.getP_content());
            intent.putExtra("post_img", post.getP_img()); // 이미지 경로 전달
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            // item_board.xml의 TextView ID 확인
            titleTextView = itemView.findViewById(R.id.tv_title);
        }
    }
}
