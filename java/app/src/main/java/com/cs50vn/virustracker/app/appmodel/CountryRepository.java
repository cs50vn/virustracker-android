package com.cs50vn.virustracker.app.appmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cs50vn.virustracker.app.model.online.Continent;
import com.cs50vn.virustracker.app.model.online.Country;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.CountryModeEnum;
import com.cs50vn.virustracker.app.utils.CountrySortEnum;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class CountryRepository {

    private MutableLiveData<LinkedList<Country>> countryList;
    private LinkedList<Country> internalCountryList;

    private MutableLiveData<LinkedList<Country>> searchCountryList;
    private LinkedList<Country> internalSearchCountryList;

    private MutableLiveData<Country> countryDetail;
    private Country internalCountryDetail;

    private HashMap<String, Continent> continentList;

    private MutableLiveData<CountryModeEnum> countryMode;
    private CountryModeEnum internalCountryMode;

    private MutableLiveData<CountrySortEnum> countrySort;
    private CountrySortEnum internalCountrySort;

    public CountryRepository() {
        countryList = new MutableLiveData<>();
        internalCountryList = new LinkedList<>();
        searchCountryList = new MutableLiveData<>();
        internalSearchCountryList = new LinkedList<>();
        countryDetail = new MutableLiveData<>();
        internalCountryDetail = new Country();
        continentList = new HashMap<>();
        countryMode = new MutableLiveData<>();
        internalCountryMode = CountryModeEnum.STANDARD;
        countrySort = new MutableLiveData<>();
        internalCountrySort = CountrySortEnum.TOTAL_CASES;
    }

    public void sortCountryList(LinkedList<Country> list, CountrySortEnum type) {
        PLog.WriteLog(PLog.MAIN_TAG, "sort =======0 ");
        if (type == CountrySortEnum.TOTAL_CASES) {
            PLog.WriteLog(PLog.MAIN_TAG, "sort =======1 ");
            Collections.sort(list, (o1, o2) -> {
                return Long.compare(o2.getItemList().get(0).getTotalCases(), o1.getItemList().get(0).getTotalCases());
            });
        } else if (type == CountrySortEnum.NEW_CASES) {
            PLog.WriteLog(PLog.MAIN_TAG, "sort =======2 ");
            Collections.sort(list, (o1, o2) -> {
                return Long.compare(o2.getItemList().get(0).getNewCases(), o1.getItemList().get(0).getNewCases());
            });
        } else if (type == CountrySortEnum.TOTAL_DEATHS) {
            PLog.WriteLog(PLog.MAIN_TAG, "sort =======3 ");
            Collections.sort(list, (o1, o2) -> {
                return Long.compare(o2.getItemList().get(0).getTotalDeaths(), o1.getItemList().get(0).getTotalDeaths());
            });
        }

    }

    public LiveData<LinkedList<Country>> getCountryList() {
        return countryList;
    }

    public void setInternalCountryList(LinkedList<Country> list) {
        internalCountryList.clear();
        internalCountryList.addAll(list);
    }

    public LiveData<LinkedList<Country>> getSearchCountryList() {
        return searchCountryList;
    }

    public void setInternalSearchCountryList(LinkedList<Country> list) {
        internalSearchCountryList.clear();
        internalSearchCountryList.addAll(list);
        sortCountryList(internalSearchCountryList, internalCountrySort);
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
        continentList.clear();
        continentList.putAll(list);
    }

    public LiveData<CountryModeEnum> getCountryMode() {
        return countryMode;
    }

    public void setCountryMode(CountryModeEnum mode) {
        internalCountryMode = mode;
        countryMode.postValue(internalCountryMode);
    }

    public LiveData<CountrySortEnum> getCountrySortEnum() {
        return countrySort;
    }


    public void setCountrySortEnum(CountrySortEnum type) {
        internalCountrySort = type;
        sortCountryList(internalSearchCountryList, type);
        searchCountryList.postValue(internalSearchCountryList);
        countrySort.postValue(internalCountrySort);
    }

    public void search(String keyword) {
        LinkedList<Country> list = new LinkedList<>();

        for (Country country: internalCountryList) {
            if (country.getName().toLowerCase().contains(keyword.toLowerCase()))
                list.add(country);
        }

        setInternalSearchCountryList(list);
    }
}
