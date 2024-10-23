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
        holder.hospitalContact.setText(hospital.getH_contact_number());
        holder.hospitalAddress.setText(hospital.getH_address());
        holder.hospitalWebServer.setText(hospital.getH_web_server());
        holder.hospitalReservationLink.setText(hospital.getH_reservation_link());

        holder.hospitalReservationLink.setOnClickListener(v -> {
            String url = hospital.getH_reservation_link();
            if (!url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                holder.itemView.getContext().startActivity(intent);
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
        TextView hospitalContact;
        TextView hospitalAddress;
        TextView hospitalWebServer;
        TextView hospitalReservationLink;
        LinearLayout moreInfoLayout; // 병원 주소 및 링크 레이아웃

        public HospitalViewHolder(View itemView) {
            super(itemView);
            hospitalName = itemView.findViewById(R.id.hospitalName);
            hospitalContact = itemView.findViewById(R.id.hospitalContact);
            hospitalAddress = itemView.findViewById(R.id.hospitalAddress);
            hospitalWebServer = itemView.findViewById(R.id.hospitalWebServer);
            hospitalReservationLink = itemView.findViewById(R.id.reservation);
        }
    }
}

