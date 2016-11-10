package com.example.geolockeadministrator.ContentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ACER on 11/1/2016.
 */
public class GeolockeIBeaconsDbHelper extends SQLiteOpenHelper{
    //private static final String DATABASE_NAME = "udinic.db";\
    private static final String DATABASE_NAME = "geolocke.db";
    private static final int DATABASE_VERSION = 1;

    public static final String GEOLOCKE_IBEACONS_TABLE_NAME = "geolockeibeacons";


    public static final String GEOLOCKE_IBEACONS_COL_ID =  "_id";
    public static final String GEOLOCKE_IBEACONS_COL_MAC_ADDRESS =  "mac_address";
    public static final String GEOLOCKE_IBEACONS_COL_UUID = "uuid";
    public static final String GEOLOCKE_IBEACONS_COL_LATITUDE= "latitude";
    public static final String GEOLOCKE_IBEACONS_COL_LONGITUDE = "longitude";
    public static final String GEOLOCKE_IBEACONS_COL_ORGANIZATION_ID = "organizationId";
    public static final String GEOLOCKE_IBEACONS_COL_BUILDING_ID = "buildingId";
    public static final String GEOLOCKE_IBEACONS_COL_LEVEL_ID = "levelId";
    public static final String GEOLOCKE_IBEACONS_COL_SECTION_ID = "sectionId";



    public static final String DATABASE_CREATE= "create table "
            + GEOLOCKE_IBEACONS_TABLE_NAME  + "(" +
            GEOLOCKE_IBEACONS_COL_ID + " integer   primary key autoincrement, " +
            GEOLOCKE_IBEACONS_COL_MAC_ADDRESS + " text not null, " +
            GEOLOCKE_IBEACONS_COL_UUID + " integer not null," +
            GEOLOCKE_IBEACONS_COL_LATITUDE + " double not null, " +
            GEOLOCKE_IBEACONS_COL_LONGITUDE + " double not null," +
            GEOLOCKE_IBEACONS_COL_ORGANIZATION_ID + " integer not null," +
            GEOLOCKE_IBEACONS_COL_BUILDING_ID + " integer not null," +
            GEOLOCKE_IBEACONS_COL_LEVEL_ID + " integer not null," +
            GEOLOCKE_IBEACONS_COL_SECTION_ID + " integer not null" +
            ");";

    public GeolockeIBeaconsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(GeolockeIBeaconsDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + GEOLOCKE_IBEACONS_TABLE_NAME);
        onCreate(db);
    }
}
