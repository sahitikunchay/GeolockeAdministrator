package com.example.acer.geolockeadministrator;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;

/**
 * Created by ACER on 8/11/2016.
 */
public class BeaconDetails {

    private BluetoothDevice mBluetoothDevice;
    private String mUUID;
    private String mMacAddress;
    private String mName;
    private int mMajor;
    private int mMinor;

    @Override
    public String toString() {
        return "BeaconDetails{" +
                "mBluetoothDevice=" + mBluetoothDevice +
                ", mUUID='" + mUUID + '\'' +
                ", mMacAddress='" + mMacAddress + '\'' +
                ", mName='" + mName + '\'' +
                ", mMajor=" + mMajor +
                ", mMinor=" + mMinor +
                '}';
    }

    @Override
    public boolean equals(Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;

        BeaconDetails that = (BeaconDetails) pO;

        if (mMajor != that.mMajor) return false;
        if (mMinor != that.mMinor) return false;
        if (mBluetoothDevice != null ? !mBluetoothDevice.equals(that.mBluetoothDevice) : that.mBluetoothDevice != null)
            return false;
        if (mUUID != null ? !mUUID.equals(that.mUUID) : that.mUUID != null) return false;
        if (mMacAddress != null ? !mMacAddress.equals(that.mMacAddress) : that.mMacAddress != null)
            return false;
        return mName != null ? mName.equals(that.mName) : that.mName == null;

    }

    @Override
    public int hashCode() {
        int result = mBluetoothDevice != null ? mBluetoothDevice.hashCode() : 0;
        result = 31 * result + (mUUID != null ? mUUID.hashCode() : 0);
        result = 31 * result + (mMacAddress != null ? mMacAddress.hashCode() : 0);
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + mMajor;
        result = 31 * result + mMinor;
        return result;
    }

    public BluetoothDevice getBluetoothDevice() {

        return mBluetoothDevice;
    }

    public String getUUID() {
        return mUUID;
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    public String getName() {
        return mName;
    }

    public int getMajor() {
        return mMajor;
    }

    public int getMinor() {
        return mMinor;
    }

    public BeaconDetails(BluetoothDevice pBluetoothDevice, String pUUID, String pMacAddress, String pName, int pMajor, int pMinor) {

        mBluetoothDevice = pBluetoothDevice;
        mUUID = pUUID;
        mMacAddress = pMacAddress;
        mName = pName;
        mMajor = pMajor;
        mMinor = pMinor;
    }

    protected BeaconDetails(Parcel in) {

    }

}
