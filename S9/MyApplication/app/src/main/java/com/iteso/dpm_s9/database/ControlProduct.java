package com.iteso.dpm_s9.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.dpm_s9.beans.Category;
import com.iteso.dpm_s9.beans.ItemProduct;
import com.iteso.dpm_s9.beans.Store;

import java.util.ArrayList;

/**
 * Created by Maritza on 10/10/2017.
 */

public class ControlProduct {
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
}
