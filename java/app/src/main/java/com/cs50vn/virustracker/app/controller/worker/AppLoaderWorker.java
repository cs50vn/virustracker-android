package com.cs50vn.virustracker.app.controller.worker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;

import com.cs50vn.virustracker.app.utils.AppConfig;
import com.cs50vn.virustracker.app.utils.AppUtils;
import com.cs50vn.virustracker.app.APIRequest;

import com.cs50vn.virustracker.app.appmodel.AppRepository;
import com.cs50vn.virustracker.app.tracking.PLog;

import org.json.JSONObject;

import java.io.File;

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

        SystemClock.sleep(2000);

        return null;
    }

    @Override
    protected void onPostExecute(Void param) {
        appRepository.setSplashScreenMode(false);
    }

    @Override
    protected void onCancelled() {

    }
}
