package com.cs50vn.virustracker.app.controller.worker;

import android.os.AsyncTask;
import android.os.SystemClock;

import com.cs50vn.virustracker.app.APIRequest;
import com.cs50vn.virustracker.app.appmodel.AppRepository;
import com.cs50vn.virustracker.app.model.online.Country;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.AppUtils;

import org.json.JSONObject;

public class CountryDetailRefreshWorker extends AsyncTask<Integer, Void, Void> {

    @Override
    protected Void doInBackground(Integer... params) {
        int type = params[0];
        AppRepository appRepository = AppRepository.getInstance();

        PLog.WriteLog(PLog.MAIN_TAG, "type: " + type);
        if (type == 0) {
            appRepository.getCountryRepository().setNoDataRetryMode(true);
            SystemClock.sleep(1000);
        }

        Country country = appRepository.getCountryRepository().getCountryDetail().getValue();
        if (country != null) {
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

                        appRepository.getCountryRepository().setNoDataRetryMode(false);
                        appRepository.getCountryRepository().setNoDataMode(false);
                    }

                } catch (Exception e) {

                }

            } else {
                appRepository.getCountryRepository().setNoDataRetryMode(false);
                appRepository.getCountryRepository().setNoDataMode(true);
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void param) {

    }

}
