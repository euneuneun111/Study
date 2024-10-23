package com.example.myapplication;

public class Post {
    private String p_title;
    private String p_content;
    private String p_img;
    private String room_id;  // room_id 필드 추가

    // Getter 메서드 추가
    public String getP_title() {
        return p_title;
    }

    public String getP_content() {
        return p_content;
    }

    public String getP_img() {
        return p_img;
    }

    public String getRoom_id() {  // room_id의 Getter
        return room_id;
    }

    // Setter 메서드 (필요할 경우)
    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}
