package com.cs50vn.virustracker.app.appmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.cs50vn.virustracker.app.controller.AssetManager;
import com.cs50vn.virustracker.app.controller.BitmapManager;
import com.cs50vn.virustracker.app.model.online.Country;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.CountrySortEnum;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class AppRepository {
    private static AppRepository instance;

    private AppRepository() {
        //Init first for app
        offlineMode = new MutableLiveData<>();
        noDataMode = new MutableLiveData<>();
        noDataRetryMode = new MutableLiveData<>();
        splashScreenMode = new MutableLiveData<>();
        countryDetailMode = new MutableLiveData<>();
        currentFragment = new MutableLiveData<>();
        showNotification = new MutableLiveData<>();
        exitApp = new MutableLiveData<>();
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
    private MutableLiveData<Boolean> countryDetailMode;
    private boolean internalCountryDetailMode = true;
    private MutableLiveData<String> currentFragment;
    private String internalCurrentFragment = "HomeFragment";
    private MutableLiveData<Object> showNotification;
    private MutableLiveData<Object> exitApp;

    private Context ctx;

    private int exitCount = 2;
    private HomeRepository homeRepository;
    private CountryRepository countryRepository;
    private AppDAO appDAO;
    private AssetManager bitmapManager;
    private OkHttpClient client = new OkHttpClient.Builder().callTimeout(10, TimeUnit.SECONDS).build();

    public void init(Context ctx) {
        this.ctx = ctx;

        homeRepository = new HomeRepository();
        countryRepository = new CountryRepository();
        bitmapManager = BitmapManager.getInstance();

        setSplashScreenMode(true);
        countryRepository.setCountrySortEnum(CountrySortEnum.TOTAL_CASES);
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

    public OkHttpClient getClient() {
        return client;
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

    public LiveData<Boolean> isCountryDetailMode() {
        return countryDetailMode;
    }

    public void setCountryDetailMode(boolean status) {
        this.internalCountryDetailMode = status;

        countryDetailMode.postValue(internalCountryDetailMode);
    }

    public void destroy() {
        
    }

    public void goToCountryDetail(Country country) {
        setCountryDetailMode(true);
    }


    public void pressBack() {
        if (internalCurrentFragment.equals("HomeFragment") ||
                internalCurrentFragment.equals("CountryFragment") ||
                internalCurrentFragment.equals("AboutFragment")) {
            exitCount--;
            if (exitCount == 0) {
                exitApp.postValue(null);
            } else {
                destroy();
                showNotification.postValue(null);
            }

        } else if (internalCurrentFragment.equals("CountryDetailFragment")) {
            setCountryDetailMode(false);
        }
    }

    public void setCurrentFragment(String className) {
        internalCurrentFragment = className;
        exitCount = 2;
    }

    public LiveData<String> onPressBack() {
        return currentFragment;
    }

    public LiveData<Object> showNotification() {
        return showNotification;
    }

    public LiveData<Object> exitApp() {
        return exitApp;
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
