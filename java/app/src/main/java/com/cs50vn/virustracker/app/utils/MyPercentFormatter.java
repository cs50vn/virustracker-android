package com.cs50vn.virustracker.app.utils;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MyPercentFormatter extends PercentFormatter {

    public MyPercentFormatter() {
        super();
    }

    public MyPercentFormatter(PieChart pieChart) {
        super(pieChart);
    }

    @Override
    public String getFormattedValue(float value) {
        DecimalFormat mFormat = new DecimalFormat("###,###,##0.00");
        return mFormat.format(value) + " %";
    }

}
