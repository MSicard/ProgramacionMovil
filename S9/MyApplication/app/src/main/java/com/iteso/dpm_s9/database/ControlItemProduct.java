package com.iteso.dpm_s9.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iteso.dpm_s9.beans.Category;
import com.iteso.dpm_s9.beans.City;
import com.iteso.dpm_s9.beans.ItemProduct;
import com.iteso.dpm_s9.beans.Store;

import java.util.ArrayList;

/**
 * Created by Maritza on 16/10/2017.
 */

public class ControlItemProduct {

    public long addItemProduct(ItemProduct product, DataBaseHandler dh){
        long inserted = 0;
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataBaseHandler.KEY_PRODUCT_TITLE, product.getTitle());
        values.put(DataBaseHandler.KEY_PRODUCT_IMAGE, product.getImage());
        values.put(DataBaseHandler.KEY_PRODUCT_CATEGORY, product.getCategory().getIdCategory());
        // Inserting Row
        inserted = db.insert(DataBaseHandler.TABLE_PRODUCT, null, values);
        // Closing database connection
        try {db.close();} catch (Exception e) {}
        db = null; values = null;
        return inserted;
    }

    public int addProduct(ItemProduct product, DataBaseHandler dh) {
        int inserted = 0;
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.KEY_PRODUCT_TITLE, product.getTitle());
        values.put(DataBaseHandler.KEY_PRODUCT_IMAGE, product.getImage());
        values.put(DataBaseHandler.KEY_PRODUCT_CATEGORY, product.getCategory().getIdCategory());
        // Inserting Row
        db.insert(DataBaseHandler.TABLE_PRODUCT, null, values);

        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if (cursor.moveToFirst()) {
            values.clear();
            inserted = cursor.getInt(0);
            values.put(DataBaseHandler.KEY_PRODUCT_ID, cursor.getInt(0));
            values.put(DataBaseHandler.KEY_STORE_ID, product.getStore().getId());
            db.insert(DataBaseHandler.TABLE_STORE_PRODUCT, null, values);
        }

        // Closing database connection
        try {
            cursor.close();
            db.close();
        } catch (Exception e) {
        }
        db = null;
        cursor = null;
        values = null;
        return inserted;
    }

    public int updateProduct(ItemProduct product, DataBaseHandler dh){
        long inserted = 0;
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DataBaseHandler.KEY_PRODUCT_TITLE, product.getTitle());
        values.put(DataBaseHandler.KEY_PRODUCT_IMAGE, product.getImage());
        values.put(DataBaseHandler.KEY_PRODUCT_CATEGORY, product.getCategory().getIdCategory());
        int count = db.update(DataBaseHandler.TABLE_PRODUCT, values,
                DataBaseHandler.KEY_PRODUCT_ID + " = ?",
                new String[] { String.valueOf(product.getCode()) });
        try { db.close();} catch (Exception e) {}
        db = null;
        return count;
    }

    public void deleteProduct(int idProduct, DataBaseHandler dh){
        SQLiteDatabase db = dh.getWritableDatabase();
        db.delete(DataBaseHandler.TABLE_STORE, DataBaseHandler.KEY_PRODUCT_ID
                + " = ?", new String[] { String.valueOf(idProduct) });
        try {
            db.close();
        } catch (Exception e) {
        }
        db = null;
    }


    public ItemProduct getProductById(int idProduct, DataBaseHandler dh){
        ItemProduct product = new ItemProduct();
        String selectQuery = "SELECT P." + DataBaseHandler.KEY_PRODUCT_ID + ","
                + "P." + DataBaseHandler.KEY_PRODUCT_TITLE + ","
                + "P." + DataBaseHandler.KEY_PRODUCT_IMAGE + ","
                + "P." + DataBaseHandler.KEY_PRODUCT_CATEGORY + ","
                + "C." + DataBaseHandler.KEY_CATEGORY_ID + ","
                + "C." + DataBaseHandler.KEY_CATEGORY_NAME + " FROM "
                + DataBaseHandler.TABLE_PRODUCT + " P,"
                + DataBaseHandler.TABLE_CATEGORY + " C WHERE P."
                + DataBaseHandler.KEY_PRODUCT_ID + "= " + idProduct
                + " AND P." + DataBaseHandler.KEY_PRODUCT_CATEGORY
                + " = C." + DataBaseHandler.KEY_CATEGORY_ID;
        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.v("Product", selectQuery);
        if (cursor.moveToFirst()) {
            product.setCode(cursor.getInt(0));
            product.setTitle(cursor.getString(1));
            product.setImage(cursor.getInt(2));
            Category category = new Category();
            category.setIdCategory(cursor.getInt(4));
            category.setName(cursor.getString(5));
            product.setCategory(category);
        }
        try {cursor.close();db.close();
        } catch (Exception e) {}
        db = null;
        cursor = null;
        return product;
    }
    public ArrayList<ItemProduct> getProductsWhere(String strWhere, String strOrderBy, DataBaseHandler dh){
        ArrayList<ItemProduct> products = new ArrayList<ItemProduct>();
        String query;
        if(strWhere != null){
            query = "SELECT P." + DataBaseHandler.KEY_PRODUCT_ID + ","
                    + "P." + DataBaseHandler.KEY_PRODUCT_TITLE + ","
                    + "P." + DataBaseHandler.KEY_PRODUCT_IMAGE + ","
                    + "C." + DataBaseHandler.KEY_CATEGORY_ID + ","
                    + "C." + DataBaseHandler.KEY_CATEGORY_NAME + " FROM "
                    + DataBaseHandler.TABLE_PRODUCT + " P,"
                    + DataBaseHandler.TABLE_CATEGORY + " C WHERE "
                    + " P." + DataBaseHandler.KEY_PRODUCT_CATEGORY
                    + " = C." + DataBaseHandler.KEY_CATEGORY_ID + " AND "
                    + strWhere + " ORDER BY " + strOrderBy;
        }else{
            query = "SELECT P." + DataBaseHandler.KEY_PRODUCT_ID + ","
                    + "P." + DataBaseHandler.KEY_PRODUCT_TITLE + ","
                    + "P." + DataBaseHandler.KEY_PRODUCT_IMAGE + ","
                    + "C." + DataBaseHandler.KEY_CATEGORY_ID + ","
                    + "C." + DataBaseHandler.KEY_CATEGORY_NAME + " FROM "
                    + DataBaseHandler.TABLE_PRODUCT + " P,"
                    + DataBaseHandler.TABLE_CATEGORY + " C WHERE "
                    + " P." + DataBaseHandler.KEY_PRODUCT_CATEGORY
                    + " = C." + DataBaseHandler.KEY_CATEGORY_ID
                    + " ORDER BY " + strOrderBy;
        }
        SQLiteDatabase db = dh.getReadableDatabase();

        // El null funcion como en el where del delete, ahi iria el arreglo
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()) {
            ItemProduct product = new ItemProduct();
            product.setCode(cursor.getInt(0));
            product.setTitle(cursor.getString(1));
            product.setImage(cursor.getInt(2));
            Category category = new Category();
            category.setIdCategory(cursor.getInt(3));
            category.setName(cursor.getString(4));
            product.setCategory(category);
            products.add(product);
        }

        try {
            db.close();
        } catch (Exception e) {

        }
        db = null;
        cursor = null;

        return products;
    }

    public ArrayList<ItemProduct> getProducts(int idCategory, DataBaseHandler dh){
        ArrayList<ItemProduct> products = new ArrayList<>();

        String selectQuery = "SELECT  P." + DataBaseHandler.KEY_PRODUCT_ID + ","
                + "P." + DataBaseHandler.KEY_PRODUCT_TITLE + ","
                + "0 " + DataBaseHandler.KEY_PRODUCT_IMAGE + ","
                + "C. " + DataBaseHandler.KEY_PRODUCT_CATEGORY + ","
                + "C." + DataBaseHandler.KEY_CATEGORY_NAME
                + " FROM " + DataBaseHandler.TABLE_PRODUCT + " P, "
                + DataBaseHandler.TABLE_CATEGORY + " C,"
                + DataBaseHandler.TABLE_STORE_PRODUCT + " SP WHERE P."
                + DataBaseHandler.KEY_PRODUCT_CATEGORY + " = C." + DataBaseHandler.KEY_CATEGORY_ID
                + " AND C." + DataBaseHandler.KEY_CATEGORY_ID + "=" + (idCategory+1)
                + " AND SP." + DataBaseHandler.KEY_PRODUCT_ID + " = P." + DataBaseHandler.KEY_PRODUCT_ID;
        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            ItemProduct product = new ItemProduct();
            product.setCode(cursor.getInt(0));
            product.setTitle(cursor.getString(1));
            product.setImage(cursor.getInt(2));

            Category category = new Category();
            category.setIdCategory(cursor.getInt(3));
            category.setName(cursor.getString(4));
            product.setCategory(category);
            product.setStore(getStore(product.getCode(), dh));

            products.add(product);
        }
        try {
            cursor.close();
            db.close();
        } catch (Exception e) {
        }
        db = null;
        cursor = null;
        // return store

        return products;
    }

    private Store getStore(int idProduct, DataBaseHandler dh){
        return new ControlStore().getStoreByProductId(idProduct, dh);
    }

    public Cursor getStoreByIdProduct(String id, DataBaseHandler dh){
        String selectQuery =
                "Select P." + DataBaseHandler.KEY_PRODUCT_TITLE + "," +
                        " C." + DataBaseHandler.KEY_CATEGORY_NAME + "," +
                        " S." + DataBaseHandler.KEY_STORE_NAME + "," +
                        " S." + DataBaseHandler.KEY_STORE_PHONE + "," +
                        " Ci." + DataBaseHandler.KEY_CITY_NAME +
                        " FROM " + DataBaseHandler.TABLE_PRODUCT + " P, " +
                        DataBaseHandler.TABLE_STORE + " S," +
                        DataBaseHandler.TABLE_CATEGORY + " C," +
                        DataBaseHandler.TABLE_CITY + " Ci," +
                        DataBaseHandler.TABLE_STORE_PRODUCT + " SP WHERE P." +
                        DataBaseHandler.KEY_PRODUCT_CATEGORY + " = C." + DataBaseHandler.KEY_CATEGORY_ID +
                        " AND SP." + DataBaseHandler.KEY_PRODUCT_ID + " = P." + DataBaseHandler.KEY_PRODUCT_ID +
                        " AND SP." + DataBaseHandler.KEY_STORE_ID + " = S." + DataBaseHandler.KEY_STORE_ID +
                        " AND Ci." + DataBaseHandler.KEY_CITY_ID + " = S." + DataBaseHandler.KEY_STORE_CITY +
                        " AND P." + DataBaseHandler.KEY_PRODUCT_ID + " = " + id;
        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }


    public  Cursor getProducts(DataBaseHandler dh){
        String selectQuery = "SELECT P." + DataBaseHandler.KEY_PRODUCT_ID + "," +
                             " P." + DataBaseHandler.KEY_PRODUCT_TITLE + "," +
                             " P." + DataBaseHandler.KEY_PRODUCT_CATEGORY + "," +
                             " C." + DataBaseHandler.KEY_CATEGORY_NAME  +
                             " FROM " + DataBaseHandler.TABLE_PRODUCT + " P," +
                             DataBaseHandler.TABLE_CATEGORY + " C" +
                             " WHERE " +
                             " P." + DataBaseHandler.KEY_PRODUCT_CATEGORY + " = C." + DataBaseHandler.KEY_CATEGORY_ID;
                SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
}


/*"Select P." + DataBaseHandler.KEY_PRODUCT_TITLE +
                        " C." + DataBaseHandler.KEY_CATEGORY_NAME +
                        " S." + DataBaseHandler.KEY_STORE_NAME +
                        " S." + DataBaseHandler.KEY_STORE_PHONE +
                        " S." + DataBaseHandler.KEY_STORE_THUMBNAIL +
                        " S." + DataBaseHandler.KEY_STORE_LAT +
                        " S." + DataBaseHandler.KEY_STORE_LNG +
                        " Ci." + DataBaseHandler.KEY_CITY_NAME +
                        " FROM " + DataBaseHandler.TABLE_PRODUCT + " P, " +
                        DataBaseHandler.TABLE_STORE + " S," +
                        DataBaseHandler.TABLE_CATEGORY + " C," +
                        DataBaseHandler.TABLE_CITY + " Ci," +
                        DataBaseHandler.TABLE_STORE_PRODUCT + " SP WHERE P." +
                        DataBaseHandler.KEY_PRODUCT_CATEGORY + " = C." + DataBaseHandler.KEY_CATEGORY_ID +
                        " AND SP." + DataBaseHandler.KEY_PRODUCT_ID + " = P." + DataBaseHandler.KEY_PRODUCT_ID +
                        " AND SP." + DataBaseHandler.KEY_STORE_ID + " = S." + DataBaseHandler.KEY_STORE_ID +
                        " AND Ci." + DataBaseHandler.KEY_CITY_ID + " = S." + DataBaseHandler.KEY_STORE_CITY +
                        " AND P." + DataBaseHandler.KEY_PRODUCT_ID + " = " + id;*/