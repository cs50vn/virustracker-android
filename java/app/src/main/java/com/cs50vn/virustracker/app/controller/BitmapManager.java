package com.cs50vn.virustracker.app.controller;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.cs50vn.virustracker.app.MainAct;
import com.cs50vn.virustracker.app.appmodel.AppDAO;
import com.cs50vn.virustracker.app.appmodel.AppRepository;
import com.cs50vn.virustracker.app.model.online.ImageRes;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.AppConfig;
import com.cs50vn.virustracker.app.utils.AppUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

public class BitmapManager implements AssetManager {

    private static BitmapManager instance;

    /////////////////////////////////////////////////////////////////////////
    //Inner data
    private Hashtable<String, ImageRes> data = new Hashtable<>();


    /////////////////////////////////////////////////////////////////////////

    private BitmapManager() {
        initCache();
    }

    public static BitmapManager getInstance() {
        if (instance == null) {
            instance = new BitmapManager();
        }

        return instance;
    }

    private void initCache() {
        AppRepository appRepository = AppRepository.getInstance();
        AppDAO appDAO = appRepository.getAppDAO();

        File des = new File(appRepository.getContext().getCacheDir().getPath(), AppConfig.ASSET_CACHE_FOLDER_NAME);
        data = appDAO.getAssets();
        if (!data.isEmpty() && !des.exists()) {
            appDAO.deleteAssets();
            data.clear();
        }

    }

    @Override
    public Object get(Object key) {
        ImageRes res = (ImageRes) key;
        AppRepository appRepository = AppRepository.getInstance();
        AppDAO appDAO = appRepository.getAppDAO();

        //Check cache folder
        File des = new File(appRepository.getContext().getCacheDir().getPath(), AppConfig.ASSET_CACHE_FOLDER_NAME);
        if (!des.exists()) {
            des.mkdir();
            data.clear();
        }

        if (needUpdate(res)) {
            try {

                //Download image
                String url = res.getUrl();
                Bitmap bmp = ImageLoader.getInstance().loadImageSync(url);

                if (bmp != null) {
                    //Delete a record
                    appDAO.deleteAsset(res);
                    //Insert a record
                    appDAO.insertAsset(res);
                    //Update to mem
                    data.remove(res.getId());
                    data.put(res.getId(), res);
                    //Write to disk
                    String desUrl = AppConfig.ASSET_CACHE_FOLDER_NAME + "/" + res.getId();
                    File file = new File(appRepository.getContext().getCacheDir() + "/" + desUrl);
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        bmp.compress(Bitmap.CompressFormat.WEBP, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //Return data
                    return bmp;

                } else return null;


            } catch (Exception e) {
                PLog.WriteLog(PLog.MAIN_TAG, "Decode fail!!!");
                return null;
            }

        } else {
            String desPath = AppConfig.ASSET_CACHE_FOLDER_NAME + "/" + res.getId();
            String fullUrl = AppRepository.getInstance().getContext().getCacheDir().getAbsolutePath() + "/" + desPath;
            PLog.WriteLog(PLog.MAIN_TAG, fullUrl);
            PLog.WriteLog(PLog.MAIN_TAG, Uri.fromFile(new File(fullUrl)).toString());

            Bitmap bmp = ImageLoader.getInstance().loadImageSync(Uri.fromFile(new File(fullUrl)).toString());

            return bmp;
            //
        }
    }

    private boolean needUpdate(ImageRes res) {
        //True if no contains cache or timestamp is invalid

        if (!data.containsKey(res.getId()) || isValidTmiestamp(res)) return true;

        return false;
    }

    private boolean isValidTmiestamp(ImageRes res) {
        ImageRes innerRes = data.get(res.getId());
        if (res.getTimestamp() > innerRes.getTimestamp()) return true;
        else return false;
    }

}
