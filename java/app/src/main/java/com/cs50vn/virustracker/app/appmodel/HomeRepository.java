package com.cs50vn.virustracker.app.appmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cs50vn.virustracker.app.model.online.AppItem;
import com.cs50vn.virustracker.app.tracking.PLog;

import java.util.LinkedList;
import java.util.List;

public class HomeRepository {

    private MutableLiveData<AppItem> appItem;
    private AppItem internalAppItem;

    public HomeRepository() {
        appItem = new MutableLiveData<>();
        internalAppItem = new AppItem();
    }

    public LiveData<AppItem> getTopHome() {
        return appItem;
    }


    public void setInternalAppItem(AppItem item) {
        internalAppItem = item;

        appItem.postValue(internalAppItem);
    }
}
