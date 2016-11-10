package com.example.acer.geolockeadministrator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

public class LevelPlanWithConfiguredBeaconsActivity extends AppCompatActivity {


    int building;
    int level;
    double latitude;
    double longitude;
    int section;

    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_plan_with_configured_beacons);
        building = getIntent().getExtras().getInt("Building");
        level = getIntent().getExtras().getInt("Level");
        webview = (WebView) findViewById(R.id.webView);

        //WebView webview = new WebView(this);
        //setContentView(webview);

        //getWindow().requestFeature(Window.FEATURE_PROGRESS);

        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl("http://sahitistrial.esy.es/geolocke/sample.html");
        latitude = 30;
        longitude = 80;
        section = 8;
    }

    public void finalModification(View v){
        Intent mIntent = new Intent(LevelPlanWithConfiguredBeaconsActivity.this, ScanActivity.class);
        mIntent.putExtra("Building", building);
        mIntent.putExtra("Level", level);
        mIntent.putExtra("Section", section);
        mIntent.putExtra("Latitude", latitude);
        mIntent.putExtra("Longitude", longitude);
        startActivity(mIntent);

    }
}
