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
public class HomeFragment extends Fragment {

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

        return view;
    }

}
