package com.example.acer.geolockeadministrator;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScanActivity extends AppCompatActivity {
    ListView mListView;
    ArrayList<BeaconDetails> mBluetoothDeviceArrayList;
    ArrayAdapter<BeaconDetails> mBluetoothDeviceArrayAdapter;
    BluetoothAdapter mBluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mListView = (ListView)findViewById(R.id.listView);
        mBluetoothDeviceArrayList = new ArrayList<BeaconDetails>();
        mBluetoothDeviceArrayAdapter = new ArrayAdapter<BeaconDetails>(ScanActivity.this ,android.R.layout.simple_list_item_1, mBluetoothDeviceArrayList);
        mListView.setAdapter(mBluetoothDeviceArrayAdapter);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        final int building = getIntent().getExtras().getInt("Building");
        final int level = getIntent().getExtras().getInt("Level");
        final int section = getIntent().getExtras().getInt("Section");
        final double latitude = getIntent().getExtras().getDouble("Latitude");
        final double longitude = getIntent().getExtras().getDouble("Longitude");

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

        startBleScan();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                BeaconDetails beaconToBeSent = mBluetoothDeviceArrayList.get(position);
                Intent mEditIntent = new Intent(ScanActivity.this, ModificationActivity.class);
                mEditIntent.putExtra("Bluetooth Device", beaconToBeSent.getBluetoothDevice());
                mEditIntent.putExtra("UUID", beaconToBeSent.getUUID());
                mEditIntent.putExtra("Mac Address", beaconToBeSent.getMacAddress());
                mEditIntent.putExtra("Building", building);
                mEditIntent.putExtra("Level", level);
                mEditIntent.putExtra("Section", section);
                mEditIntent.putExtra("Latitude", latitude);
                mEditIntent.putExtra("Longitude", longitude);
                startActivity(mEditIntent);
            }
        });
    }

    public void startBleScan(){
        final BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
                // Toast.makeText(getApplicationContext(), "scanning", Toast.LENGTH_LONG).show();
                int startByte = 2;
                boolean patternFound = false;
                while (startByte <= 5) {
                    if (    ((int) scanRecord[startByte + 2] & 0xff) == 0x02 && //Identifies an IBeacon
                            ((int) scanRecord[startByte + 3] & 0xff) == 0x15) { //Identifies correct data length
                        patternFound = true;
                        break;
                    }
                    startByte++;
                }

                if (patternFound) {
                    //Convert to hex String
                    byte[] uuidBytes = new byte[16];
                    System.arraycopy(scanRecord, startByte+4, uuidBytes, 0, 16);
                    String hexString = bytesToHex(uuidBytes);

                    //Here is your UUID
                    String uuid =  hexString.substring(0,8) + "-" +
                            hexString.substring(8,12) + "-" +
                            hexString.substring(12,16) + "-" +
                            hexString.substring(16,20) + "-" +
                            hexString.substring(20,32);

                    //Here is your Major value
                    int major = (scanRecord[startByte+20] & 0xff) * 0x100 + (scanRecord[startByte+21] & 0xff);

                    //Here is your Minor value
                    int minor = (scanRecord[startByte+22] & 0xff) * 0x100 + (scanRecord[startByte+23] & 0xff);

                    //txpower value
                    byte txPower = scanRecord[startByte+24];

                    BeaconDetails mBeaconDetails = new BeaconDetails(device, uuid, device.getAddress(), device.getName(),major, minor);
                    if(!mBluetoothDeviceArrayList.contains(mBeaconDetails)){
                        mBluetoothDeviceArrayList.add(mBeaconDetails);
                        mBluetoothDeviceArrayAdapter.notifyDataSetChanged();
                    }


                }

            }
        };

        mBluetoothAdapter.startLeScan(leScanCallback);
    }

    public static String bytesToHex(byte[] pBytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[pBytes.length * 2];
        for ( int j = 0; j < pBytes.length; j++ ) {
            int v = pBytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
