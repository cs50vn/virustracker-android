package com.cs50vn.virustracker.app.views;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.appmodel.HomeViewModel;
import com.cs50vn.virustracker.app.controller.TotalCasesAdapter;
import com.cs50vn.virustracker.app.model.online.AppItem;
import com.cs50vn.virustracker.app.model.online.Continent;
import com.cs50vn.virustracker.app.model.online.RecentItem;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.AppConfig;
import com.cs50vn.virustracker.app.utils.DayAxisValueFormatter;
import com.cs50vn.virustracker.app.utils.MyDayAxisFormatter;
import com.cs50vn.virustracker.app.utils.MyPercentFormatter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    HomeViewModel homeViewModel;
    View parent;
    AppItem appItem;

    public HomeFragment() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        PLog.WriteLog(PLog.MAIN_TAG, "Home hidden: " + hidden);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = ViewModelProviders.of(this.getActivity()).get(HomeViewModel.class);

        homeViewModel.getTopHome().observe(this, appItem -> {
            PLog.WriteLog(PLog.MAIN_TAG, "ooo: " + appItem.getTotalCases());
            buildHome(appItem);
        });

        tfLight = ResourcesCompat.getFont(getContext(), R.font.opensans_bold);

        return parent;
    }

    private void buildHome(AppItem item) {
        if (item != null) {
            appItem = item;
            buildStat();
            buildPieCharts();
            buildBarCharts();
        }
    }

    private void buildStat() {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setGroupingUsed(true);

        TextView tv5 = parent.findViewById(R.id.fragment_home_lastupdated);
        String date = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        tv5.setText("Last updated: " + date);

        TextView tv = parent.findViewById(R.id.fragment_home_stat_totalcases);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, (int) appItem.getTotalCases());
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator ani) {
                tv.setText(formatter.format(ani.getAnimatedValue()));
            }
        });
        valueAnimator.start();



        TextView tv1 = parent.findViewById(R.id.fragment_home_stat_newcases);
        tv1.setText(formatter.format(appItem.getNewCases()));

        TextView tv2 = parent.findViewById(R.id.fragment_home_stat_totaldeaths);
        tv2.setText(formatter.format(appItem.getTotalDeaths()));

        TextView tv3 = parent.findViewById(R.id.fragment_home_stat_newdeaths);
        tv3.setText(formatter.format(appItem.getNewDeaths()));

        TextView tv4 = parent.findViewById(R.id.fragment_home_stat_totalrecovered);
        tv4.setText(formatter.format(appItem.getTotalRecovered()));
    }

//////////////////////////////////////////////////////////////////////////////
//Pie chart

    private void buildPieCharts() {
        PieChart chart1 = parent.findViewById(R.id.fragment_home_totalcases_chart);
        RecyclerView rv1 = parent.findViewById(R.id.fragment_home_totalcases_recyclerview);
        buildPieChart(chart1, rv1, appItem.getTotalCasesChart(), appItem.getTotalCases());

        PieChart chart2 = parent.findViewById(R.id.fragment_home_totaldeaths_chart);
        RecyclerView rv2 = parent.findViewById(R.id.fragment_home_totaldeaths_recyclerview);
        buildPieChart(chart2, rv2, appItem.getTotalDeathsChart(), appItem.getTotalDeaths());
    }

    private void buildPieChart(PieChart chart, RecyclerView recyclerView, LinkedList<Continent> list, long total) {
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterTextTypeface(tfLight);
        chart.setCenterText(getContext().getString(R.string.piechart_title));
        chart.setDrawCenterText(true);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(40f);
        chart.setTransparentCircleRadius(61f);

        chart.getLegend().setEnabled(false);   // Hide the legend
        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);

        chart.animateY(2000, Easing.EaseInOutQuad);

        chart.setEntryLabelColor(Color.RED);
        chart.setEntryLabelTypeface(tfLight);
        chart.setEntryLabelTextSize(12f);

        setPieData(chart, list, total, AppConfig.colors);
        setPieLegend(recyclerView, list, AppConfig.colors);
    }

    private void setPieLegend(RecyclerView recyclerView, LinkedList<Continent> list, ArrayList<Integer> colors) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        TotalCasesAdapter adapter = new TotalCasesAdapter(getContext(), list, colors);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setPieData(PieChart chart, LinkedList<Continent> list, long total, ArrayList<Integer> colors) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (Continent con: list) {
            entries.add(new PieEntry(((float)(con.getValue() * 100) / total)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        dataSet.setColors(AppConfig.colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new MyPercentFormatter(chart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);
        chart.disableScroll();
        chart.invalidate();
    }

//////////////////////////////////////////////////////////////////////////////
//Bar chart

    private void buildBarCharts() {
        BarChart chart3 = parent.findViewById(R.id.fragment_home_recentcases_chart);
        buildBarChart(chart3, appItem.getTotalCasesRecent(), AppConfig.colors);

        BarChart chart4 = parent.findViewById(R.id.fragment_home_recentdeaths_chart);
        buildBarChart(chart4, appItem.getTotalDeathsRecent(), AppConfig.colors);
    }


    private void buildBarChart(BarChart chart, LinkedList<RecentItem> list, ArrayList<Integer> colors) {

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setMaxVisibleValueCount(7);
        chart.setPinchZoom(false);
        chart.setScaleEnabled(false);

        chart.setDrawGridBackground(false);

        ValueFormatter xAxisFormatter = new MyDayAxisFormatter(list);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(list.size());
        xAxis.setValueFormatter(xAxisFormatter);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        setBarData(chart, list, colors);
    }

    private void setBarData(BarChart chart, LinkedList<RecentItem> list, ArrayList<Integer> colors) {
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int index = 0; index < list.size(); ++index) {
            RecentItem item = list.get(index);
            entries.add(new BarEntry(index, item.getValue()));
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(entries, "Data Set");

            set1.setColors(colors);
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

    protected Typeface tfLight;

    private final RectF onValueSelectedRectF = new RectF();




}
