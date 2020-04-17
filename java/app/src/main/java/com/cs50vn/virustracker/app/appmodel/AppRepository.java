package com.cs50vn.virustracker.app.appmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.cs50vn.virustracker.app.controller.AssetManager;
import com.cs50vn.virustracker.app.controller.BitmapManager;
import com.cs50vn.virustracker.app.tracking.PLog;

import java.util.List;

public class AppRepository {
    private static AppRepository instance;

    private AppRepository() {
        //Init first for app
        offlineMode = new MutableLiveData<>();
        noDataMode = new MutableLiveData<>();
        noDataRetryMode = new MutableLiveData<>();
        splashScreenMode = new MutableLiveData<>();
    }

    public static AppRepository getInstance() {
        if (instance == null) {
            instance = new AppRepository();
        }

        return instance;
    }

    //NOTE: App data here
    private MutableLiveData<Boolean> offlineMode;
    private boolean internalOfflineMode = false;
    private MutableLiveData<Boolean> noDataMode;
    private boolean internalNoDataMode = false;
    private MutableLiveData<Boolean> noDataRetryMode;
    private boolean internalNoDataRetryMode = false;
    private MutableLiveData<Boolean> splashScreenMode;
    private boolean internalSplashScreenMode = true;

    private Context ctx;

    private HomeRepository homeRepository;
    private CountryRepository countryRepository;
    private AppDAO appDAO;
    private AssetManager bitmapManager;

    public void init(Context ctx) {
        this.ctx = ctx;

        homeRepository = new HomeRepository();
        countryRepository = new CountryRepository();
        bitmapManager = BitmapManager.getInstance();

        setSplashScreenMode(true);
    }

    public Context getContext() {
        return ctx;
    }

    public void setContext(Context ctx) {
        this.ctx = ctx;
    }

    public AppDAO getAppDAO() {
        return AppDAO.getInstance();
    }

    public AssetManager getBitmapManager() {
        return bitmapManager;
    }

    public LiveData<Boolean> isOfflineMode() {
        return offlineMode;
    }

    public void setOfflineMode(boolean status) {
        this.internalOfflineMode = status;

        offlineMode.postValue(internalOfflineMode);
    }

    public LiveData<Boolean> isNoDataMode() {
        return noDataMode;
    }

    public void setNoDataMode(boolean status) {
        this.internalNoDataMode = status;

        PLog.WriteLog(PLog.MAIN_TAG, "=======2  noData: " + status);
        PLog.WriteLog(PLog.MAIN_TAG, "=======2  noData internalNoDataMode: " + internalNoDataMode);
        noDataMode.postValue(internalNoDataMode);
    }

    public LiveData<Boolean> isNoDataRetryMode() {
        return noDataRetryMode;
    }

    public void setNoDataRetryMode(boolean status) {
        this.internalNoDataRetryMode = status;

        noDataRetryMode.postValue(internalNoDataRetryMode);
    }

    public LiveData<Boolean> isSplashScreenMode() {
        return splashScreenMode;
    }

    public void setSplashScreenMode(boolean status) {
        this.internalSplashScreenMode = status;

        splashScreenMode.postValue(internalSplashScreenMode);
    }

    public void destroy() {
        appDAO.close();
    }

    public void pressBack() {
    }

    /////////////////////////////////////////////////////////////////////////////////
    //App repository

    public HomeRepository getHomeRepository() {
        return homeRepository;
    }

    public CountryRepository getCountryRepository() {
        return countryRepository;
    }

    /////////////////////////////////////////////////////////////////////////////////
}
