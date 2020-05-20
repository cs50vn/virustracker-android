package com.cs50vn.virustracker.app;

import android.os.Bundle;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.cs50vn.virustracker.app.appmodel.AppViewModel;
import com.cs50vn.virustracker.app.tracking.PLog;
import com.cs50vn.virustracker.app.views.MainFragment;
import com.cs50vn.virustracker.app.views.SplashScreenFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainAct extends AppCompatActivity {
    private AppViewModel appViewModel;

    private void initApp() {
        PLog.WriteLog(PLog.MAIN_TAG, "initApp");

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        appViewModel.initApp(this);
        appViewModel.isSplashScreenMode().observe(this, loadingMode -> {
            if (loadingMode)
                loadFragment(new SplashScreenFragment());
            else
                startApp();

        });

        appViewModel.exitApp().observe(this, o -> {
            System.exit(0);
        });
    }

    private void startApp() {
        loadFragment(new MainFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize app data
        initApp();

        setContentView(R.layout.activity_main);

        PLog.WriteLog(PLog.MAIN_TAG, "onCreated");
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.main_component, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }

    @Override
    protected void onStart() {
        PLog.WriteLog(PLog.MAIN_TAG, "MainAct.onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        PLog.WriteLog(PLog.MAIN_TAG, "MainAct.onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        PLog.WriteLog(PLog.MAIN_TAG, "MainAct.onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        PLog.WriteLog(PLog.MAIN_TAG, "MainAct.onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        PLog.WriteLog(PLog.MAIN_TAG, "MainAct.onDestroy");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        PLog.WriteLog(PLog.MAIN_TAG, "MainAct.onBackPressed");
        appViewModel.pressBack();
    }
}
