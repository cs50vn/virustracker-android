package com.cs50vn.virustracker.app;

import com.cs50vn.virustracker.app.tracking.PLog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class APIRequest {
    private static String NETWORK_PROTOCOL = "https";
    private static String API_URL = "virustracker-api-dev.7perldata.win";
    private static String API_VERSION = "v1";
    public static String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0";

    private static String buildPath(String endpointPath) {
        return NETWORK_PROTOCOL + "://" + API_URL + "/" + API_VERSION + "/" + endpointPath;
    }

    public static String getTopAll() {
        StringBuilder responseData = new StringBuilder("");
        try {
            //Thread.sleep(100);

            URL fullUrl = null;
            HttpURLConnection conn = null;


            fullUrl = new URL(buildPath("app/topall"));
            conn = (HttpsURLConnection) fullUrl.openConnection();

            PLog.WriteLog(PLog.MAIN_TAG, "getTopAll(): " + fullUrl.toString());
            conn.setConnectTimeout(10 * 1000);
            conn.setReadTimeout(20 * 1000);
            conn.setRequestProperty("User-Agent", USERAGENT);


            String tmp = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            while ((tmp = reader.readLine()) != null) {
                responseData.append(tmp);
            }

            reader.close();

        } catch (Exception e) {

        }

        return responseData.toString();
    }

}
