package com.cs50vn.virustracker.app.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppVersion {
    public static int HIGHER = 0;
    public static int EQUAL = 1;
    public static int LOWER = 2;

    private int major;
    private int minor;
    private int patch;
    private int versionId;

    public AppVersion(int versionId) {
        this.versionId = versionId;
    }

    public AppVersion(int major, int minor, int patch, int versionId) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.versionId = versionId;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getPatch() {
        return patch;
    }

    public void setPatch(int patch) {
        this.patch = patch;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public static int compare(AppVersion av, AppVersion av1) {
        if (av.getVersionId() == av1.getVersionId())
            return EQUAL;

        if (av.getVersionId() > av1.getVersionId())
            return HIGHER;

        return LOWER;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return getVersionId() == ((AppVersion)obj).getVersionId();
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(major) + "." + String.valueOf(minor) + "." + String.valueOf(patch) + "-" + String.valueOf(versionId);
    }
}
