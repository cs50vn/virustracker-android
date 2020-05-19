package com.cs50vn.virustracker.app.uicomponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.appmodel.CountryViewModel;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.utils.CountrySortEnum;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FilterBottomSheet extends BottomSheetDialogFragment {

    CountryViewModel countryViewModel;
    View parent;
    FilterBottomSheet a;

    public FilterBottomSheet() {
        a = this;
    }

    private void createGroup(LayoutInflater inflater) {
        RadioButton bt1 = (RadioButton) inflater.inflate(R.layout.component_radiobutton, null);
        bt1.setText(R.string.totalcases_sort_title);
        bt1.setTag(CountrySortEnum.TOTAL_CASES);


        RadioButton bt2 = (RadioButton) inflater.inflate(R.layout.component_radiobutton, null);
        bt2.setText(R.string.newcases_sort_title);
        bt2.setTag(CountrySortEnum.NEW_CASES);


        RadioButton bt3 = (RadioButton) inflater.inflate(R.layout.component_radiobutton, null);
        bt3.setText(R.string.totaldeaths_sort_title);
        bt3.setTag(CountrySortEnum.TOTAL_DEATHS);

        RadioGroup radio = parent.findViewById(R.id.fragment_country_radio);
        radio.addView(bt1);
        radio.addView(bt2);
        radio.addView(bt3);

        radio.setOnCheckedChangeListener((group, checkedId) -> {
            if (group.getCheckedRadioButtonId() != -1) {
                RadioButton rb = group.findViewById(checkedId);
                CountrySortEnum tag = (CountrySortEnum) rb.getTag();
                PLog.WriteLog(PLog.MAIN_TAG, "tag: " + tag);
                countryViewModel.sortCountries(tag);
            }

        });

        countryViewModel.getCountrySortEnum().observe(this, countrySortEnum -> {
            PLog.WriteLog(PLog.MAIN_TAG, "countrySortEnum: " + countrySortEnum);
            if (countrySortEnum == CountrySortEnum.TOTAL_CASES) {
                ((RadioButton)radio.getChildAt(0)).setChecked(true);
            } else if (countrySortEnum == CountrySortEnum.NEW_CASES) {
                ((RadioButton)radio.getChildAt(1)).setChecked(true);
            } else if (countrySortEnum == CountrySortEnum.TOTAL_DEATHS) {
                ((RadioButton)radio.getChildAt(2)).setChecked(true);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        countryViewModel = ViewModelProviders.of(this).get(CountryViewModel.class);

        parent = inflater.inflate(R.layout.fragment_country_bottom, container, false);

        createGroup(inflater);






        return parent;
    }
}
