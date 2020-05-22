package com.cs50vn.virustracker.app.controller.worker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.cs50vn.virustracker.app.APIRequest;
import com.cs50vn.virustracker.app.appmodel.AppRepository;
import com.cs50vn.virustracker.app.model.online.AppItem;
import com.cs50vn.virustracker.app.model.online.Continent;
import com.cs50vn.virustracker.app.model.online.Country;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.AppConfig;
import com.cs50vn.virustracker.app.utils.AppUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class AppRefreshWorker extends AsyncTask<Integer, Void, Void> {
    private AppRepository appRepository;
    private Context ctx;

    @Override
    protected Void doInBackground(Integer... params) {
        //Type: 1: refresh, 0: retry
        int type = params[0];
        appRepository = AppRepository.getInstance();

        if (type == 0) {
            appRepository.setNoDataRetryMode(true);
        }

        String data = APIRequest.getTopAll();
        PLog.WriteLog(PLog.MAIN_TAG, data);

        if (!data.equals("")) {
            try {
                JSONObject obj = new JSONObject(data);
                int statusCode = obj.getInt("statusCode");
                if (statusCode == 200) {
                    AppItem appItem = AppUtils.parseAppItemFromJSON(obj.getJSONObject("data").getString("tophome"));
                    HashMap<String, Continent> continentList = AppUtils.parseContinentListFromJSON(obj.getJSONObject("data").getJSONObject("topcountry").getString("continentList"));
                    LinkedList<Country> countryList = AppUtils.parseCountryListFromJSON(continentList, obj.getJSONObject("data").getJSONObject("topcountry").getString("countryList"));

                    AppRepository.getInstance().getHomeRepository().setInternalAppItem(appItem);
                    AppRepository.getInstance().getCountryRepository().setContinentList(continentList);
                    AppRepository.getInstance().getCountryRepository().setInternalCountryList(countryList);
                    AppRepository.getInstance().getCountryRepository().setInternalSearchCountryList(countryList);

                    PLog.WriteLog(PLog.MAIN_TAG, continentList.size());
                    PLog.WriteLog(PLog.MAIN_TAG, countryList.size());

                    appRepository.setNoDataRetryMode(false);
                    appRepository.setNoDataMode(false);
                }

            } catch (Exception e) {
                PLog.WriteLog(PLog.MAIN_TAG, "Could not parse refresh topall !!!");
                PLog.WriteLog(PLog.MAIN_TAG, e.toString());
                e.printStackTrace();
            }

        } else {
            appRepository.setNoDataRetryMode(false);
            appRepository.setNoDataMode(true);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void param) {

    }

    @Override
    protected void onCancelled() {

    }
}
