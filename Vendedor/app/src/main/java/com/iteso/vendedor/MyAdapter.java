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

    // The contact's _ID value
    private long mProductId;
    // The contact's LOOKUP_KEY
    private String mProductKey;

    // The column index for the _ID column
    private static final int PRODUCT_ID_INDEX = 0;

    private static final int PRODUCT_NAME_INDEX = 1;
    // The column index for the LOOKUP_KEY column
    private static final int CATEGORY_COLUMN = 2;


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        super.bindView(view, context, cursor);

        TextView category = (TextView) view.findViewById(R.id.product_item_category);

        /*position.setText(String.valueOf(cursor.getPosition()));
        name.setText( cursor.getString(cursor.getColumnIndex(Constants.KEY_PRODUCT_TITLE)));*/
        int idCategory = cursor.getInt(cursor.getColumnIndex(Constants.KEY_PRODUCT_CATEGORY));

        /*int id = cursor.getInt(0);
        String nombre = cursor.getString(1);
        int image = cursor.getInt(2);
        int category2 = cursor.getInt(3);
        Log.d("QUERY", "" +id+ ", " +nombre+ ", " +image+ ", " + category2);*/
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
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }
}
