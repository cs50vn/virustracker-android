package com.cs50vn.virustracker.app.controller.worker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.cs50vn.virustracker.app.model.online.AppItem;
import com.cs50vn.virustracker.app.model.online.Continent;
import com.cs50vn.virustracker.app.model.online.Country;
import com.cs50vn.virustracker.app.model.online.Version;
import com.cs50vn.virustracker.app.utils.AppConfig;
import com.cs50vn.virustracker.app.utils.AppUtils;
import com.cs50vn.virustracker.app.APIRequest;

import com.cs50vn.virustracker.app.appmodel.AppRepository;
import com.cs50vn.virustracker.app.tracking.PLog;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AppLoaderWorker extends AsyncTask<Context, Void, Void> {
    private AppRepository appRepository;
    private Context ctx;

    @Override
    protected Void doInBackground(Context... params) {
        ctx = params[0];

        appRepository = AppRepository.getInstance();

        //Check first launch time
        if (AppUtils.isFirstLaunch(ctx)) {
            //Clean up previous version old data if exist
            File tmpFolder = new File(ctx.getFilesDir().getPath(), "tmp");
            if (tmpFolder.exists())
                AppUtils.deleteRecursive(tmpFolder);

            File appDbFile = new File(ctx.getDatabasePath(AppConfig.APPDB_NAME), "");
            if (appDbFile.exists())
                appDbFile.delete();

            //Copy packed_data.zip to /data/data/<package name>/files/tmp
            String src = AppConfig.PACKED_DATA_NAME;
            File des = new File(ctx.getFilesDir().getPath(), "tmp");

            if (!des.exists()) {
                des.mkdir();
            }

            AppUtils.copyFileFromAssets(ctx, src, des.getPath() + "/" + AppConfig.PACKED_DATA_NAME);

            //Unzip packed_data.zip
            AppUtils.unZipFile(des.getPath() + "/" + AppConfig.PACKED_DATA_NAME);

            //Copy content of packed data to destination
            //Copy app.db, academy.db to /data/data/<package name>/files/databases
            if (!ctx.getDatabasePath(AppConfig.APPDB_NAME).exists())
                ctx.getDatabasePath(AppConfig.APPDB_NAME).getParentFile().mkdirs();

            AppUtils.copyFile(ctx, new File(des, AppConfig.PACKED_DATA_FOLDER_NAME + "/" + AppConfig.APPDB_NAME), ctx.getDatabasePath(AppConfig.APPDB_NAME));

            //Clean up tmp folder
            AppUtils.deleteRecursive(tmpFolder);

            //Set app_version to Prefs
            SharedPreferences pref = ctx.getSharedPreferences("virustracker", ctx.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("app_version", AppUtils.getVersionFromAssets(ctx).toString());
            editor.commit();

        }

        appRepository.init(ctx);


        String data = APIRequest.getVersionStatus(AppUtils.getVersionFromAssets(ctx).getVersionId());
        PLog.WriteLog(PLog.MAIN_TAG, data);

        if (!data.equals("")) {
            try {
                JSONObject responseObj = new JSONObject(data);
                int statusCode = responseObj.getInt("statusCode");
                if (statusCode == 200) {
                    //Parse version status
                    int requestVersionCode, hasNewVersion;
                    String status, downloadLink;
                    JSONObject obj = responseObj.getJSONObject("data");
                    hasNewVersion = obj.getInt("hasNewVersion");
                    status = obj.getString("status");
                    downloadLink = obj.getString("downloadLink");

                    //Write to preferences
                    SharedPreferences pref = ctx.getSharedPreferences("virustracker", ctx.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("hasNewVersion", hasNewVersion);
                    editor.putString("status", status);
                    editor.putString("downloadLink", downloadLink);
                    editor.commit();
                    if (hasNewVersion == 1 && status == "force_update") {

                    } else {
                        getTopAll();
                    }


                }


            }  catch (Exception e) {
                PLog.WriteLog(PLog.MAIN_TAG, "Could not parse version status !!!");
                PLog.WriteLog(PLog.MAIN_TAG, e.toString());
                e.printStackTrace();
            }

        } else {

        }

        return null;
    }

    private void getTopAll() {
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

                    PLog.WriteLog(PLog.MAIN_TAG, continentList.size());
                    PLog.WriteLog(PLog.MAIN_TAG, countryList.size());
                }

            } catch (Exception e) {

            }

        } else {

        }


    }

    @Override
    protected void onPostExecute(Void param) {
        appRepository.setSplashScreenMode(false);
    }

    @Override
    protected void onCancelled() {

    }
}
