package com.cs50vn.virustracker.app.controller.worker;

import android.os.AsyncTask;
import android.os.SystemClock;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeRefreshWorker extends AsyncTask<SwipeRefreshLayout, Void, Void> {
    private SwipeRefreshLayout swipe;

    @Override
    protected Void doInBackground(SwipeRefreshLayout... ivs) {
        this.swipe = ivs[0];

        SystemClock.sleep(2000);

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        swipe.setRefreshing(false);
    }

}
