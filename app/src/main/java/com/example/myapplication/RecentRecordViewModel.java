package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecentRecordViewModel extends ViewModel {
    private MutableLiveData<List<RecentRecord>> recentRecords;

    public RecentRecordViewModel() {
        recentRecords = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<RecentRecord>> getRecentRecords() {
        return recentRecords;
    }

    public void addRecentRecord(RecentRecord record) {
        List<RecentRecord> currentList = recentRecords.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(record);
        recentRecords.setValue(currentList); // 옵저버에게 알리기
    }
}
