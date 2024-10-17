package com.example.myapplication;

import android.net.Uri;

public class RecentRecord {
    private Uri imageUri;
    private String timestamp;

    public RecentRecord(Uri imageUri, String timestamp) {
        this.imageUri = imageUri;
        this.timestamp = timestamp;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
