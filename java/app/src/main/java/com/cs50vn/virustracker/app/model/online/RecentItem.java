package com.cs50vn.virustracker.app.model.online;

public class RecentItem {
    private long timestamp;
    private int value;

    public RecentItem(long timestamp, int value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
