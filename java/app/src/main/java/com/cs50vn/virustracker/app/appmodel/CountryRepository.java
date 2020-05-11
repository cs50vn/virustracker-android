package com.cs50vn.virustracker.app.appmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cs50vn.virustracker.app.model.online.AppItem;
import com.cs50vn.virustracker.app.model.online.Continent;
import com.cs50vn.virustracker.app.model.online.Country;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CountryRepository {

    private MutableLiveData<LinkedList<Country>> countryList;
    private LinkedList<Country> internalCountryList;

    private MutableLiveData<LinkedList<Country>> searchCountryList;
    private LinkedList<Country> internalSearchCountryList;

    private MutableLiveData<Country> countryDetail;
    private Country internalCountryDetail;

    private HashMap<String, Continent> continentList;

    public CountryRepository() {
        countryList = new MutableLiveData<>();
        internalCountryList = new LinkedList<>();
        searchCountryList = new MutableLiveData<>();
        internalSearchCountryList = new LinkedList<>();
        countryDetail = new MutableLiveData<>();
        internalCountryDetail = new Country();
        continentList = new HashMap<>();
    }

    public LiveData<LinkedList<Country>> getCountryList() {
        return countryList;
    }

    public void setInternalCountryList(LinkedList<Country> list) {
        internalCountryList = list;
        countryList.postValue(internalCountryList);
    }

    public LiveData<LinkedList<Country>> getSearchCountryList() {
        return searchCountryList;
    }

    public void setInternalSearchCountryList(LinkedList<Country> list) {
        internalSearchCountryList = list;
        searchCountryList.postValue(internalSearchCountryList);
    }

    public LiveData<Country> getCountryDetail() {
        return countryDetail;
    }

    public void setInternalCountryDetail(Country country) {
        internalCountryDetail = country;
        countryDetail.postValue(internalCountryDetail);
    }

    public HashMap<String, Continent> getContinentList() {
        return continentList;
    }

    public void setContinentList(HashMap<String, Continent> list) {
        continentList = list;
    }
}
