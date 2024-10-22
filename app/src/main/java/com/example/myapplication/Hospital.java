package com.example.myapplication;

public class Hospital {
    private String h_img; // 병원 로고
    private String h_name; // 병원 이름
    private String h_region; // 지역
    private String h_contact_number; // 연락처
    private String h_Address; // 주소
    private String h_url; // 병원 웹서버
    private String h_re_url; // 예약 링크

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
