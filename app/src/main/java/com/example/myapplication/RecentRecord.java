package com.example.myapplication;

public class RecentRecord {

    private String timestamp;
    private String imageUri;

    public RecentRecord(String timestamp, String imageUri) {
        this.timestamp = timestamp;
        this.imageUri = imageUri;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getImageUri() {
        return imageUri;
    }
}
