package com.cs50vn.virustracker.app.views;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.appmodel.HomeViewModel;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    HomeViewModel homeViewModel;
    PieChart chart1;
    PieChart chart2;
    BarChart chart3;
    BarChart chart4;

    public HomeFragment() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        PLog.WriteLog(PLog.MAIN_TAG, "Home hidden: " + hidden);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = ViewModelProviders.of(this.getActivity()).get(HomeViewModel.class);

        homeViewModel.getTopHome().observe(this, appItem -> {
            PLog.WriteLog(PLog.MAIN_TAG, "ooo: " + appItem.getTotalCases());
        });

        tfLight = Typeface.createFromAsset(view.getContext().getAssets(), "OpenSans-Light.ttf");

        chart1 = view.findViewById(R.id.chart1);
        chart2 = view.findViewById(R.id.chart2);
        chart3 = view.findViewById(R.id.chart3);
        chart4 = view.findViewById(R.id.chart4);

        buildPieChart(chart1);
        buildPieChart(chart2);
        buildBarChart(chart3);
        buildBarChart(chart4);

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

    private void buildPieChart(PieChart chart) {
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterTextTypeface(tfLight);
        chart.setCenterText("World");

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(40f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.getLegend().setEnabled(false);   // Hide the legend
        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        chart.animateY(1400, Easing.EaseInOutQuad);

        chart.setEntryLabelColor(Color.RED);
        chart.setEntryLabelTypeface(tfLight);
        chart.setEntryLabelTextSize(12f);


        setPieData(chart, 6, 50);
    }

    protected Typeface tfLight;

    private final String[] parties = new String[] {
            "North America", "South America", "Party C", "Party DParty C", "Party EParty C", "Party FParty C    ", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };


    private void setPieData(PieChart chart, int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5),
                    "",
                    getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();


        colors.add(ColorTemplate.rgb("003f5c"));
        colors.add(ColorTemplate.rgb("374c80"));
        colors.add(ColorTemplate.rgb("7a5195"));
        colors.add(ColorTemplate.rgb("bc5090"));
        colors.add(ColorTemplate.rgb("ef5675"));
        colors.add(ColorTemplate.rgb("ff764a"));
        colors.add(ColorTemplate.rgb("ffa600"));

            //colors.add(c);




        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);
        chart.disableScroll();
        chart.invalidate();
    }

}
