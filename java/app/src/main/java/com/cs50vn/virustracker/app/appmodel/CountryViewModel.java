package com.cs50vn.virustracker.app.appmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cs50vn.virustracker.app.controller.worker.CountryLoaderWorker;

import java.util.List;

public class CountryViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    public CountryViewModel(Application application) {
        super(application);

        appRepository = AppRepository.getInstance();
    }


    public void switchTournament(int posTournament) {
        new CountryLoaderWorker().execute(posTournament);

    }
}
