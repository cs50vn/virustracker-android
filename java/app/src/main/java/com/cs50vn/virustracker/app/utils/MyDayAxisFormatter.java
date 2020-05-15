package com.cs50vn.virustracker.app.utils;

import com.cs50vn.virustracker.app.model.online.RecentItem;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class MyDayAxisFormatter extends ValueFormatter {

    private LinkedList<RecentItem> list;

    public MyDayAxisFormatter(LinkedList<RecentItem> list) {
        this.list = list;
    }

    @Override
    public String getFormattedValue(float value) {

        RecentItem item = list.get((int)value);
        PLog.WriteLog(PLog.MAIN_TAG, item.getTimestamp());

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(item.getTimestamp() * 1000);
        DateFormat formatter = new SimpleDateFormat("MMM-dd");
        String date = formatter.format(cal.getTime());

        PLog.WriteLog(PLog.MAIN_TAG, date);
        return date;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return super.getFormattedValue(value, entry, dataSetIndex, viewPortHandler);
    }

}
