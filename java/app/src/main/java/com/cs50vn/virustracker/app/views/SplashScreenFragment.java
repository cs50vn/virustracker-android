package com.cs50vn.virustracker.app.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.tracking.PLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreenFragment extends Fragment {

    public SplashScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splashscreen, container, false);
        PLog.WriteLog(PLog.MAIN_TAG, "SplashScreenFragment.onCreateView");

        return view;
    }

}
