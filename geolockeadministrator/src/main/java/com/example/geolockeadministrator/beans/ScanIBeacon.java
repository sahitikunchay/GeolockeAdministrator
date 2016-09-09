package com.example.geolockeadministrator.beans;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ACER on 9/3/2016.
 */
public class ScanIBeacon implements Parcelable{

    private ArrayList<IBeacon> mIBeaconArrayList;
    private ArrayList<Integer> mRssiList;


    protected ScanIBeacon(Parcel pParcel) {
        mIBeaconArrayList = pParcel.readArrayList(IBeacon.class.getClassLoader());
        mRssiList = pParcel.readArrayList(int.class.getClassLoader());
    }

    public static final Creator<ScanIBeacon> CREATOR = new Creator<ScanIBeacon>() {
        @Override
        public ScanIBeacon createFromParcel(Parcel in) {
            return new ScanIBeacon(in);
        }

        @Override
        public ScanIBeacon[] newArray(int size) {
            return new ScanIBeacon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pParcel, int pI) {
        pParcel.writeList(mIBeaconArrayList);
        pParcel.writeList(mRssiList);
    }

    public ScanIBeacon(ArrayList<IBeacon> pIBeaconArrayList, ArrayList<Integer> pRssiList) {
        mIBeaconArrayList = pIBeaconArrayList;
        mRssiList = pRssiList;
    }

    public ArrayList<IBeacon> getIBeaconArrayList() {
        return mIBeaconArrayList;
    }

    public ArrayList<Integer> getRssiList() {
        return mRssiList;
    }

    @Override
    public boolean equals(Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;

        ScanIBeacon that = (ScanIBeacon) pO;

        if (!mIBeaconArrayList.equals(that.mIBeaconArrayList)) return false;
        return mRssiList.equals(that.mRssiList);

    }

    @Override
    public int hashCode() {
        int result = mIBeaconArrayList.hashCode();
        result = 31 * result + mRssiList.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ScanIBeacon{" +
                "mIBeaconArrayList=" + mIBeaconArrayList +
                ", mRssiList=" + mRssiList +
                '}';
    }
}
