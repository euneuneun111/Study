package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class RecentRecord implements Parcelable {
    private String timestamp;
    private String imageUri;

    public RecentRecord(String timestamp, String imageUri) {
        this.timestamp = timestamp;
        this.imageUri = imageUri;
    }

    protected RecentRecord(Parcel in) {
        timestamp = in.readString();
        imageUri = in.readString();
    }

    public static final Creator<RecentRecord> CREATOR = new Creator<RecentRecord>() {
        @Override
        public RecentRecord createFromParcel(Parcel in) {
            return new RecentRecord(in);
        }

        @Override
        public RecentRecord[] newArray(int size) {
            return new RecentRecord[size];
        }
    };

    public String getTimestamp() {
        return timestamp;
    }

    public String getImageUri() {
        return imageUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timestamp);
        dest.writeString(imageUri);
    }
}
