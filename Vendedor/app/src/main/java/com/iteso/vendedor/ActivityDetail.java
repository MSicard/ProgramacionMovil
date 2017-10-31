package com.iteso.vendedor;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

/**
 * Created by Maritza on 30/10/2017.
 */

public class ActivityDetail extends Activity{

    static final String PROVIDER_NAME = "com.iteso.dpm_s9";

    protected EditText name;
    private EditText category;
    private EditText phone;
    private EditText city;
    private EditText store;

    String [] PROJECTION = { "" };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        name = (EditText)findViewById(R.id.activity_detail_name);
        category = (EditText)findViewById(R.id.activity_detail_category);
        store = (EditText)findViewById(R.id.activity_detail_store);
        phone = (EditText)findViewById(R.id.activity_detail_phone);
        city = (EditText)findViewById(R.id.activity_detail_city);

        String id_producto = getIntent().getExtras().getString("ID_PROD");
        Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/productos/store/"+id_producto);

        Cursor c = getContentResolver().query(CONTENT_URI,PROJECTION,null,null,null);

        if(c.moveToFirst()){
            name.setText(c.getString(3));
            category.setText(c.getString(5));
            store.setText(c.getString(8));
            phone.setText(c.getString(10));
            city.setText(c.getString(9));
        }

        c.close();
    }
}
