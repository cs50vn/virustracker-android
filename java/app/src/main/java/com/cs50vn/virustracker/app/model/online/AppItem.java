package com.cs50vn.virustracker.app.model.online;

import java.util.LinkedList;

public class AppItem {
    private long timestamp;
    private long totalCases;
    private long newCases;
    private long totalDeaths;
    private long newDeaths;
    private long totalRecovered;
    LinkedList<Continent> totalCasesChart;
    LinkedList<Continent> totalDeathsChart;
    LinkedList<RecentItem> totalCasesRecent;
    LinkedList<RecentItem> totalDeathsRecent;

    public AppItem() {
        this.timestamp = 0;
        this.totalCases = 0;
        this.newCases = 0;
        this.totalDeaths = 0;
        this.newDeaths = 0;
        this.totalRecovered = 0;
        this.totalCasesChart = new LinkedList<>();
        this.totalDeathsChart = new LinkedList<>();
        this.totalCasesRecent = new LinkedList<>();
        this.totalDeathsRecent = new LinkedList<>();
    }

    public AppItem(long timestamp, long totalCases, long newCases, long totalDeaths, long newDeaths, long totalRecovered, LinkedList<Continent> totalCasesChart, LinkedList<Continent> totalDeathsChart, LinkedList<RecentItem> totalCasesRecent, LinkedList<RecentItem> totalDeathsRecent) {
        this.timestamp = timestamp;
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.totalDeaths = totalDeaths;
        this.newDeaths = newDeaths;
        this.totalRecovered = totalRecovered;
        this.totalCasesChart = totalCasesChart;
        this.totalDeathsChart = totalDeathsChart;
        this.totalCasesRecent = totalCasesRecent;
        this.totalDeathsRecent = totalDeathsRecent;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public LinkedList<Continent> getTotalCasesChart() {
        return totalCasesChart;
    }

    public void setTotalCasesChart(LinkedList<Continent> totalCasesChart) {
        this.totalCasesChart = totalCasesChart;
    }

    public LinkedList<Continent> getTotalDeathsChart() {
        return totalDeathsChart;
    }

    public void setTotalDeathsChart(LinkedList<Continent> totalDeathsChart) {
        this.totalDeathsChart = totalDeathsChart;
    }

    public LinkedList<RecentItem> getTotalCasesRecent() {
        return totalCasesRecent;
    }

    public void setTotalCasesRecent(LinkedList<RecentItem> totalCasesRecent) {
        this.totalCasesRecent = totalCasesRecent;
    }

    public LinkedList<RecentItem> getTotalDeathsRecent() {
        return totalDeathsRecent;
    }

    public void setTotalDeathsRecent(LinkedList<RecentItem> totalDeathsRecent) {
        this.totalDeathsRecent = totalDeathsRecent;
    }
}
