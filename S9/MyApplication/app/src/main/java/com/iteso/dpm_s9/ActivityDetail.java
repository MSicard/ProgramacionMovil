package com.iteso.dpm_s9;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.iteso.dpm_s9.beans.ItemProduct;

/**
 * Created by Maritza on 26/09/2017.
 */

public class ActivityDetail extends AppCompatActivity{
    protected EditText title, store, location;
    protected ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = (EditText) findViewById(R.id.activity_detail_title);
        store = (EditText) findViewById(R.id.activity_detail_store);
        image = (ImageView) findViewById(R.id.activity_detail_image);
        location = (EditText)findViewById(R.id.activity_detail_location);


        final ItemProduct itemProduct = getIntent().getParcelableExtra("ITEM");
        title.setText(itemProduct.getTitle());
        store.setText(itemProduct.getStore().getName());
        location.setText(itemProduct.getStore().getCity().getName());


        switch (itemProduct.getImage()){
            case 0:
                image.setImageResource(R.drawable.mac);
                break;
            case 1:
                image.setImageResource(R.drawable.alienware);
                break;
        }

    }
}
