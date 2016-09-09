package com.example.geolockeadministrator.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ACER on 9/7/2016.
 */
public class IBeacon implements Parcelable{
    private final String mMacAddress;
    private final String mUuid;
    private final String mName;
    private final int mMajor;
    private final int mMinor;

    protected IBeacon(Parcel in) {
        mMacAddress = in.readString();
        mUuid = in.readString();
        mName = in.readString();
        mMajor = in.readInt();
        mMinor = in.readInt();
    }

    public static final Creator<IBeacon> CREATOR = new Creator<IBeacon>() {
        @Override
        public IBeacon createFromParcel(Parcel in) {
            return new IBeacon(in);
        }

        @Override
        public IBeacon[] newArray(int size) {
            return new IBeacon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pParcel, int pI) {
        pParcel.writeString(mMacAddress);
        pParcel.writeString(mUuid);
        pParcel.writeString(mName);
        pParcel.writeInt(mMajor);
        pParcel.writeInt(mMinor);
    }

    public IBeacon(String pMacAddress, String pUuid, String pName, int pMajor, int pMinor) {
        mMacAddress = pMacAddress;
        mUuid = pUuid;
        mName = pName;
        mMajor = pMajor;
        mMinor = pMinor;
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    public String getUuid() {
        return mUuid;
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

    @Override
    public boolean equals(Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;

        IBeacon iBeacon = (IBeacon) pO;

        return mMacAddress.equals(iBeacon.mMacAddress);

    }

    @Override
    public int hashCode() {
        return mMacAddress.hashCode();
    }

    @Override
    public String toString() {
        return "IBeacon{" +
                "mMacAddress='" + mMacAddress + '\'' +
                ", mUuid='" + mUuid + '\'' +
                ", mName='" + mName + '\'' +
                ", mMajor=" + mMajor +
                ", mMinor=" + mMinor +
                '}';
    }
}
