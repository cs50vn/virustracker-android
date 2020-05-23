package com.cs50vn.virustracker.app.views;

import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.appmodel.AppViewModel;
import com.cs50vn.virustracker.app.appmodel.CountryViewModel;
import com.cs50vn.virustracker.app.appmodel.HomeViewModel;
import com.cs50vn.virustracker.app.controller.worker.BitmapWorker;
import com.cs50vn.virustracker.app.model.online.AppItem;
import com.cs50vn.virustracker.app.model.online.Country;
import com.cs50vn.virustracker.app.model.online.Item;
import com.cs50vn.virustracker.app.model.online.RecentItem;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.AppConfig;
import com.cs50vn.virustracker.app.utils.AppUtils;
import com.cs50vn.virustracker.app.utils.MyDayAxisFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryDetailFragment extends Fragment {

    private CountryViewModel countryViewModel;
    private AppViewModel appViewModel;
    private View parent;
    private Typeface tfLight;
    private Country country;

    public CountryDetailFragment() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //PLog.WriteLog(PLog.MAIN_TAG, "Home hidden: " + hidden);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent = inflater.inflate(R.layout.fragment_country_detail, container, false);
        countryViewModel = ViewModelProviders.of(this.getActivity()).get(CountryViewModel.class);
        appViewModel = ViewModelProviders.of(this.getActivity()).get(AppViewModel.class);

        ////////////////////////////////////////////////////////////////////////////
        //Handle network issue
        SwipeRefreshLayout content = parent.findViewById(R.id.country_detail_swipe_content);
        View loadingScreen = parent.findViewById(R.id.country_detail_loading);
        View noDataScreen = parent.findViewById(R.id.country_detail_network_error);

        countryViewModel.isNoDataMode().observe(this, status -> {
            PLog.WriteLog(PLog.MAIN_TAG, " isNoDataMode loading status: " + status);
            content.setRefreshing(false);
            if (status) {
                content.setVisibility(View.GONE);
                noDataScreen.setVisibility(View.VISIBLE);
            } else {
                content.setVisibility(View.VISIBLE);
                noDataScreen.setVisibility(View.GONE);
            }

        });

        countryViewModel.isNoDataRetryMode().observe(this, status -> {
            PLog.WriteLog(PLog.MAIN_TAG, " isNoDataRetryMode loading status: " + status);
            content.setRefreshing(false);
            if (status) {
                PLog.WriteLog(PLog.MAIN_TAG, " isNoDataRetryMode loading status 2: " + status);
                loadingScreen.setVisibility(View.VISIBLE);
                noDataScreen.setVisibility(View.GONE);
                content.setVisibility(View.GONE);
            } else {
                PLog.WriteLog(PLog.MAIN_TAG, "isNoDataRetryMode loading status 3: " + status);
                loadingScreen.setVisibility(View.GONE);
                noDataScreen.setVisibility(View.VISIBLE);
                content.setVisibility(View.VISIBLE);
            }
        });

        Button bt = parent.findViewById(R.id.country_detail_network_error).findViewById(R.id.reloadButton);
        bt.setOnClickListener(v -> {
            countryViewModel.reloadData(0);
        });

        content.setOnRefreshListener(() -> {
            countryViewModel.reloadData(1);
        });

        ////////////////////////////////////////////////////////////////////////////

        countryViewModel.getCountryDetail().observe(this, country -> {
            buildCountryDetail(country);
        });

        ImageView iv = parent.findViewById(R.id.fragment_country_detail_header_icon);
        iv.setOnClickListener(v -> {
            appViewModel.pressBack();
        });

        tfLight = ResourcesCompat.getFont(getContext(), R.font.opensans_bold);


        return parent;
    }

    private void buildCountryDetail(Country country) {
        if (country != null) {
            this.country = country;
            buildStat();
            buildBarCharts();
            buildOthers();
        }
    }

    private void buildStat() {
        Item item = country.getItemList().get(0);
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setGroupingUsed(true);

        PLog.WriteLog(PLog.MAIN_TAG, item.getTotalCases());

        TextView tv = parent.findViewById(R.id.fragment_country_detail_lastupdated);
        String date = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        tv.setText("Last updated: " + date);

        TextView tv1 = parent.findViewById(R.id.fragment_country_detail_stat_totalcases);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, (int) item.getTotalCases());
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator ani) {
                tv1.setText(formatter.format(ani.getAnimatedValue()));
            }
        });
        valueAnimator.start();

        TextView tv2 = parent.findViewById(R.id.fragment_country_detail_stat_newcases);
        tv2.setText(formatter.format(item.getNewCases()));

        TextView tv3 = parent.findViewById(R.id.fragment_country_detail_stat_totaldeaths);
        tv3.setText(formatter.format(item.getTotalDeaths()));

        TextView tv4 = parent.findViewById(R.id.fragment_country_detail_stat_newdeaths);
        tv4.setText(formatter.format(item.getNewDeaths()));

        TextView tv5 = parent.findViewById(R.id.fragment_country_detail_stat_totalrecovered);
        tv5.setText(formatter.format(item.getTotalRecovered()));

        ////////////////////////////////////////////////////////////
        TextView tv6 = parent.findViewById(R.id.fragment_country_detail_capitalname);
        tv6.setText(country.getCapitalName());

        TextView tv7 = parent.findViewById(R.id.fragment_country_detail_area);
        tv7.setText(AppUtils.formatNumber(country.getArea()) + " km2");

        TextView tv8 = parent.findViewById(R.id.fragment_country_detail_population);
        tv8.setText(AppUtils.formatNumber(country.getPopulation()));

        TextView tv9 = parent.findViewById(R.id.fragment_country_detail_header_title);
        tv9.setText(country.getName());

        ImageView iv = parent.findViewById(R.id.fragment_country_detail_flag);
        new BitmapWorker(iv, country.getFlagRes()).execute();

        ////////////////////////////////////////////////////////////
        TextView tv10 = parent.findViewById(R.id.fragment_country_detail_active_cases);
        tv10.setText(AppUtils.formatNumber(item.getTotalCases() - item.getTotalDeaths() - item.getTotalRecovered()));

        TextView tv11 = parent.findViewById(R.id.fragment_country_detail_serious_cases);
        tv11.setText(AppUtils.formatNumber(item.getSeriousCases()));

        TextView tv12 = parent.findViewById(R.id.fragment_country_detail_total_cases_per_1pop);
        tv12.setText(AppUtils.formatFloating(item.getTotalCasesPer1Pop()));

        TextView tv13 = parent.findViewById(R.id.fragment_country_detail_total_deaths_per_1pop);
        tv13.setText(AppUtils.formatFloating(item.getTotalDeathsPer1Pop()));

        TextView tv14 = parent.findViewById(R.id.fragment_country_detail_total_tests);
        tv14.setText(AppUtils.formatNumber(item.getTotalTests()));

        TextView tv15 = parent.findViewById(R.id.fragment_country_detail_tests_per_1pop);
        tv15.setText(AppUtils.formatFloating(item.getTestsPer1Pop()));

    }

    private void buildOthers() {
    }

    private void buildBarCharts() {
        BarChart chart3 = parent.findViewById(R.id.fragment_country_detail_recentcases_chart);
        buildBarChart(chart3, country.getTotalCasesRecent(), AppConfig.colors);

        BarChart chart4 = parent.findViewById(R.id.fragment_country_detail_recentdeaths_chart);
        buildBarChart(chart4, country.getTotalDeathsRecent(), AppConfig.colors);
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


        chart.invalidate();
    }



}
