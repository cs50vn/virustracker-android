package com.cs50vn.virustracker.app.model.online;

import android.graphics.Bitmap;

public class ImageRes {
    private String id;
    private String url;
    private String data;
    private Bitmap rawData;
    private long timestamp;

    public ImageRes() {
        this.id = "";
        this.url = "";
        this.data = "";
        this.rawData = null;
        this.timestamp = 0;
    }

    public ImageRes(String id, String url, String data, Bitmap rawData, long timestamp) {
        this.id = id;
        this.url = url;
        this.data = data;
        this.rawData = rawData;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Bitmap getRawData() {
        return rawData;
    }

    public void setRawData(Bitmap rawData) {
        this.rawData = rawData;
    }
}
