package com.iteso.vendedor;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Maritza on 30/10/2017.
 */

public class ActivityDetail extends Activity{

    protected TextView name;
    private TextView category;
    private TextView phone;
    private TextView city;
    private TextView store;

    String [] PROJECTION = { "" };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        name = (TextView)findViewById(R.id.activity_detail_name);
        category = (TextView)findViewById(R.id.activity_detail_category);
        store = (TextView)findViewById(R.id.activity_detail_store);
        phone = (TextView)findViewById(R.id.activity_detail_phone);
        city = (TextView)findViewById(R.id.activity_detail_city);

        int idProduct = getIntent().getExtras().getInt("idP");
        Uri CONTENT_URI = Uri.parse("content://" + Constants.PROVIDER_NAME + "/products/" + idProduct);
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(CONTENT_URI,PROJECTION,null,null,null);

        if(c.moveToFirst()){
            name.setText(c.getString(0));
            category.setText(c.getString(1));
            store.setText(c.getString(2));
            phone.setText(c.getString(3));
            city.setText(c.getString(4));
        }

        c.close();
    }
}
