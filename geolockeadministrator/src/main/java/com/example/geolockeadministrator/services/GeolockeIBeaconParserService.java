package com.example.geolockeadministrator.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.geolockeadministrator.beans.GeolockeIBeacon;
import com.example.geolockeadministrator.beans.GeolockeIBeaconScan;
import com.example.geolockeadministrator.beans.IBeacon;
import com.example.geolockeadministrator.beans.ScanIBeacon;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ACER on 9/7/2016.
 */
public class GeolockeIBeaconParserService extends Service{
    ScanReceiver mScanReceiver;
    GeolockeIBeaconScan mGeolockeIBeaconScan;
    ArrayList<GeolockeIBeacon> mGeolockeIBeaconList;
    ArrayList<Integer> mRssiList;

    private IBinder mBinder = new GeolockeIBeaconParserServiceBinder();



    public class GeolockeIBeaconParserServiceBinder extends Binder {
        public GeolockeIBeaconParserService getService() {
            return GeolockeIBeaconParserService.this;
        }
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v("", "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v("", "in onUnbind");
        return true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent pIntent) {
        Log.v("BLEBIND", "in onBind");
        return mBinder;
    }



    @Override
    public int onStartCommand(Intent pIntent, int pFlags, int pStartId) {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("IBEACON_SCAN_BROADCAST");
        mScanReceiver = new ScanReceiver();
        registerReceiver(mScanReceiver, intentFilter);


        return START_STICKY;
    }



    public class ScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context pContext, Intent pIntent) {

            ScanIBeacon iBeaconScan = pIntent.getExtras().getParcelable("IBEACON_SCAN");
            ArrayList<IBeacon> iBeaconList = new ArrayList<IBeacon>();
            iBeaconList = iBeaconScan.getIBeaconArrayList();
            ArrayList<Integer> rssiList = new ArrayList<Integer>();
            rssiList = iBeaconScan.getRssiList();

            mGeolockeIBeaconList = new ArrayList<GeolockeIBeacon>();
            mRssiList = new ArrayList<Integer>();

            for(int i=0; i<iBeaconList.size(); i++){
                IBeacon iBeacon = iBeaconList.get(i);
                int rssi = rssiList.get(i);

                String uuid = iBeacon.getUuid();
                String[] split = uuid.split("-");

                String lat = split[0];
                Long l = Long.parseLong(lat, 16);
                Float f = Float.intBitsToFloat(l.intValue());
                Double latitude = Double.valueOf(f);

                //last 8 digits are fixed as 0000;
                String lng = split[1]+split[2];
                Long j = Long.parseLong(lng, 16);
                Float k = Float.intBitsToFloat(j.intValue());
                Double longitude = Double.valueOf(k);

                // TODO: 31-07-2016 store buildingId and floorId somewhere
                String buildingIdAndFloorId = split[3];
                int buildingId = (int ) Long.parseLong(buildingIdAndFloorId.substring(0, 2), 16);
                int levelId = (int) Long.parseLong(buildingIdAndFloorId.substring(2, 4), 16);
                /*String floorId = split[4].substring(0, 4);
                int levelId = Integer.parseInt(floorId);*/
                int geofenceid = 0; //TODO: change

                //GeolockeIBeacon geolockeIBeacon = new GeolockeIBeacon(iBeacon.getMacAddress(),iBeacon.getUuid(),iBeacon.getName(),iBeacon.getMajor(),iBeacon.getMinor(),latitude,longitude);
                GeolockeIBeacon geolockeIBeacon = new GeolockeIBeacon(iBeacon.getMacAddress(),iBeacon.getUuid(), latitude, longitude,1 ,buildingId, levelId, geofenceid);
                mGeolockeIBeaconList.add(geolockeIBeacon);
                mRssiList.add(rssi);
            }

            mGeolockeIBeaconScan = new GeolockeIBeaconScan(mGeolockeIBeaconList,mRssiList);

            Intent intent = new Intent();
            intent.putExtra("GEOLOCKE_IBEACON_SCAN",mGeolockeIBeaconScan);
            intent.setAction("GEOLOCKE_IBEACON_SCAN_BROADCAST");
            sendBroadcast(intent);
            Log.i("broadcast sent", "from parseservice");

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScanReceiver);
        stopSelf();
    }
}
