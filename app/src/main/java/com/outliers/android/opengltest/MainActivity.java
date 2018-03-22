package com.outliers.android.opengltest;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    MyGLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        Log.e("onCreate",configurationInfo.getGlEsVersion()+","+configurationInfo.reqGlEsVersion);
        surfaceView = new MyGLSurfaceView(this);
        setContentView(surfaceView);
    }

    @Override
    public void onResume(){
        super.onResume();
        surfaceView.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        surfaceView.onPause();
    }
}
