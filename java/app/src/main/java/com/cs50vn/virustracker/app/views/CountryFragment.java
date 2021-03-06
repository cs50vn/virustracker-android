package com.cs50vn.virustracker.app.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.TooltipCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.appmodel.AppViewModel;
import com.cs50vn.virustracker.app.appmodel.CountryViewModel;
import com.cs50vn.virustracker.app.controller.CountryAdapter;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.uicomponent.FilterBottomSheet;
import com.cs50vn.virustracker.app.utils.CountryModeEnum;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryFragment extends Fragment {

    private AppViewModel appViewModel;
    private CountryViewModel countryViewModel;
    private View parent;

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            EditText input = parent.findViewById(R.id.fragment_country_edittext);
            countryViewModel.setCountryMode(CountryModeEnum.STANDARD);
            input.setCursorVisible(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);
        appViewModel = ViewModelProviders.of(this.getActivity()).get(AppViewModel.class);

        // Inflate the layout for this fragment
        parent = inflater.inflate(R.layout.fragment_country, container, false);

        ////////////////////////////////////////////////////////////////////////////
        //Handle network issue
        View content = parent.findViewById(R.id.fragment_country_content);
        View homeLoading = parent.findViewById(R.id.country_loading);
        View homeNodata = parent.findViewById(R.id.country_network_error);

        appViewModel.isNoDataMode().observe(this, status -> {
            if (status) {
                content.setVisibility(View.GONE);
                homeNodata.setVisibility(View.VISIBLE);
            } else {
                content.setVisibility(View.VISIBLE);
                homeNodata.setVisibility(View.GONE);
            }

        });

        appViewModel.isNoDataRetryMode().observe(this, status -> {
            if (status) {
                homeLoading.setVisibility(View.VISIBLE);
                homeNodata.setVisibility(View.GONE);
            } else {
                homeLoading.setVisibility(View.GONE);
                homeNodata.setVisibility(View.VISIBLE);
            }
        });

        Button bt = parent.findViewById(R.id.country_network_error).findViewById(R.id.reloadButton);
        bt.setOnClickListener(v -> {
            appViewModel.reloadData(0);
        });


        ////////////////////////////////////////////////////////////////////////////

        EditText input = parent.findViewById(R.id.fragment_country_edittext);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PLog.WriteLog(PLog.MAIN_TAG, "textchanged: " + s);
                countryViewModel.search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input.setOnTouchListener((v, event) -> {
            PLog.WriteLog(PLog.MAIN_TAG, "Click on input ===");
            countryViewModel.setCountryMode(CountryModeEnum.SEARCH);
            input.setCursorVisible(true);
            return false;
        });

        input.setOnEditorActionListener((v, keyCode, event) -> {
            PLog.WriteLog(PLog.MAIN_TAG, "keycode: " + keyCode);
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                //Perform Code
                hideSoftKeyboard(this.getActivity());

                return true;
            }

            return false;
        });

        input.setOnFocusChangeListener((v, hasFocus) -> {
            //PLog.WriteLog(PLog.MAIN_TAG, "Input focus: " + hasFocus);
//            if (hasFocus)
//                countryViewModel.setCountryMode(CountryModeEnum.SEARCH);
        });


        Button cancel = parent.findViewById(R.id.fragment_country_cancel_button);
        cancel.setOnClickListener(v -> {
            countryViewModel.setCountryMode(CountryModeEnum.STANDARD);
            input.setCursorVisible(false);

        });

        Button filter = parent.findViewById(R.id.fragment_country_filter_button);
        FragmentManager manager = getChildFragmentManager();

        filter.setOnClickListener(v -> {
            input.setCursorVisible(false);
            TooltipCompat.setTooltipText(filter, this.getContext().getString(R.string.active_cases));
            FilterBottomSheet bs = new FilterBottomSheet();
            //bs.show(manager, "ds");


        });

        RecyclerView rv = parent.findViewById(R.id.fragment_country_recyclerview);
        rv.setOnClickListener(v -> {
            PLog.WriteLog(PLog.MAIN_TAG, "recyclerview clicked");
        });

        rv.setOnTouchListener((v, event) -> {
            PLog.WriteLog(PLog.MAIN_TAG, "recyclerview moved");
            hideSoftKeyboard(this.getActivity());
            input.setCursorVisible(false);

            return false;
        });

        countryViewModel.getCountryMode().observe(this, countryMode -> {
            if (countryMode == CountryModeEnum.STANDARD) {
                View title = parent.findViewById(R.id.fragment_country_title);
                title.setVisibility(View.VISIBLE);

                View button = parent.findViewById(R.id.fragment_country_filter_button);
                button.setVisibility(View.VISIBLE);

                View button1 = parent.findViewById(R.id.fragment_country_cancel_button);
                button1.setVisibility(View.GONE);

                hideSoftKeyboard(this.getActivity());
            } else if (countryMode == CountryModeEnum.SEARCH) {
                View title = parent.findViewById(R.id.fragment_country_title);
                title.setVisibility(View.GONE);

                View button = parent.findViewById(R.id.fragment_country_filter_button);
                button.setVisibility(View.GONE);

                View button1 = parent.findViewById(R.id.fragment_country_cancel_button);
                button1.setVisibility(View.VISIBLE);
            }

        });

        countryViewModel.getSearchCountryList().observe(this, countries -> {
            PLog.WriteLog(PLog.MAIN_TAG, "getSearchCountryList founded: " + countries.size());
            LinearLayoutManager layout = new LinearLayoutManager(this.getContext());
            CountryAdapter adapter = new CountryAdapter(this, countries);
            rv.setLayoutManager(layout);
            rv.setAdapter(adapter);

        });



        return parent;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
