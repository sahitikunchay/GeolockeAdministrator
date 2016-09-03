package com.example.geolockeadministrator.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ACER on 9/3/2016.
 */
public class GeolockeIBeacon implements Parcelable{

    private final double mLatitude;
    private final double mLogitude;
    private final int mOrganizationId;
    private final int mBuildingId;
    private final int mLevelId;

    protected GeolockeIBeacon(Parcel in) {
        mLatitude = in.readDouble();
        mLogitude = in.readDouble();
        mOrganizationId = in.readInt();
        mBuildingId = in.readInt();
        mLevelId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLogitude);
        dest.writeInt(mOrganizationId);
        dest.writeInt(mBuildingId);
        dest.writeInt(mLevelId);
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

    public GeolockeIBeacon(double pLatitude, double pLogitude, int pOrganizationId, int pBuildingId, int pLevelId) {
        mLatitude = pLatitude;
        mLogitude = pLogitude;
        mOrganizationId = pOrganizationId;
        mBuildingId = pBuildingId;
        mLevelId = pLevelId;
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

    @Override
    public boolean equals(Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;

        GeolockeIBeacon that = (GeolockeIBeacon) pO;

        if (Double.compare(that.mLatitude, mLatitude) != 0) return false;
        if (Double.compare(that.mLogitude, mLogitude) != 0) return false;
        if (mOrganizationId != that.mOrganizationId) return false;
        if (mBuildingId != that.mBuildingId) return false;
        return mLevelId == that.mLevelId;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(mLatitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mLogitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + mOrganizationId;
        result = 31 * result + mBuildingId;
        result = 31 * result + mLevelId;
        return result;
    }

    @Override
    public String toString() {
        return "GeolockeIBeacon{" +
                "mLatitude=" + mLatitude +
                ", mLogitude=" + mLogitude +
                ", mOrganizationId=" + mOrganizationId +
                ", mBuildingId=" + mBuildingId +
                ", mLevelId=" + mLevelId +
                '}';
    }
}
