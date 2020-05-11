package com.cs50vn.virustracker.app.appmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cs50vn.virustracker.app.controller.worker.AppLoaderWorker;

public class AppViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    public AppViewModel(Application application) {
        super(application);

        appRepository = AppRepository.getInstance();
    }

    public void initApp(Context ctx) {
        new AppLoaderWorker().execute(ctx);
    }

    public void destroyApp() {
        appRepository.destroy();
    }

    public void pressBack() {
        appRepository.pressBack();
    }

    public LiveData<Boolean> isSplashScreenMode() {
        return appRepository.isSplashScreenMode();
    }

    public LiveData<Boolean> isOfflineMode() {
        return appRepository.isOfflineMode();
    }

    public LiveData<Boolean> isNoDataMode() {
        return appRepository.isNoDataMode();
    }

    public LiveData<Boolean> isNoDataRetryMode() {
        return appRepository.isNoDataRetryMode();
    }

    public LiveData<Boolean> isHideNavigationMode() {
        return appRepository.isHideNavigationMode();
    }
}
