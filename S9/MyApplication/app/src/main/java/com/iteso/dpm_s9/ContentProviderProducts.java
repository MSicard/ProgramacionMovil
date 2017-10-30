package com.iteso.dpm_s9;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.iteso.dpm_s9.database.ControlItemProduct;
import com.iteso.dpm_s9.database.DataBaseHandler;

import java.util.HashMap;

/**
 * Created by Maritza on 29/10/2017.
 */

public class ContentProviderProducts extends ContentProvider {
    static final String PROVIDER_NAME = "com.iteso.dpm_s9";
    static final String URL = "content://" + PROVIDER_NAME + "/products";
    static final Uri CONTENT_URI = Uri.parse(URL);

    private SQLiteDatabase db;
    private DataBaseHandler dh;

    static final String _ID = "_id";

    private static HashMap<String, String> PRODUCTS_PROJECTION_MAP;

    static final int PRODUCTS = 1;
    static final int PRODUCTS_ID = 2;
    static final int PRODUCT_STORE = 3;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "products", PRODUCTS);
        uriMatcher.addURI(PROVIDER_NAME, "products/#", PRODUCTS_ID);
        uriMatcher.addURI(PROVIDER_NAME, "products/store/#", PRODUCT_STORE);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dh = new DataBaseHandler(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        db = dh.getReadableDatabase();
        Cursor c;
        switch (uriMatcher.match(uri)){
            case PRODUCTS:
                 c = db.query(dh.TABLE_PRODUCT, projection, selection,
                        selectionArgs, null, null, sortOrder);
                 return c;
            case PRODUCTS_ID:
                String where = dh.KEY_PRODUCT_ID + "=" + uri.getLastPathSegment();
                c = db.query(dh.TABLE_PRODUCT, projection, where,
                        selectionArgs, null, null, sortOrder);
                return c;
            case PRODUCT_STORE:
                String selectQuery =
                        "Select P." + DataBaseHandler.KEY_PRODUCT_TITLE +
                                " C." + DataBaseHandler.KEY_CATEGORY_NAME +
                                " S." + DataBaseHandler.KEY_STORE_ID +
                                " S." + DataBaseHandler.KEY_STORE_NAME +
                                " S." + DataBaseHandler.KEY_STORE_CITY +
                                " S." + DataBaseHandler.KEY_STORE_PHONE +
                                " S." + DataBaseHandler.KEY_STORE_THUMBNAIL +
                                " S." + DataBaseHandler.KEY_STORE_LAT +
                                " S." + DataBaseHandler.KEY_STORE_LNG +
                                " FROM " + DataBaseHandler.TABLE_PRODUCT + " P, " +
                                DataBaseHandler.TABLE_STORE + " S," +
                                DataBaseHandler.TABLE_CATEGORY + " C," +
                                DataBaseHandler.TABLE_STORE_PRODUCT + " SP WHERE P." +
                                DataBaseHandler.KEY_PRODUCT_CATEGORY + " = C." + DataBaseHandler.KEY_CATEGORY_ID +
                                " AND SP." + DataBaseHandler.KEY_PRODUCT_ID + " = P." + DataBaseHandler.KEY_PRODUCT_ID +
                                " AND SP." + DataBaseHandler.KEY_STORE_ID + " = P." + DataBaseHandler.KEY_STORE_ID +
                                " AND P." + DataBaseHandler.KEY_PRODUCT_ID + " = " + uri.getLastPathSegment();
                c = db.rawQuery(selectQuery, null);
                return c;
            default:
                throw new UnsupportedOperationException("Uri no soportada");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all products records
             */
            case PRODUCTS:
                return "vnd.android.cursor.dir/vnd.iteso.dpm_s9.products";
            /**
             * Get a particular product
             */
            case PRODUCTS_ID:
                return "vnd.android.cursor.item/vnd.iteso.dpm_s9.products";
            case PRODUCT_STORE:
                return "vnd.android.cursos.item/vnd.iteso.dpm_s9.products";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long regId = 1;

        SQLiteDatabase db = dh.getWritableDatabase();

        regId = db.insert(dh.TABLE_PRODUCT, null, contentValues);

        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
        try {
            db.close();
        } catch (Exception e) {
        }
        db = null;

        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int cont;
        String where = selection;
        if(uriMatcher.match(uri) == PRODUCTS_ID){
            where = dh.KEY_PRODUCT_ID + uri.getLastPathSegment();
        }

        SQLiteDatabase db = dh.getWritableDatabase();

        cont = db.delete(dh.TABLE_PRODUCT, where, selectionArgs);
        try {
            db.close();
        } catch (Exception e) {
        }
        db = null;
        return cont;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == PRODUCTS_ID){
            where = dh.KEY_PRODUCT_ID + uri.getLastPathSegment();
        }
        SQLiteDatabase db = dh.getWritableDatabase();

        cont = db.update(dh.TABLE_PRODUCT, contentValues, where, selectionArgs);
        try {
            db.close();
        } catch (Exception e) {
        }
        db = null;
        return cont;
    }
}
