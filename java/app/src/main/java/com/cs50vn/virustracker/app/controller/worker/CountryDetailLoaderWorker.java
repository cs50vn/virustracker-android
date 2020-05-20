package com.cs50vn.virustracker.app.controller.worker;

import android.os.AsyncTask;

import com.cs50vn.virustracker.app.APIRequest;
import com.cs50vn.virustracker.app.appmodel.AppRepository;
import com.cs50vn.virustracker.app.model.online.AppItem;
import com.cs50vn.virustracker.app.model.online.Continent;
import com.cs50vn.virustracker.app.model.online.Country;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.AppUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

public class CountryDetailLoaderWorker extends AsyncTask<Country, Void, Void> {
    private AppRepository appRepository;

    @Override
    protected Void doInBackground(Country... params) {
        Country country = params[0];
        appRepository = AppRepository.getInstance();
        appRepository.goToCountryDetail(country);

        String data = APIRequest.getCountryDetail(country.getId());
        PLog.WriteLog(PLog.MAIN_TAG, data);

        if (!data.equals("")) {
            try {
                JSONObject obj = new JSONObject(data);
                int statusCode = obj.getInt("statusCode");
                if (statusCode == 200) {
                    Country c = AppUtils.parseCountryDetail(obj.getString("data"));

                    appRepository.getCountryRepository().setInternalCountryDetail(c);

                    PLog.WriteLog(PLog.MAIN_TAG, c);
                }

            } catch (Exception e) {

            }

        } else {

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void param) {

    }

}
