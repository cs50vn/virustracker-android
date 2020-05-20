package com.cs50vn.virustracker.app.appmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cs50vn.virustracker.app.controller.worker.CountryLoaderWorker;
import com.cs50vn.virustracker.app.model.online.Country;
import com.cs50vn.virustracker.app.utils.CountryModeEnum;
import com.cs50vn.virustracker.app.utils.CountrySortEnum;

import java.util.LinkedList;
import java.util.List;

public class CountryViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    public CountryViewModel(Application application) {
        super(application);

        appRepository = AppRepository.getInstance();
    }

    public LiveData<CountryModeEnum> getCountryMode() {
        return appRepository.getCountryRepository().getCountryMode();
    }

    public void setCountryMode(CountryModeEnum type) {
        appRepository.getCountryRepository().setCountryMode(type);
    }

    public LiveData<LinkedList<Country>> getSearchCountryList() {
        return appRepository.getCountryRepository().getSearchCountryList();
    }


    public LiveData<CountrySortEnum> getCountrySortEnum() {
        return appRepository.getCountryRepository().getCountrySortEnum();
    }

    public void sortCountries(CountrySortEnum type) {
        appRepository.getCountryRepository().setCountrySortEnum(type);
    }

    public void search(String keyword) {
        appRepository.getCountryRepository().search(keyword);
    }

    public LiveData<Country> getCountryDetail() {
        return appRepository.getCountryRepository().getCountryDetail();
    }
}
