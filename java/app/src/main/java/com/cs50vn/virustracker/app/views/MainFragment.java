package com.cs50vn.virustracker.app.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.cs50vn.virustracker.app.R;
import com.cs50vn.virustracker.app.appmodel.AppViewModel;
import com.cs50vn.virustracker.app.tracking.PLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private HomeFragment homeFragment;
    private CountryFragment countryFragment;
    private AboutFragment aboutFragment;

    private AppViewModel appViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(homeFragment);
                    return true;
                case R.id.navigation_country:
                    switchFragment(countryFragment);
                    return true;
                case R.id.navigation_about:
                    switchFragment(aboutFragment);
                    return true;
            }
            return false;
        }
    };

    private BottomNavigationView.OnNavigationItemReselectedListener mOnNavigationItemReSelectedListener
            = new BottomNavigationView.OnNavigationItemReselectedListener() {

        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    PLog.WriteLog(PLog.MAIN_TAG, "Reselected: Home");
                    //Go to start page
                    break;
                case R.id.navigation_country:
                    PLog.WriteLog(PLog.MAIN_TAG, "Reselected: Country");
                    break;
                case R.id.navigation_about:
                    PLog.WriteLog(PLog.MAIN_TAG, "Reselected: About");
                    break;
            }
        }
    };

    public MainFragment() {
        // Required empty public constructor
        homeFragment = new HomeFragment();
        countryFragment = new CountryFragment();
        aboutFragment = new AboutFragment();

    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction trans = getChildFragmentManager().beginTransaction();
        String className = fragment.getClass().getSimpleName();
        //PLog.WriteLog(PLog.MAIN_TAG, "className: " + className);
        //PLog.WriteLog(PLog.MAIN_TAG, "className: " + fragment.getClass().getCanonicalName());
        //PLog.WriteLog(PLog.MAIN_TAG, "className: " + fragment.getClass().getSimpleName());

        if (className.equals("HomeFragment")) {

            trans.show(homeFragment);
            trans.hide(countryFragment);
            trans.hide(aboutFragment);
        } else if (className.equals("CountryFragment")) {
            trans.hide(homeFragment);
            trans.show(countryFragment);
            trans.hide(aboutFragment);
        } if (className.equals("AboutFragment")) {
            trans.hide(homeFragment);
            trans.hide(countryFragment);
            trans.show(aboutFragment);
        }

        trans.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        appViewModel = ViewModelProviders.of(this.getActivity()).get(AppViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navigation = view.findViewById(R.id.app_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setOnNavigationItemReselectedListener(mOnNavigationItemReSelectedListener);

        //Add to fragment list
        FragmentTransaction trans = getChildFragmentManager().beginTransaction();
        trans.add(R.id.app_content, homeFragment);
        trans.add(R.id.app_content, countryFragment);
        trans.add(R.id.app_content, aboutFragment);
        trans.commit();

        //Init app fragment
        switchFragment(homeFragment);
    }

}
