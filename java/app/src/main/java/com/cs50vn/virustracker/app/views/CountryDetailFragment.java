package com.cs50vn.virustracker.app.views;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryDetailFragment extends Fragment {

    BarChart chart1;
    BarChart chart2;
    Typeface tfLight;

    public CountryDetailFragment() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        PLog.WriteLog(PLog.MAIN_TAG, "Home hidden: " + hidden);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_detail, container, false);


        tfLight = Typeface.createFromAsset(view.getContext().getAssets(), "OpenSans-Light.ttf");

        chart1 = view.findViewById(R.id.fragment_country_detail_chart1);
        chart2 = view.findViewById(R.id.fragment_country_detail_chart2);

        buildBarChart(chart1);
        buildBarChart(chart2);

        return view;
    }

    private void buildBarChart(BarChart chart) {
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setMaxVisibleValueCount(7);
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        chart.getAxisLeft().setDrawGridLines(false);


        // add a nice and smooth animation
        chart.animateY(1500);

        chart.getLegend().setEnabled(false);

        setBarData(chart, 7, 42000);
    }

    private void setBarData(BarChart chart, int count, float range) {
        ArrayList<BarEntry> values = new ArrayList<>();

        for (int index = 0; index < count; ++index) {
            values.add(new BarEntry(index, 1 * index));
        }


        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
            set1.setDrawValues(true);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);
            chart.setData(data);
            chart.setFitBars(true);
        }

        chart.invalidate();
    }

}
