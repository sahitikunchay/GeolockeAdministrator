package com.example.acer.geolockeadministrator;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.geolockeadministrator.ContentProvider.GeolockeIBeaconsDbHelper;
import com.example.geolockeadministrator.ContentProvider.GeolockeIBeaconsContract;

public class ContentProviderDemo extends AppCompatActivity {

    ListView mListView;
    CursorAdapter mCursorAdapter;
    String[] mProjection;
    String mSelectionClause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_demo);
        mListView = (ListView) findViewById(R.id.listView2);
        mSelectionClause = null;
        loadGeolockeIBeacons();
    }
    public void loadGeolockeIBeacons(){
        String[] geolockeIBeaconsListColumns = {
                GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_ID,
                GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_MAC_ADDRESS,
                GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_UUID,
                GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_LATITUDE,
                GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_LONGITUDE,
                GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_ORGANIZATION_ID,
                GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_BUILDING_ID,
                GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_LEVEL_ID,
                GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_SECTION_ID
        };

        mProjection = geolockeIBeaconsListColumns;
        Cursor cursor = getContentResolver().query(GeolockeIBeaconsContract.CONTENT_URI,mProjection,mSelectionClause,null,null);

        int[] geolockeIBeaconsListItems = {R.id.idView, R.id.macAddressView, R.id.uuid, R.id.latitudeView, R.id.longitudeView, R.id.organizationIdView, R.id.buildingIdView, R.id.levelIdView, R.id.sectionIdView};

        mCursorAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.geolocke_ibeacons_list_item,cursor,geolockeIBeaconsListColumns,geolockeIBeaconsListItems,0);
        mListView.setAdapter(mCursorAdapter);
    }
}
