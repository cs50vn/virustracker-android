package com.cs50vn.virustracker.app;

import com.cs50vn.virustracker.app.appmodel.AppRepository;
import com.cs50vn.virustracker.app.model.online.Version;
import com.cs50vn.virustracker.app.tracking.PLog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Request;
import okhttp3.Response;

public class APIRequest {
    private static String NETWORK_PROTOCOL = "https";
    private static String API_URL = "virustracker-api-dev.7perldata.win";
    private static String API_VERSION = "v1";
    public static String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0";

    private static String buildPath(String endpointPath) {
        return NETWORK_PROTOCOL + "://" + API_URL + "/" + API_VERSION + "/" + endpointPath;
    }

    public static String getTopAll() {
        String fullUrl = buildPath("app/topall");
        Request request = new Request.Builder().url(fullUrl).build();
        String data = "";
        try {
            Response response = AppRepository.getInstance().getClient().newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, "Could not connect to topall!!!");
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
            e.printStackTrace();
        }

        return data;
    }

    public static String getTopHome() {
        String fullUrl = buildPath("app/tophome");
        Request request = new Request.Builder().url(fullUrl).build();
        String data = "";
        try {
            Response response = AppRepository.getInstance().getClient().newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, "Could not connect to tophome!!!");
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
            e.printStackTrace();
        }

        return data;
    }

    public static String getTopCountry() {
        String fullUrl = buildPath("app/topcountry");
        Request request = new Request.Builder().url(fullUrl).build();
        String data = "";
        try {
            Response response = AppRepository.getInstance().getClient().newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, "Could not connect to topcountry!!!");
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
            e.printStackTrace();
        }

        return data;
    }

    public static String getCountryDetail(String countryId) {
        String fullUrl = buildPath("app/country/" + countryId);
        Request request = new Request.Builder().url(fullUrl).build();
        String data = "";
        try {
            Response response = AppRepository.getInstance().getClient().newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, "Could not connect to country!!!");
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
            e.printStackTrace();
        }

        return data;
    }


    public static String getVersionStatus(int versionCode) {
        String fullUrl = buildPath("app/version/check?versionCode=" + String.valueOf(versionCode));
        Request request = new Request.Builder().url(fullUrl).build();
        String data = "";
        try {
            Response response = AppRepository.getInstance().getClient().newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, "Could not connect to check version!!!");
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
            e.printStackTrace();
        }

        return data;
    }
}
