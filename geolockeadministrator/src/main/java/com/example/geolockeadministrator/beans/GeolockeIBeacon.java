package com.example.geolockeadministrator.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ACER on 10/30/2016.
 */
public class GeolockeIBeacon implements Parcelable {

    private final String macAddress;
    private String mUuid;
    private double mLatitude;
    private double mLogitude;
    private final int mOrganizationId;
    private final int mBuildingId;
    private int mLevelId;

    public int getGeofenceId() {
        return mGeofenceId;
    }

    private int mGeofenceId;


    protected GeolockeIBeacon(Parcel in) {
        macAddress = in.readString();
        mUuid = in.readString();
        mLatitude = in.readDouble();
        mLogitude = in.readDouble();
        mOrganizationId = in.readInt();
        mBuildingId = in.readInt();
        mLevelId = in.readInt();
        mGeofenceId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(macAddress);
        dest.writeString(mUuid);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLogitude);
        dest.writeInt(mOrganizationId);
        dest.writeInt(mBuildingId);
        dest.writeInt(mLevelId);
        dest.writeInt(mGeofenceId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GeolockeIBeacon> CREATOR = new Creator<GeolockeIBeacon>() {
        @Override
        public GeolockeIBeacon createFromParcel(Parcel in) {
            return new GeolockeIBeacon(in);
        }

        @Override
        public GeolockeIBeacon[] newArray(int size) {
            return new GeolockeIBeacon[size];
        }
    };

    public GeolockeIBeacon(String pMacAddress, String pUuid, double pLatitude, double pLongitude, int pOrganizationId, int pBuildingId, int pLevelId, int pGeofenceId) {
        macAddress = pMacAddress;
        mUuid = pUuid;
        mLatitude = pLatitude;
        mLogitude = pLongitude;
        mOrganizationId = pOrganizationId;
        mBuildingId = pBuildingId;
        mLevelId = pLevelId;
        mGeofenceId = pGeofenceId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLogitude() {
        return mLogitude;
    }

    public int getOrganizationId() {
        return mOrganizationId;
    }

    public int getBuildingId() {
        return mBuildingId;
    }

    public int getLevelId() {
        return mLevelId;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String pUuid) {
        mUuid = pUuid;
    }

    public void setLatitude(double pLatitude) {
        mLatitude = pLatitude;
    }

    public void setLogitude(double pLogitude) {
        mLogitude = pLogitude;
    }

    public void setLevelId(int pLevelId) {
        mLevelId = pLevelId;
    }

    public void setGeofenceId(int pGeofenceId) {
        mGeofenceId = pGeofenceId;
    }

    @Override
    public boolean equals(Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;

        GeolockeIBeacon that = (GeolockeIBeacon) pO;

        if (!macAddress.equals(that.macAddress)) return false;
        return mUuid.equals(that.mUuid);

    }

    @Override
    public int hashCode() {
        int result = macAddress.hashCode();
        result = 31 * result + mUuid.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GeolockeIBeacon{" +
                "macAddress='" + macAddress + '\'' +
                ", mUuid='" + mUuid + '\'' +
                ", mLatitude=" + mLatitude +
                ", mLogitude=" + mLogitude +
                ", mOrganizationId=" + mOrganizationId +
                ", mBuildingId=" + mBuildingId +
                ", mLevelId=" + mLevelId +
                ", mGeofenceId=" + mGeofenceId +
                '}';
    }
}
