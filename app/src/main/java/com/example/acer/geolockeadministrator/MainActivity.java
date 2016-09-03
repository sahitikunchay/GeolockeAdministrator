package com.example.acer.geolockeadministrator;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.geolockeadministrator.beans.ScanIBeacon;
import com.example.geolockeadministrator.services.BleScanService;

public class MainActivity extends AppCompatActivity {

    ListReceiver mListReceiver;
    ScanIBeacon sReceivedBeacon;
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE NOT SUPPORTED", Toast.LENGTH_SHORT).show();
        }

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "BLUETOOTH NOT SUPPORTED", Toast.LENGTH_SHORT).show();
        }
        startService(new Intent(this, BleScanService.class));
        IntentFilter filter = new IntentFilter();
        filter.addAction("BLE_SCAN_BROADCAST");
        mListReceiver = new ListReceiver();
        registerReceiver(mListReceiver,filter);
    }

    public class ListReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context pContext, Intent pIntent) {

            Log.i("hello", "received broadcast");
            sReceivedBeacon = pIntent.getExtras().getParcelable("BLE_SCAN");
            Log.i("hello", sReceivedBeacon.toString());
        }
    }
}
