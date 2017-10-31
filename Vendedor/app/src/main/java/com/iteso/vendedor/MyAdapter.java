package com.iteso.vendedor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Maritza on 30/10/2017.
 */

class MyAdapter  extends SimpleCursorAdapter {

    private Context mContext;
    private int layout;
    private final LayoutInflater inflater;

    public MyAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mContext = context;
        this.layout = layout;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView name = (TextView) view.findViewById(R.id.product_item_name);
        TextView category = (TextView) view.findViewById(R.id.product_item_category);
        int idCategory = cursor.getInt(cursor.getColumnIndex(Constants.KEY_PRODUCT_CATEGORY));
        final int idProduct = cursor.getInt(cursor.getColumnIndex(Constants.KEY_PRODUCT_ID));

        switch (idCategory){
            case Constants.FRAGMENT_ELECTRONICS:
                category.setText("Electronics");
                break;
            case Constants.FRAGMENT_HOME:
                category.setText("Home");
                break;
            case Constants.FRAGMENT_TECHNOLOGY:
                category.setText("Technology");
                break;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityDetail.class);
                intent.putExtra("idP", idProduct);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }
}
