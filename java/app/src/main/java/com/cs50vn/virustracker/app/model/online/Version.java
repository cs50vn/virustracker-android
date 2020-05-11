package com.cs50vn.virustracker.app.model.online;

public class Version {
    public enum STATUS {
        FORCE_UPDATE,
        RECOMMEND_UPDATE,
        NONE
    }

    private int versionCode;
    private STATUS status;
    private String downloadLink;

    public Version(int versionCode, STATUS status, String downloadLink) {
        this.versionCode = versionCode;
        this.status = status;
        this.downloadLink = downloadLink;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
}
