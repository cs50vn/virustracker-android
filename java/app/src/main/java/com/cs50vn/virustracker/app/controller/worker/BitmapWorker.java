package com.cs50vn.virustracker.app.controller.worker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.cs50vn.virustracker.app.appmodel.AppRepository;
import com.cs50vn.virustracker.app.controller.BitmapManager;
import com.cs50vn.virustracker.app.model.online.ImageRes;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.AppUtils;

import java.io.InputStream;

public class BitmapWorker extends AsyncTask<Void, Void, Bitmap> {
    private ImageView iv;
    private ImageRes res;

    public BitmapWorker(ImageView iv, ImageRes res) {
        this.iv = iv;
        this.res = res;
    }

    @Override
    protected Bitmap doInBackground(Void... args) {
        return (Bitmap) AppRepository.getInstance().getBitmapManager().get(res);
    }

    @Override
    protected void onPostExecute(Bitmap raw) {
        if (res != null) {
            PLog.WriteLog(PLog.MAIN_TAG, "Got images!");
            iv.setImageBitmap(raw);
        }
    }

    @Override
    protected void onCancelled() {

    }
}
