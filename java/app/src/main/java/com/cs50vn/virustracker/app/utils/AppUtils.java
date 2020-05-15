package com.cs50vn.virustracker.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import com.cs50vn.virustracker.app.appmodel.AppRepository;
import com.cs50vn.virustracker.app.model.online.AppItem;
import com.cs50vn.virustracker.app.model.online.Continent;
import com.cs50vn.virustracker.app.model.online.Country;
import com.cs50vn.virustracker.app.model.online.ImageRes;
import com.cs50vn.virustracker.app.model.online.Item;
import com.cs50vn.virustracker.app.model.online.RecentItem;
import com.cs50vn.virustracker.app.model.online.Version;
import com.cs50vn.virustracker.app.tracking.PLog;

import net.lingala.zip4j.ZipFile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.Collator;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AppUtils {

    public static Date convertUnixTimeToDate(long timestamp){
        return new Date(timestamp * 100);
    }

    public static String convertPercent(Continent con, List<Continent> list) {
        long total = 0;

        for (Continent c: list) {
            total += c.getValue();
        }

        NumberFormat formatter = NumberFormat.getInstance();

        return String.format("%.2f", (float)con.getValue() *100 /total) + "%";
    }

    public static Version parseVersionFromJSON(String data) {
        Version version = null;

        try {
            int id;
            String status, downloadLink;
            JSONObject obj = new JSONObject(data);
            id = obj.getInt("" );

        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, "Could not parse tournament detail content !!!");
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
            e.printStackTrace();

        }
        return version;
    }

    public static HashMap<String, Continent> parseContinentListFromJSON(String data) {
        HashMap<String, Continent> list = new HashMap<>();

        try {
            JSONArray arr = new JSONArray(data);
            for (int index = 0; index < arr.length(); index++) {
                JSONObject obj = arr.getJSONObject(index);
                String id = obj.getString("id");
                String name = obj.getString("name");
                list.put(id, new Continent(id, name, 0));
            }

        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, "Could not parseContinentListFromJSON() !!!");
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
            e.printStackTrace();

        }
        return list;
    }

    public static LinkedList<Country> parseCountryListFromJSON(HashMap<String, Continent> continentList, String data) {
        LinkedList<Country> list = new LinkedList();

        try {
            JSONArray arr = new JSONArray(data);
            for (int index = 0; index < arr.length(); index++) {
                JSONObject obj = arr.getJSONObject(index);
                String id = obj.getString("id");
                String name = obj.getString("name");
                ImageRes res = new ImageRes(obj.getString("flagId"), obj.getString("flagUrl"), obj.getString("flagData"), null, obj.getLong("flagTimestamp"));
                Long timestamp = obj.getLong("timestamp");
                LinkedList<Item> items = new LinkedList<>();
                String continentId = obj.getString("continentId");

                items.add(new Item(obj.getLong("totalCases"), obj.getLong("newCases"), obj.getLong("totalDeaths")));
                list.add(new Country(id, name, "", 0, 0, res, timestamp, continentList.get(continentId), items));
            }

        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, "Could not parseCountryListFromJSON !!!");
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
            e.printStackTrace();

        }
        return list;
    }

    public static AppItem parseAppItemFromJSON(String data) {
        AppItem appItem = new AppItem();

        try {
            long timestamp, totalCases, newCases, totalDeaths, newDeaths, totalRecovered;
            LinkedList<Continent> totalCasesChart = new LinkedList<>();
            LinkedList<Continent> totalDeathsChart = new LinkedList<>();
            LinkedList<RecentItem> totalCasesRecent = new LinkedList<>();
            LinkedList<RecentItem> totalDeathsRecent = new LinkedList<>();
            JSONObject obj = new JSONObject(data);

            timestamp = obj.getInt("timestamp");
            totalCases = obj.getInt("totalCases");
            newCases = obj.getInt("newCases");
            totalDeaths = obj.getInt("totalDeaths");
            newDeaths = obj.getInt("newDeaths");
            totalRecovered = obj.getInt("totalRecovered");
            totalCasesChart = parseChartItemFromJSON(obj.getString("totalCasesChart"));
            totalDeathsChart = parseChartItemFromJSON(obj.getString("totalDeathsChart"));
            totalCasesRecent = parseRecentItemFromJSON(obj.getString("totalCasesRecent"));
            totalDeathsRecent = parseRecentItemFromJSON(obj.getString("totalDeathsRecent"));

            PLog.WriteLog(PLog.MAIN_TAG, "Total items: ==" + totalCasesRecent.size());

            appItem = new AppItem(timestamp, totalCases, newCases, totalDeaths, newDeaths, totalRecovered, totalCasesChart, totalDeathsChart, totalCasesRecent, totalDeathsRecent);
        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, "Could not parse tournament detail content !!!");
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
            e.printStackTrace();

        }
        return appItem;
    }

    public static LinkedList<Continent> parseChartItemFromJSON(String data) {
        LinkedList<Continent> list = new LinkedList<>();
        try {
            JSONArray arr = new JSONArray(data);
            for (int index = 0; index < arr.length(); index++) {
                JSONObject obj = arr.getJSONObject(index);
                list.add(new Continent(obj.getString("continentId"), obj.getString("continentName"), obj.getLong("value")));
            }

        } catch (Exception e) {

        }

        Collections.sort(list, new Comparator<Continent>() {
            @Override
            public int compare(Continent c1, Continent c2) {

                //compare by name return Collator.getInstance().compare(c1.getName(), c2.getName());
                return Long.compare(c2.getValue(), c1.getValue());
            }
        });

        return list;
    }

    public static LinkedList<RecentItem> parseRecentItemFromJSON(String data) {
        LinkedList<RecentItem> list = new LinkedList<>();
        try {
            JSONArray arr = new JSONArray(data);
            for (int index = 0; index < arr.length(); index++) {
                JSONObject obj = arr.getJSONObject(index);
                list.add(new RecentItem(obj.getLong("timestamp"), obj.getInt("value")));
            }

        } catch (Exception e) {

        }

        Collections.sort(list, new Comparator<RecentItem>() {
            @Override
            public int compare(RecentItem c1, RecentItem c2) {

                //compare by name return Collator.getInstance().compare(c1.getName(), c2.getName());
                return Long.compare(c1.getTimestamp(), c2.getTimestamp());
            }
        });

        return list;
    }

    public static AppVersion getVersionFromAssets(Context ctx) {
        String appVersion = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open(AppConfig.APPVERSION_NAME)));

            while ((appVersion = reader.readLine()) != null) {
                break;
            }

            reader.close();

            PLog.WriteLog(PLog.MAIN_TAG, "app_version: " + appVersion);

            //Parse app version, Major.minor.patch from assets
            String[] list = appVersion.split("-");

            if (list != null && list.length > 0) {
                PLog.WriteLog(PLog.MAIN_TAG, "major: " + list[0].trim());
                PLog.WriteLog(PLog.MAIN_TAG, "version id: " + list[1].trim());

                return new AppVersion(Integer.parseInt(list[1]));
            } else {
                return new AppVersion(0);
            }

        } catch (Exception ex) {
            PLog.WriteLog(PLog.MAIN_TAG, "Could not read app_version.txt !!!");
            PLog.WriteLog(PLog.MAIN_TAG, ex.toString());
            ex.printStackTrace();

            return new AppVersion(0);
        }
    }

    public static boolean isFirstLaunch(Context ctx) {
        //What is first launch time?
        //It's app_version is higher than the exist app_version or app_version is not exist
        //In future implementation: OR data integrity (database) fail

        AppVersion av = getVersionFromAssets(ctx);

        SharedPreferences pref = ctx.getSharedPreferences("virustracker", ctx.MODE_PRIVATE);

        AppVersion av1;

        try {
            String version = pref.getString("app_version", "0.0.0-0");
            String[] list1 = version.split("-");

            av1 = new AppVersion(Integer.parseInt(list1[1]));

        } catch (Exception e) {
            av1 = new AppVersion(0);
        }

        if (AppVersion.compare(av, av1) == AppVersion.HIGHER) {
            return true;
        } else {
            return false;
        }

    }

    public static void copyFileFromAssets(Context ctx, String filename, String des) {
        PLog.WriteLog(PLog.MAIN_TAG, des);

        try {
            InputStream reader = ctx.getAssets().open(filename);
            OutputStream writer = new FileOutputStream(des);

            int count = 0;
            byte[] buffer = new byte[8192];
            int length;

            while ((length = reader.read(buffer, 0, 8192)) > 0) {
                writer.write(buffer, 0, length);
                count++;
            }
            PLog.WriteLog(PLog.MAIN_TAG, "Total bytes: " + count * 8192);
            writer.flush();
            writer.close();
            reader.close();
        } catch (Exception ex) {
            PLog.WriteLog(PLog.MAIN_TAG, "Copy database failed!!!");
            PLog.WriteLog(PLog.MAIN_TAG, ex.toString());
            ex.printStackTrace();

        }

    }

    public static void copyFile(Context ctx, File src, File des) {
        //PLog.WriteLog(PLog.MAIN_TAG, src);
        //PLog.WriteLog(PLog.MAIN_TAG, des);

        try {
            InputStream reader = new FileInputStream(src);
            OutputStream writer = new FileOutputStream(des);

            int count = 0;
            byte[] buffer = new byte[8192];
            int length;

            while ((length = reader.read(buffer, 0, 8192)) > 0) {
                writer.write(buffer, 0, length);
                count++;
            }
            PLog.WriteLog(PLog.MAIN_TAG, "Total bytes: " + count * 8192);
            writer.flush();
            writer.close();
            reader.close();

        } catch (Exception ex) {
            PLog.WriteLog(PLog.MAIN_TAG, "Copy file failed!!!");
            PLog.WriteLog(PLog.MAIN_TAG, ex.toString());
            ex.printStackTrace();

        }
    }

    public static void copyFolder(Context ctx, File src, File des) {

        try {
            if (src.isDirectory()) {
                if (!des.exists()) {
                    des.mkdirs();
                }

                String[] files = src.list();

                for (String item : files) {
                    File srcFile = new File(src, item);
                    File desFile = new File(des, item);

                    copyFolder(ctx, srcFile, desFile);
                }

            } else {
                copyFile(ctx, src, des);
                PLog.WriteLog(PLog.MAIN_TAG, "Copied " + src.getPath() + " to " + des.getPath());
            }


        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, "Copy folder failed!!!");
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
            e.printStackTrace();
        }
    }

    public static void unZipFile(String filename) {
        try {
            File f = new File(filename);
            ZipFile zip = new ZipFile(filename);

            zip.extractAll(f.getParent());
        } catch (Exception e) {
            PLog.WriteLog(PLog.MAIN_TAG, e.toString());
        }
    }

    public static void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        fileOrDirectory.delete();
    }

    public int getStatusBarHeight(Context ctx) {
        int result = 0;
        int resourceId = ctx.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ctx.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void getPathFromDevice(Context ctx) {

        File file = ctx.getFilesDir();
        PLog.WriteLog(PLog.MAIN_TAG, "=========BEGIN Internal Storage====================");
        PLog.WriteLog(PLog.MAIN_TAG, "getName(): " + file.getName());
        PLog.WriteLog(PLog.MAIN_TAG, "getPath():         " + file.getPath());
        PLog.WriteLog(PLog.MAIN_TAG, "getAbsolutePath(): " + file.getAbsolutePath());
        PLog.WriteLog(PLog.MAIN_TAG, "=========END Internal Storage====================");

        file = ctx.getDatabasePath("a.db");
        PLog.WriteLog(PLog.MAIN_TAG, "=========BEGIN Database - Internal Storage====================");
        PLog.WriteLog(PLog.MAIN_TAG, "getName(): " + file.getName());
        PLog.WriteLog(PLog.MAIN_TAG, "getPath():         " + file.getPath());
        PLog.WriteLog(PLog.MAIN_TAG, "getAbsolutePath(): " + file.getAbsolutePath());
        PLog.WriteLog(PLog.MAIN_TAG, "=========END Database - Internal Storage====================");

        file = ctx.getExternalFilesDir(null);
        PLog.WriteLog(PLog.MAIN_TAG, "=========BEGIN Private - External Storage====================");
        PLog.WriteLog(PLog.MAIN_TAG, "getName(): " + file.getName());
        PLog.WriteLog(PLog.MAIN_TAG, "getPath():         " + file.getPath());
        PLog.WriteLog(PLog.MAIN_TAG, "getAbsolutePath(): " + file.getAbsolutePath());
        PLog.WriteLog(PLog.MAIN_TAG, "=========END Private - External Storage=====================");

        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        PLog.WriteLog(PLog.MAIN_TAG, "=========BEGIN Public - External Storage====================");
        PLog.WriteLog(PLog.MAIN_TAG, "getName(): " + file.getName());
        PLog.WriteLog(PLog.MAIN_TAG, "getPath():         " + file.getPath());
        PLog.WriteLog(PLog.MAIN_TAG, "getAbsolutePath(): " + file.getAbsolutePath());
        PLog.WriteLog(PLog.MAIN_TAG, "=========END Public - External Storage=====================");

        file = Environment.getExternalStorageDirectory();
        PLog.WriteLog(PLog.MAIN_TAG, "=========BEGIN Primary - External Storage====================");
        PLog.WriteLog(PLog.MAIN_TAG, "getName(): " + file.getName());
        PLog.WriteLog(PLog.MAIN_TAG, "getPath():         " + file.getPath());
        PLog.WriteLog(PLog.MAIN_TAG, "getAbsolutePath(): " + file.getAbsolutePath());
        PLog.WriteLog(PLog.MAIN_TAG, "=========END Primary - External Storage=====================");

    }

    public static boolean compareDate(long date1, long date2) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date1 * 1000L);
        int day1 = cal.get(cal.DAY_OF_MONTH);
        int month1 = cal.get(cal.MONTH);
        int year1 = cal.get(cal.YEAR);

        cal.setTimeInMillis(date2 * 1000L);
        int day2 = cal.get(cal.DAY_OF_MONTH);
        int month2 = cal.get(cal.MONTH);
        int year2 = cal.get(cal.YEAR);

        return (day1 == day2 && month1 == month2 && year1 == year2);
    }

    public static String getPackageName() {
        return AppRepository.getInstance().getContext().getPackageName();
    }

    public static Bitmap decodeImageBase64(String data) {
        byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }

}
