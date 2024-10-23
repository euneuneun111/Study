package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class Hospital {
    @SerializedName("h_img") // JSON의 h_img 필드와 매핑
    private String h_img;

    @SerializedName("h_name") // JSON의 h_name 필드와 매핑
    private String h_name;

    @SerializedName("h_region") // JSON의 h_region 필드와 매핑
    private String h_region;

    @SerializedName("h_contact_number") // JSON의 h_contact_number 필드와 매핑
    private String h_contact_number;

    @SerializedName("h_Address") // JSON의 h_Address 필드와 매핑 (대소문자 주의)
    private String h_Address;

    @SerializedName("h_url") // JSON의 h_url 필드와 매핑
    private String h_url;

    @SerializedName("h_re_url") // JSON의 h_re_url 필드와 매핑
    private String h_re_url;

    // 생성자
    public Hospital(String h_img, String h_name, String h_region, String h_contact_number,
                    String h_Address, String h_url, String h_re_url) {
        this.h_img = h_img;
        this.h_name = h_name;
        this.h_region = h_region;
        this.h_contact_number = h_contact_number;
        this.h_Address = h_Address;
        this.h_url= h_url;
        this.h_re_url = h_re_url;
    }

    // Getter 메소드
    public String getH_imageUrl() { return h_img; }
    public String getH_name() { return h_name; }
    public String getH_region() { return h_region; }
    public String getH_contact_number() { return h_contact_number; }
    public String getH_address() { return h_Address; }
    public String getH_web_server() { return h_url; }
    public String getH_reservation_link() { return h_re_url; }
}
