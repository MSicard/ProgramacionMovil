package com.iteso.vendedor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
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
    private static final int CONTACT_ID_INDEX = 0;

    // The column index for the LOOKUP_KEY column
    private static final int LOOKUP_KEY_INDEX = 1;

    // The column index for the PHOTO_INDEX column
    private static final int PHOTO_ID_INDEX = 3;

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        super.bindView(view, context, cursor);
        TextView name = (TextView) view.findViewById(R.id.product_item_name);
        TextView position = (TextView) view.findViewById(R.id.product_item_position);
        TextView category = (TextView) view.findViewById(R.id.product_item_category);

        position.setText(String.valueOf(cursor.getPosition()));
        final int name_index = cursor.getColumnIndexOrThrow(
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
        name.setText(cursor.getString(name_index));
        //image.setImageBitmap(queryContactImage(cursor.getInt(PHOTO_ID_INDEX)));
        /*name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.valueOf(position.getText().toString());
                cursor.moveToPosition(pos);
                mProductId = cursor.getLong(CONTACT_ID_INDEX);
                mProductKey = cursor.getString(LOOKUP_KEY_INDEX);
                Intent intent = new Intent(context, ActivityDetail.class);
                intent.putExtra("ContactID", mProductId);
                intent.putExtra("ContactKEY", mProductKey);
                context.startActivity(intent);
            }
        });*/
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }
}
