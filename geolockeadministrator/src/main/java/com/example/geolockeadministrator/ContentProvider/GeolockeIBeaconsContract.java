package com.example.geolockeadministrator.ContentProvider;

import android.net.Uri;

/**
 * Created by ACER on 11/1/2016.
 */
public class GeolockeIBeaconsContract {
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.geolockeadmin.geolockeibeacons";
    public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.geolockeadmin.geolockeibeacons";

    public static final String AUTHORITY = "com.geolocke.android.geolockeadmin.geolockeibeacons.provider";

    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/geolockeibeacons");

    public static final String GEOLOCKE_IBEACON_ID =  "_id";
    public static final String GEOLOCKE_IBEACON_MAC_ADDRESS =  "mac_address";
    public static final String GEOLOCKE_IBEACON_UUID = "uuid";
    public static final String GEOLOCKE_IBEACON_LATITUDE= "latitude";
    public static final String GEOLOCKE_IBEACON_LONGITUDE = "longitude";
    public static final String GEOLOCKE_IBEACON_ORGANIZATION_ID = "organizationId";
    public static final String GEOLOCKE_IBEACON_BUILDING_ID = "buildingId";
    public static final String GEOLOCKE_IBEACON_LEVEL_ID = "levelId";
    public static final String GEOLOCKE_IBEACON_SECTION_ID = "sectionId";

}
