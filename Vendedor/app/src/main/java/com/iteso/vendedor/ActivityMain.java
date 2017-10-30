package com.iteso.vendedor;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

public class ActivityMain extends AppCompatActivity {

    static final String PROVIDER_NAME = "com.iteso.dpm_s9";
    static final String URL = "content://" + PROVIDER_NAME + "/products";
    static final Uri CONTENT_URI = Uri.parse(URL);


    protected ListView listView;
    protected EditText editText;

    private MyAdapter mCursorAdapter;
    private static final

    // Sets the columns to retrieve for the user profile
    String[] PROJECTION = new String[]{
            Constants.KEY_PRODUCT_ID,
            Constants.KEY_PRODUCT_TITLE,
            Constants.KEY_PRODUCT_IMAGE,
            Constants.KEY_PRODUCT_CATEGORY};

    //private final static String[] FROM_COLUMNS = {Constants.KEY_PRODUCT_TITLE};
    //private final static int[] TO_IDS = {R.id.product_item_name};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(android.R.id.list);
        editText = (EditText) findViewById(R.id.activity_main_search);

        testing();
       // listView.setAdapter(mCursorAdapter);
    }

    public void testing(){
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(CONTENT_URI, PROJECTION, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String nombre = c.getString(1);
            int image = c.getInt(2);
            int category = c.getInt(3);
            Log.d("QUERY", "" +id+ ", " +nombre+ ", " +image+ ", " + category);
        }
        try {
            c.close();
        } catch (Exception e) {
        }
        c = null;
    }

}
