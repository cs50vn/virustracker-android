package com.cs50vn.virustracker.app.appmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cs50vn.virustracker.app.model.online.AppItem;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    public HomeViewModel(Application application) {
        super(application);

        appRepository = AppRepository.getInstance();
    }

    public LiveData<AppItem> getTopHome() {
        return appRepository.getHomeRepository().getTopHome();
    }

}
