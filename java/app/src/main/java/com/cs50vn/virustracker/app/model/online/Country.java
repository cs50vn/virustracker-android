package com.cs50vn.virustracker.app.model.online;

import java.util.LinkedList;

public class Country {
    private String id;
    private String name;
    private String capitalName;
    private int area;
    private long population;
    private ImageRes flagRes;
    private long timestamp;
    private Continent continent;
    private LinkedList<Item> itemList;
    private LinkedList<RecentItem> totalCasesRecent;
    private LinkedList<RecentItem> totalDeathsRecent;

    public Country() {
        this.id = "";
        this.name = "";
        this.capitalName = "";
        this.area = 0;
        this.population = 0;
        this.flagRes = new ImageRes();
        this.timestamp = 0;
        this.continent = null;
        this.itemList = new LinkedList<>();
    }

    public Country(String id, String name, String capitalName, int area, long population, ImageRes flagRes, long timestamp, Continent continent, LinkedList<Item> itemList, LinkedList<RecentItem> totalCasesRecent, LinkedList<RecentItem> totalDeathsRecent) {
        this.id = id;
        this.name = name;
        this.capitalName = capitalName;
        this.area = area;
        this.population = population;
        this.flagRes = flagRes;
        this.timestamp = timestamp;
        this.continent = continent;
        this.itemList = itemList;
        this.totalCasesRecent = totalCasesRecent;
        this.totalDeathsRecent = totalDeathsRecent;
    }

    public Country(String id, String name, String capitalName, int area, long population, ImageRes flagRes, long timestamp, Continent continent, LinkedList<Item> itemList) {
        this.id = id;
        this.name = name;
        this.capitalName = capitalName;
        this.area = area;
        this.population = population;
        this.flagRes = flagRes;
        this.timestamp = timestamp;
        this.continent = continent;
        this.itemList = itemList;
        totalCasesRecent = new LinkedList<>();
        totalDeathsRecent = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public ImageRes getFlagRes() {
        return flagRes;
    }

    public void setFlagRes(ImageRes flagRes) {
        this.flagRes = flagRes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public LinkedList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(LinkedList<Item> itemList) {
        this.itemList = itemList;
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
