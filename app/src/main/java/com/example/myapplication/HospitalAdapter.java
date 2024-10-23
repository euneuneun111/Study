package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private List<Hospital> hospitalList; // Hospital 객체 리스트

    public HospitalAdapter() {
        this.hospitalList = new ArrayList<>(); // 초기화
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hospital hospital = hospitalList.get(position);
        holder.hospitalNameTextView.setText(hospital.getH_name()); // h_name 대신 getH_name() 사용
        holder.contactNumberTextView.setText(hospital.getH_contact_number()); // h_contact_number 대신 getH_contact_number() 사용
        holder.addressTextView.setText(hospital.getH_address()); // h_Address 대신 getH_address() 사용
        holder.websiteTextView.setText(hospital.getH_web_server()); // h_url 대신 getH_web_server() 사용
        holder.reservationLinkTextView.setText(hospital.getH_reservation_link()); // h_re_url 대신 getH_reservation_link() 사용
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public void updateData(List<Hospital> newHospitalList) {
        this.hospitalList = newHospitalList; // 새로운 데이터로 갱신
        notifyDataSetChanged(); // 어댑터에 데이터 변경 알림
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView hospitalNameTextView;
        TextView contactNumberTextView;
        TextView addressTextView;
        TextView websiteTextView;
        TextView reservationLinkTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            hospitalNameTextView = itemView.findViewById(R.id.h_name);
            contactNumberTextView = itemView.findViewById(R.id.h_contact_number);
            addressTextView = itemView.findViewById(R.id.h_address);
            websiteTextView = itemView.findViewById(R.id.h_web_server);
            reservationLinkTextView = itemView.findViewById(R.id.h_reservation_link);
        }
    }
}
