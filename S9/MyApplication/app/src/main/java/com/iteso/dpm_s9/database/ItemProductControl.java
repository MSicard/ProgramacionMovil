package com.iteso.dpm_s9.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.dpm_s9.beans.Category;
import com.iteso.dpm_s9.beans.City;
import com.iteso.dpm_s9.beans.ItemProduct;
import com.iteso.dpm_s9.beans.Store;

import java.util.ArrayList;

/**
 * Created by Maritza on 16/10/2017.
 */

public class ItemProductControl {

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
                + "C." + DataBaseHandler.KEY_CATEGORY_ID + ","
                + "C." + DataBaseHandler.KEY_CATEGORY_NAME + " FROM "
                + DataBaseHandler.TABLE_PRODUCT + " P,"
                + DataBaseHandler.TABLE_CATEGORY + " C WHERE P."
                + DataBaseHandler.KEY_PRODUCT_ID + "= " + idProduct
                + " AND P." + DataBaseHandler.KEY_PRODUCT_CATEGORY
                + " = C." + DataBaseHandler.KEY_CATEGORY_ID;
        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            product.setCode(cursor.getInt(0));
            product.setTitle(cursor.getString(1));
            product.setImage(cursor.getInt(2));
            Category category = new Category();
            category.setIdCategory(cursor.getInt(3));
            category.setName(cursor.getString(4));
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
                    + " = C." + DataBaseHandler.KEY_CATEGORY_ID + " "
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
}
