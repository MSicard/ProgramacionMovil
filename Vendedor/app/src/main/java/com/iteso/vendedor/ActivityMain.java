package com.iteso.vendedor;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

public class ActivityMain extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    static final String URL = "content://" + Constants.PROVIDER_NAME + "/products";
    static final Uri CONTENT_URI = Uri.parse(URL);



    protected ListView listView;

    private MyAdapter mCursorAdapter;
    private static final

    // Sets the columns to retrieve for the user profile
    String[] PROJECTION = new String[]{
            Constants.KEY_PRODUCT_ID,
            Constants.KEY_PRODUCT_TITLE,
            Constants.KEY_PRODUCT_IMAGE,
            Constants.KEY_PRODUCT_CATEGORY};

    private final static String[] FROM_COLUMNS = {Constants.KEY_PRODUCT_ID,
            Constants.KEY_PRODUCT_TITLE, Constants.KEY_CATEGORY_NAME};
    private final static int[] TO_IDS = {R.id.product_item_position,
            R.id.product_item_name, R.id.product_item_category};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(android.R.id.list);
        mCursorAdapter = new MyAdapter(this,
                R.layout.product_item,
                null,
                FROM_COLUMNS,
                TO_IDS,
                0);
        listView.setAdapter(mCursorAdapter);
        getLoaderManager().restartLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                ActivityMain.this,
                CONTENT_URI, PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }
}
