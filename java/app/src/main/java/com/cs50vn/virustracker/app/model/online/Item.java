package com.cs50vn.virustracker.app.model.online;

import java.util.LinkedList;

public class Item {
    private long id;
    private long totalCases;
    private long newCases;
    private long totalDeaths;
    private long newDeaths;
    private long totalRecovered;
    private long seriousCases;
    private float totalCasesPer1Pop;
    private float totalDeathsPer1Pop;
    private long totalTests;
    private float testsPer1Pop;
    private long timestamp;

    public Item(long totalCases, long newCases, long totalDeaths) {
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.totalDeaths = totalDeaths;
    }

    public Item(long id, long totalCases, long newCases, long totalDeaths, long newDeaths, long totalRecovered, long seriousCases, float totalCasesPer1Pop, float totalDeathsPer1Pop, long totalTests, float testsPer1Pop, long timestamp) {
        this.id = id;
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalRecovered = totalRecovered;
        this.seriousCases = seriousCases;
        this.totalCasesPer1Pop = totalCasesPer1Pop;
        this.totalDeathsPer1Pop = totalDeathsPer1Pop;
        this.totalTests = totalTests;
        this.testsPer1Pop = testsPer1Pop;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(long totalCases) {
        this.totalCases = totalCases;
    }

    public long getNewCases() {
        return newCases;
    }

    public void setNewCases(long newCases) {
        this.newCases = newCases;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public long getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(long newDeaths) {
        this.newDeaths = newDeaths;
    }

    public long getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(long totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public long getSeriousCases() {
        return seriousCases;
    }

    public void setSeriousCases(long seriousCases) {
        this.seriousCases = seriousCases;
    }

    public float getTotalCasesPer1Pop() {
        return totalCasesPer1Pop;
    }

    public void setTotalCasesPer1Pop(float totalCasesPer1Pop) {
        this.totalCasesPer1Pop = totalCasesPer1Pop;
    }

    public float getTotalDeathsPer1Pop() {
        return totalDeathsPer1Pop;
    }

    public void setTotalDeathsPer1Pop(float totalDeathsPer1Pop) {
        this.totalDeathsPer1Pop = totalDeathsPer1Pop;
    }

    public long getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(long totalTests) {
        this.totalTests = totalTests;
    }

    public float getTestsPer1Pop() {
        return testsPer1Pop;
    }

    public void setTestsPer1Pop(float testsPer1Pop) {
        this.testsPer1Pop = testsPer1Pop;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
