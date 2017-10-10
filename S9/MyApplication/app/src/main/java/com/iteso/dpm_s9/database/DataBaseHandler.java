package com.iteso.dpm_s9.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maritza on 09/10/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyProducts.db";
    private static final int DATABASE_VERSION = 1;
    private DataBaseHandler dataBaseHandler;

    private DataBaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseHandler getInstace(Context context){
        if(dataBaseHandler == null){
            dataBaseHandler = new DataBaseHandler(context);
        }
        return dataBaseHandler;
    }

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE Table Geschäft(idGeschäft Integer NOT NULL, " +
                "namen TEXT, idStadt Integer NOT NULL, telefon TEXT, standort TEXT)";
        sqLiteDatabase.execSQL(createTable);
        String createTable2 = "CREATE Table Produkt(idProdukt Integer NOT NULL, " +
                "namen TEXT, idGeschäft Integer NOT NULL, idKategorie Integer NOT NULL," +
                " bild Integer, beschreibung TEXT)";
        sqLiteDatabase.execSQL(createTable2);
        String createTable3 = "CREATE Table Stadt(idStadt Integer NOT NULL, " +
                "namen TEXT, zustand TEXT)";
        sqLiteDatabase.execSQL(createTable3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
