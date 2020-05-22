package com.cs50vn.virustracker.app.utils;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class AppConfig {
    public static String PACKED_DATA_NAME = "packed_data.zip";
    public static String PACKED_DATA_FOLDER_NAME = "packed_data";
    public static String APPDB_NAME = "app.db";
    public static String APPVERSION_NAME = "app_version.txt";
    public static String ASSET_CACHE_FOLDER_NAME = "asset";

    //Image cache
    public static String SQL_GET_IMAGES = "select * from ASSET_CACHE";
    public static String SQL_DELETE_IMAGES = "delete from ASSET_CACHE";
    public static String SQL_INSERT_IMAGE = "insert into ASSET_CACHE values(?, ?, ?)";
    public static String SQL_DELETE_IMAGE = "delete from ASSET_CACHE where asset_id = ?";

    //App
    public static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0";

    public static ArrayList<Integer> colors = new ArrayList<Integer>(Arrays.asList(
            ColorTemplate.rgb("003f5c"), ColorTemplate.rgb("8cba51"),
            ColorTemplate.rgb("ef5675"), ColorTemplate.rgb("ff764a"),
            ColorTemplate.rgb("7a5195"), ColorTemplate.rgb("bc5090"),
            ColorTemplate.rgb("ffa600")));

    //Number pattern
    public static String FLOATING_PATTERN = "###,###.##";
    public static String DECIMAL_PATTERN = "###,###";
}
