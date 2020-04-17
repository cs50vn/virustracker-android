package com.cs50vn.virustracker.app.appmodel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cs50vn.virustracker.app.model.online.ImageRes;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.AppConfig;
import java.util.Hashtable;

public class AppDAO {

    private static AppDAO instance;

    private AppDAO() {
        init();
    }

    public static AppDAO getInstance() {
        if (instance == null) {
            instance = new AppDAO();
        }

        return instance;
    }

    //App data
    private Context ctx;
    private SQLiteDatabase appdb;
    private SQLiteDatabase academydb;

    private void init() {
        ctx = AppRepository.getInstance().getContext();
        appdb = SQLiteDatabase.openDatabase(ctx.getDatabasePath(AppConfig.APPDB_NAME).getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);

        PLog.WriteLog("duy", "Opened db");
    }

    public void close() {
        if (appdb != null)
            appdb.close();
    }

    /////////////////////////////////////////////////////////////////////////
    //Cache

    public Hashtable<String, ImageRes> getAssets() {
        Hashtable<String, ImageRes> list = new Hashtable<>();

        Cursor resultSet = appdb.rawQuery(AppConfig.SQL_GET_IMAGES, new String[]{});
        while (resultSet.moveToNext()) {
            String id, url;
            long timestamp;

            id = resultSet.getString(0);
            url = resultSet.getString(1);
            timestamp = resultSet.getLong(2);

            ImageRes res = new ImageRes(id, url, "", null, timestamp);
            list.put(id, res);
        }

        return list;
    }

    public void deleteAssets() {
        appdb.execSQL(AppConfig.SQL_DELETE_IMAGES, new Object[]{ });
    }

    public void deleteAsset(ImageRes res) {
        appdb.execSQL(AppConfig.SQL_DELETE_IMAGE, new Object[]{ res.getId() });
    }

    public void insertAsset(ImageRes res) {
        appdb.execSQL(AppConfig.SQL_INSERT_IMAGE, new Object[]{
            res.getId(),
            res.getUrl(),
            res.getTimestamp()
        });
    }

}

