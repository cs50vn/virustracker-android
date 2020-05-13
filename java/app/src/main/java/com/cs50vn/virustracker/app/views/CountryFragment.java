package com.cs50vn.virustracker.app.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.uicomponent.FilterBottomSheet;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryFragment extends Fragment {

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        Button bt = view.findViewById(R.id.fragment_country_filter_button);
        FragmentManager manager = getChildFragmentManager();

        bt.setOnClickListener(v -> {
            FilterBottomSheet bs = new FilterBottomSheet();
            bs.show(manager, "ds");

        });

        return view;
    }

}
