package com.example.myapplication;

public class Hospital {
    private String h_name; // 병원 이름
    private String h_contact_number; // 연락처
    private String h_Address; // 주소
    private String h_url; // 병원 웹서버
    private String h_re_url; // 예약 링크

    public String getH_name() { return h_name; }
//    public void setH_name(String h_name) {this.h_name = h_name;}
    public String getH_contact_number() { return h_contact_number; }
//    public void setH_contact_number(String h_contact_number) {this.h_contact_number = h_contact_number;}
    public String getH_address() { return h_Address; }
//    public void setH_address(String h_Address) {this.h_Address = h_Address;}
    public String getH_web_server() { return h_url; }
//    public void setH_web_server(String h_url) {this.h_url = h_url;}
    public String getH_reservation_link() { return h_re_url; }
//    public void setH_reservation_link(String h_re_url) {this.h_re_url = h_re_url;}
}