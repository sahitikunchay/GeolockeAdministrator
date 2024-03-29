package com.example.geolockeadministrator.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;


import static com.example.geolockeadministrator.ContentProvider.GeolockeIBeaconsContract.AUTHORITY;

/**
 * Created by ACER on 11/1/2016.
 */
public class GeolockeIBeaconsContentProvider extends ContentProvider{

    public static final UriMatcher URI_MATCHER = buildUriMatcher();
    public static final String PATH = "geolockeibeacons";
    public static final int PATH_TOKEN = 100;
    public static final String PATH_FOR_ID = "geolockeibeacons/*";
    public static final int PATH_FOR_ID_TOKEN = 200;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;
        matcher.addURI(authority, PATH, PATH_TOKEN);
        matcher.addURI(authority, PATH_FOR_ID, PATH_FOR_ID_TOKEN);


        return matcher;
    }


    private GeolockeIBeaconsDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        Context ctx = getContext();
        dbHelper = new GeolockeIBeaconsDbHelper(ctx);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case PATH_TOKEN:
                return GeolockeIBeaconsContract.CONTENT_TYPE_DIR;
            case PATH_FOR_ID_TOKEN:
                return GeolockeIBeaconsContract.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("URI " + uri + " is not supported.");
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int match = URI_MATCHER.match(uri);
        switch (match) {
            case PATH_TOKEN: {
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_TABLE_NAME);
                return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            }
            case PATH_FOR_ID_TOKEN: {
                int iBeaconId = (int) ContentUris.parseId(uri);
                SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
                builder.setTables(GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_TABLE_NAME);
                builder.appendWhere(GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_ID + "=" + iBeaconId);
                return builder.query(db, projection, selection,selectionArgs, null, null, sortOrder);
            }
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        switch (token) {
            case PATH_TOKEN: {
                long id = db.insert(GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_TABLE_NAME, null, values);
                if (id != -1)
                    getContext().getContentResolver().notifyChange(uri, null);
                return GeolockeIBeaconsContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
            }

            default: {
                throw new UnsupportedOperationException("URI: " + uri + " not supported.");
            }
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = URI_MATCHER.match(uri);
        int rowsDeleted = -1;
        String iBeaconIdWhereClause = GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_COL_ID + "=" + uri.getLastPathSegment();
        switch (token) {
            case (PATH_TOKEN):
                rowsDeleted = db.delete(GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_TABLE_NAME, selection, selectionArgs);
                break;
            case (PATH_FOR_ID_TOKEN):
                if (!TextUtils.isEmpty(selection))
                    iBeaconIdWhereClause += " AND " + selection;
                rowsDeleted = db.delete(GeolockeIBeaconsDbHelper.GEOLOCKE_IBEACONS_TABLE_NAME, iBeaconIdWhereClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // Notifying the changes, if there are any
        if (rowsDeleted != -1)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    /**
     * Man..I'm tired..
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
