package com.iteso.dpm_s9.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iteso.dpm_s9.beans.Category;
import com.iteso.dpm_s9.beans.ItemProduct;

import java.util.ArrayList;

/**
 * Created by Maritza on 16/10/2017.
 */

public class CategoryControl {
    public ArrayList<Category> getAllCategories(DataBaseHandler dh) {

        ArrayList<Category> categories = new ArrayList<Category>();
        String query = "SELECT C." + DataBaseHandler.KEY_CATEGORY_ID + ","
                        + "C." + DataBaseHandler.KEY_CATEGORY_NAME + " FROM "
                        + DataBaseHandler.TABLE_CATEGORY + " C";

        SQLiteDatabase db = dh.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()) {
            Category category = new Category();
            category.setIdCategory(cursor.getInt(0));
            category.setName(cursor.getString(1));
            categories.add(category);
        }

        try {
            db.close();
        } catch (Exception e) {

        }
        db = null;
        cursor = null;

        return categories;
    }
}
