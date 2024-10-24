package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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

        // 제목 설정
        holder.titleTextView.setText(post.getP_title());

        // room_id 설정
        holder.roomIdTextView.setText(post.getRoom_id());

        // 이미지 설정 (이미지 URL이 있을 경우에만 표시)
        String imageUrl = post.getP_img();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            holder.postImageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(imageUrl).into(holder.postImageView); // 이미지 로드
        } else {
            holder.postImageView.setVisibility(View.GONE); // 이미지가 없으면 숨김
        }

        // 아이템 클릭 리스너 설정
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BoardinfoActivity.class);
            intent.putExtra("post_title", post.getP_title());
            intent.putExtra("post_content", post.getP_content());
            intent.putExtra("room_id", post.getRoom_id());
            intent.putExtra("image_url", post.getP_img()); // 이미지 URL도 전달
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView roomIdTextView;
        ImageView postImageView; // 추가된 이미지뷰

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_title);
            roomIdTextView = itemView.findViewById(R.id.tv_room_id);
            postImageView = itemView.findViewById(R.id.post_image); // 이미지뷰 연결
        }
    }
}
