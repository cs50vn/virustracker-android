package com.cs50vn.virustracker.app.appmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    public HomeViewModel(Application application) {
        super(application);

        appRepository = AppRepository.getInstance();
    }

}
