package com.example.geolockeadministrator;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.geolockeadministrator.beans.GeolockeIBeacon;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ModifyCharacteristics {
    private BluetoothDevice mBluetoothDevice;
    private GeolockeIBeacon mGeolockeIBeacon;
    private BluetoothGatt mGatt;
    private BluetoothGattCallback mBluetoothGattCallback;
    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;
    private boolean mAuth = false;
    private boolean mJobDone = false;
    private boolean mResetDone = false;
    private static final int CHANGE_MINOR = 1;
    private static final int CHANGE_MAJOR = 2;
    private static final int CHANGE_UUID = 3;

    public ModifyCharacteristics(Context pContext, GeolockeIBeacon pGeolockeIBeacon, BluetoothDevice pBluetoothDevice) {
        mGeolockeIBeacon = pGeolockeIBeacon;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(pGeolockeIBeacon.getMacAddress());
        mBluetoothDevice = pBluetoothDevice;
        mContext = pContext;
        if (mGatt==null){
            mGatt = mBluetoothDevice.connectGatt(mContext, false, mBluetoothGattCallback);
            Log.i("Gatt", "changeit - connectgatt");
        }
    }

    /*
    Encoding Scheme Explained:
    first 8 bits - latitude (lat encoded in hex)
    next 8 bits - longitude (lng encoded in hex)
    next 4 bits - building id (non hex values, so if you do Ineger.parseInt of the 4 digit value, u get building id)
    next 4 bits - level id (non hex values, so if you do Ineger.parseInt of the 4 digit value, u get level id)
    next 8 bits - 00000000
     */

    public String changeLatitude(Double pLatitude){
        //put new lat n change uuid by calling changeit
        Double sDoubleLatitude = pLatitude;
        DecimalFormat df = new DecimalFormat("##.####");
        String sStringLatitude = df.format(sDoubleLatitude);
        Double sLatitude = Double.valueOf(sStringLatitude);
        Float floatLatitude = sLatitude.floatValue();
        String latitudeOfUuid = Integer.toHexString(Float.floatToIntBits(floatLatitude));

        String sFormerUuid = mGeolockeIBeacon.getUuid();
        String split[]= sFormerUuid.split("-");

        String uuidBeforeChecksum = latitudeOfUuid+"-"+split[1]+"-"+split[2]+"-"+split[3]+"-"+split[4];
        String changedUuid = UuidChecksum(uuidBeforeChecksum);
        changeProperties(mBluetoothDevice, 3, changedUuid);
        return changedUuid;
    }

    public String changeLongitude(Double pLongitude){
        //put new lng n change uuid by calling changeit
        Double sDoubleLongitude = pLongitude;
        DecimalFormat df = new DecimalFormat("##.####");
        String sStringLongitude = df.format(sDoubleLongitude);
        Double sLongitude = Double.valueOf(sStringLongitude);
        Float floatLongitude = sLongitude.floatValue();
        String longitudeOfUuid = Integer.toHexString(Float.floatToIntBits(floatLongitude));

        String sFormerUuid = mGeolockeIBeacon.getUuid();
        String split[]= sFormerUuid.split("-");

        String uuidBeforeChecksum = split[0]+"-"+longitudeOfUuid.substring(0,4)+"-"+longitudeOfUuid.substring(4,8)+"-"+split[3]+"-"+split[4];
        String changedUuid = UuidChecksum(uuidBeforeChecksum);
        changeProperties(mBluetoothDevice, CHANGE_UUID, changedUuid);
        return changedUuid;
    }

    public String changeBuildingID(int pBuildingId){
        //put new level n change level by calling changeit
        int sIntegerBuildingId = pBuildingId;
        String sStringBuildingId = Integer.toHexString(0x100 | sIntegerBuildingId).substring(1);

        String sFormerUuid = mGeolockeIBeacon.getUuid();
        String split[] = sFormerUuid.split("-");

        String uuidBeforeChecksum = split[0]+"-"+split[1]+"-"+split[2]+"-"+sStringBuildingId+split[3].substring(2,4)+"-"+split[4];
        String changedUuid = UuidChecksum(uuidBeforeChecksum);
        changeProperties(mBluetoothDevice, CHANGE_UUID, changedUuid);
        return changedUuid;

    }

    public String changeLevelID(int pLevelId){
        //put new level n change level by calling changeit
        int sIntegerLevelId = pLevelId;
        String sStringLevelId = Integer.toHexString(0x100 | sIntegerLevelId).substring(1);

        String sFormerUuid = mGeolockeIBeacon.getUuid();
        String split[] = sFormerUuid.split("-");

        String uuidBeforeChecksum = split[0]+"-"+split[1]+"-"+split[2]+"-"+split[3].substring(0,2)+sStringLevelId+"-"+split[4];
        String changedUuid = UuidChecksum(uuidBeforeChecksum);
        changeProperties(mBluetoothDevice, CHANGE_UUID, changedUuid);
        return changedUuid;

    }

    public String changeSectionId(int pSectionId){

        int sIntegerSectionId = pSectionId;
        String sStringSectionId = Integer.toHexString(0x100 | sIntegerSectionId).substring(1);

        String sFormerUuid = mGeolockeIBeacon.getUuid();
        String split[] = sFormerUuid.split("-");

        String uuidBeforeChecksum = split[0]+"-"+split[1]+"-"+split[2]+"-"+split[3]+"-"+sStringSectionId+split[4].substring(2, 12);
        String changedUuid = UuidChecksum(uuidBeforeChecksum);
        changeProperties(mBluetoothDevice, CHANGE_UUID, changedUuid);
        return changedUuid;
    }

    public String changeOrganizationId(int pOrganizationId){
        int sIntegerOrganizationId = pOrganizationId;
        String sStringOrganizationId = Integer.toHexString(0x1000000 | sIntegerOrganizationId).substring(1);

        String sFormerUuid = mGeolockeIBeacon.getUuid();
        String split[] = sFormerUuid.split("-");

        String  uuidBeforeChecksum = split[0]+"-"+split[1]+"-"+split[2]+"-"+split[3]+"-"+split[4].substring(0,2)+sStringOrganizationId+split[4].substring(8,12);
        String changedUuid = UuidChecksum(uuidBeforeChecksum);
        changeProperties(mBluetoothDevice, CHANGE_UUID, changedUuid);
        return changedUuid;
    }

    public String UuidChecksum(String pUuid){
        String sUuid = pUuid;
        String uuidToBeChecked = sUuid.substring(0,32);

        byte[] bytesOfMessage;
        try {
            bytesOfMessage = uuidToBeChecked.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < thedigest.length; i++) {
                sb.append(Integer.toString((thedigest[i] & 0xff) + 0x100, 16).substring(1));
            }
            //sb.toString
            String checksumCharactersToBeAddedToUuid = sb.toString().substring(Math.max(0, sb.toString().length() - 4));
            String finalUuid = sUuid.substring(0,32)+checksumCharactersToBeAddedToUuid;
            return  finalUuid;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //this is incase the above fails - CHANGE LATER
        return  pUuid;
    }

    public void changeProperties(BluetoothDevice pBluetoothDevice, final int pChangeID, final String changedTo){
        //pass device, change id as follows: 1 = change minor, 2 = change major, 3 = change uuid
        mBluetoothGattCallback = new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                Log.i("onConnectionStateChange", "Status: " + status);
                switch (newState) {
                    case BluetoothProfile.STATE_CONNECTED:
                        Log.i("gattCallback", "STATE_CONNECTED");
                        gatt.discoverServices();
                        break;
                    case BluetoothProfile.STATE_DISCONNECTED:
                        Log.e("gattCallback", "STATE_DISCONNECTED");
                        break;
                    default:
                        Log.e("gattCallback", "STATE_OTHER");
                }

            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                List<BluetoothGattService> services = gatt.getServices();
                Log.i("onServicesDiscovered", services.toString());
                mAuth = false;
                mJobDone = false;
                writeTothechar(gatt);
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                Log.i("onCharacteristicWrite", "? = "+ characteristic.toString());

                if(mAuth){
                    Log.i("onMinorWriteAttempt", "auth = "+mAuth +"job = "+mJobDone);
                    mAuth = false;
                    mJobDone = true;
                    writeTothechar(gatt);
                }
                else
                if(mJobDone==false){
                    Log.i("onPassWriteAttempt", "auth = "+mAuth +"job = "+mJobDone);
                    BluetoothGattService passwordService = gatt.getService(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"));
                    mAuth = true;
                    this.attemptWritePassword(passwordService, gatt, "AT+AUTHAprilBrother");

                }
                else
                if(!mResetDone){
                    mResetDone = true;
                    Log.i("onPassWriteAttempt", "auth = "+mAuth +"job = "+mJobDone);
                    BluetoothGattService passwordService = gatt.getService(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"));
                    mAuth = true;
                    this.attemptWritePassword(passwordService, gatt, "AT+RESET");
                }

            }

            public void writeTothechar(BluetoothGatt gatt){
                Log.i("Gatt", "writetochar");
                if(pChangeID==1) {
                    int pMinor = Integer.parseInt(changedTo);
                    final byte[] value = {(byte) ((byte) (pMinor >> 8) & 0xFF), (byte) ((byte) pMinor & 0xFF)};
                    final Iterator<BluetoothGattService> iterator = gatt.getServices().iterator();
                    while (iterator.hasNext()) {
                        final BluetoothGattService bluetoothGattService;
                        final BluetoothGattCharacteristic characteristic;
                        if ((bluetoothGattService = iterator.next()).getUuid().equals(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")) && (characteristic = bluetoothGattService.getCharacteristic(UUID.fromString("0000fff3-0000-1000-8000-00805f9b34fb"))).setValue(value)) {
                            gatt.writeCharacteristic(characteristic);
                        }
                    }
                } else if(pChangeID==2) {
                    int pMajor = Integer.parseInt(changedTo);
                    final byte[] value = {(byte) ((byte) (pMajor >> 8) & 0xFF), (byte) ((byte) pMajor & 0xFF)};
                    final Iterator<BluetoothGattService> iterator = gatt.getServices().iterator();
                    while (iterator.hasNext()) {
                        final BluetoothGattService bluetoothGattService;
                        final BluetoothGattCharacteristic characteristic;
                        if ((bluetoothGattService = iterator.next()).getUuid().equals(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")) && (characteristic = bluetoothGattService.getCharacteristic(UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb"))).setValue(value)) {
                            gatt.writeCharacteristic(characteristic);
                        }
                    }
                } else if(pChangeID==3) {
                    String UUIDChangedTo = changedTo;
                    final byte[] value = uuidchanger(changedTo);
                    final Iterator<BluetoothGattService> iterator = gatt.getServices().iterator();
                    while (iterator.hasNext()) {
                        final BluetoothGattService bluetoothGattService;
                        final BluetoothGattCharacteristic characteristic;
                        if ((bluetoothGattService = iterator.next()).getUuid().equals(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")) && (characteristic = bluetoothGattService.getCharacteristic(UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb"))).setValue(value)) {
                            gatt.writeCharacteristic(characteristic);
                        }
                    }
                }

            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt,
                                             BluetoothGattCharacteristic
                                                     characteristic, int status) {
                Log.i("onCharacteristicRead", "V = "+ characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT8,0));

            }


            void attemptWritePassword(BluetoothGattService bluetoothGattService, BluetoothGatt bluetoothGatt, String pPassword) {
                final BluetoothGattCharacteristic characteristic;
                if (bluetoothGattService.getUuid().equals(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")) && (characteristic = bluetoothGattService.getCharacteristic(UUID.fromString("0000fff8-0000-1000-8000-00805f9b34fb"))) != null) {
                    final byte[] bytes = pPassword.getBytes();
                    characteristic.setWriteType(2);
                    if (characteristic.setValue(bytes)) {
                        if (bluetoothGatt.writeCharacteristic(characteristic)) {
                            Log.i("onPasswordWrite", " Done");

                        }
                        else {
                            Log.i("onPasswordWrite", " NOT Done");

                        }
                    }
                }
            }

        };

        pBluetoothDevice = mBluetoothDevice;
        if (mGatt==null){
            mGatt = pBluetoothDevice.connectGatt(mContext, false, mBluetoothGattCallback);
            Log.i("Gatt", "changeProperties - connectgatt");
        }
    }

    public static byte[] uuidchanger(final String s) {
        if (s == null) {
            throw new NullPointerException("uuid is null");
        }
        final UUID fromString;
        final long leastSignificantBits = (fromString = UUID.fromString(s)).getLeastSignificantBits();
        final long mostSignificantBits = fromString.getMostSignificantBits();
        final byte[] array = new byte[16];
        for (int i = 0, n = 8; i < 8; ++i, ++n) {
            array[i] = (byte)(mostSignificantBits >>> (7 - i << 3) & 0xFFL);
            array[n] = (byte)(leastSignificantBits >>> (7 - i << 3) & 0xFFL);
        }
        return array;
    }


}
