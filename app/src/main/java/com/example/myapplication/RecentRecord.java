package com.example.myapplication;

// RecentRecord.java
import android.net.Uri;

public class RecentRecord {
    private Uri imageUri;
    private String postTime;

    public RecentRecord(Uri imageUri, String postTime) {
        this.imageUri = imageUri;
        this.postTime = postTime;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getPostTime() {
        return postTime;
    }
}
