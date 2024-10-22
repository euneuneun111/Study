package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {
    private ArrayList<Hospital> hospitalList;

    public HospitalAdapter(ArrayList<Hospital> hospitalList) {
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_item, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        Hospital hospital = hospitalList.get(position);
        holder.hospitalName.setText(hospital.getH_name());
        holder.hospitalRegion.setText(hospital.getH_region());
        holder.hospitalContact.setText(hospital.getH_contact_number());
        holder.hospitalAddress.setText(hospital.getH_address());
        holder.hospitalWebServer.setText(hospital.getH_web_server());
        holder.hospitalReservationLink.setText(hospital.getH_reservation_link());

        // 이미지 로드 (Glide 라이브러리 사용)
        Glide.with(holder.itemView.getContext())
                .load(hospital.getH_imageUrl())
                .into(holder.hospitalLogo);

        // "더보기" 클릭 리스너 추가
        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 병원 주소 및 링크 표시용 LinearLayout의 가시성 토글
                if (holder.moreInfoLayout.getVisibility() == View.GONE) {
                    holder.moreInfoLayout.setVisibility(View.VISIBLE);
                } else {
                    holder.moreInfoLayout.setVisibility(View.GONE);
                }
            }
        });

        // 예약하기 텍스트뷰 클릭 리스너 추가
        holder.hospitalReservationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = hospital.getH_reservation_link(); // 예약 링크 가져오기
                if (!url.isEmpty()) {
                    // Intent를 사용하여 웹 브라우저로 링크 열기
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    static class HospitalViewHolder extends RecyclerView.ViewHolder {
        ImageView hospitalLogo;
        TextView hospitalName;
        TextView hospitalRegion;
        TextView hospitalContact;
        TextView hospitalAddress;
        TextView hospitalWebServer;
        TextView hospitalReservationLink;
        TextView moreInfo; // 더보기 텍스트
        LinearLayout moreInfoLayout; // 병원 주소 및 링크 레이아웃

        public HospitalViewHolder(View itemView) {
            super(itemView);
            hospitalLogo = itemView.findViewById(R.id.hospitalLogo);
            hospitalName = itemView.findViewById(R.id.hospitalName);
            hospitalRegion = itemView.findViewById(R.id.hospitalRegion);
            hospitalContact = itemView.findViewById(R.id.hospitalContact);
            hospitalAddress = itemView.findViewById(R.id.hospitalAddress);
            hospitalWebServer = itemView.findViewById(R.id.hospitalWebServer);
            hospitalReservationLink = itemView.findViewById(R.id.hospitalReservationLink);
            moreInfo = itemView.findViewById(R.id.more_hos_info); // 더보기 텍스트
            moreInfoLayout = itemView.findViewById(R.id.moreInfoLayout);
        }
    }
}

