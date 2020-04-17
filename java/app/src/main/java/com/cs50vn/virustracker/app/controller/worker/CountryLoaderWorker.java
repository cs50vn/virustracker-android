package com.cs50vn.virustracker.app.controller.worker;

import android.os.AsyncTask;

import com.cs50vn.virustracker.app.appmodel.AppRepository;

public class CountryLoaderWorker extends AsyncTask<Integer, Void, Void> {
    private AppRepository appRepository;

    @Override
    protected Void doInBackground(Integer... params) {
        int posTournament = params[0];
        appRepository = AppRepository.getInstance();

        return null;
    }

    @Override
    protected void onPostExecute(Void param) {

    }

}
