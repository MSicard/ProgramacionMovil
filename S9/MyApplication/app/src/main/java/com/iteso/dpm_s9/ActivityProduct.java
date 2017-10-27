package com.iteso.dpm_s9;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iteso.dpm_s9.beans.Category;
import com.iteso.dpm_s9.beans.ItemProduct;
import com.iteso.dpm_s9.beans.Store;
import com.iteso.dpm_s9.database.ControlCategory;
import com.iteso.dpm_s9.database.DataBaseHandler;
import com.iteso.dpm_s9.database.ControlItemProduct;
import com.iteso.dpm_s9.database.ControlStore;

import java.util.ArrayList;

/**
 * Created by Maritza on 16/10/2017.
 */

public class ActivityProduct extends AppCompatActivity {

    protected Spinner stores;
    protected Spinner categories;
    protected Spinner images;
    protected EditText id;
    protected EditText title;
    protected EditText description;
    protected ArrayAdapter<Store> storesAdapter;
    protected ArrayAdapter<Category> categoriesAdapter;
    protected ArrayAdapter<String> imagesAdapter;
    protected DataBaseHandler dh; //DataBase Instance
    protected Store storeSelected; //Store selected in spinner
    protected Category categorySelected; //Category selected in spinner
    protected int imageSelected; //Image selected in spinner

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        stores = (Spinner)findViewById(R.id.activity_product_store);
        categories = (Spinner)findViewById(R.id.activity_product_category);
        images = (Spinner)findViewById(R.id.activity_product_image);
        id = (EditText)findViewById(R.id.activity_product_id);
        title = (EditText)findViewById(R.id.activity_product_title);
        description = (EditText)findViewById(R.id.activity_product_description);

        //DataBase Objects
        dh = DataBaseHandler.getInstance(this);
        ControlStore storeControl = new ControlStore();
        ControlCategory categoryControl = new ControlCategory();
        //Fill info from Database
        ArrayList<Store> storesList = storeControl.getStores(dh);
        storesAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, storesList);
        stores.setAdapter(storesAdapter);

        stores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                storeSelected = storesAdapter.getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayList<Category> categoriesList = categoryControl.getAllCategories(dh);
        categoriesAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesList);
        categories.setAdapter(categoriesAdapter);

        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categorySelected = categoriesAdapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> myimages = new ArrayList<>();
        myimages.add("Mac");
        myimages.add("Alienware");
        imagesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, myimages);
        images.setAdapter(imagesAdapter);


        images.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageSelected = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_product_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {

                ItemProduct itemProduct = new ItemProduct();
                itemProduct.setTitle(title.getText().toString());
                itemProduct.setDescription(description.getText().toString());
                itemProduct.setStore(storeSelected);
                itemProduct.setCategory(categorySelected);
                itemProduct.setImage(imageSelected);
                ControlItemProduct controlItemProduct = new ControlItemProduct();
                controlItemProduct.addProduct(itemProduct, dh);

                finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValidProduct() {
        if(!stores.isSelected()) return false;
        if(!categories.isSelected())return false;
        if(!images.isSelected())return false;
        if(id.getText().toString() == "") return false;
        if(title.getText().toString() == "")return false;
        if(description.getText().toString() == "")return false;
        return true;
    }

    public void setCategorySelected(int categoryId){
        for (int position = 0; position < categoriesAdapter.getCount(); position++)
        {
            if(((Category)categoriesAdapter.getItem(position)).getIdCategory() == categoryId)
            {
                categories.setSelection(position);
                return;
            }
        }
    }
    public void setStoreSelected(int storeId){
        for (int position = 0; position < storesAdapter.getCount(); position++)
        {
            if(((Store)storesAdapter.getItem(position)).getId() == storeId)
            {
                stores.setSelection(position);
                return;
            }
        }
    }
    public void setImageSelected(int imageId){
        images.setSelection(imageId);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_product_search:
                int idProduct = 0;
                try {
                    idProduct = Integer.parseInt(id.getText().toString());
                }catch(NumberFormatException e){ return; }
                ControlItemProduct controlItemProduct = new ControlItemProduct();
                ItemProduct itemProduct = controlItemProduct.getProductById(idProduct, dh);
                if(itemProduct != null) {
                    Toast.makeText(this, "Item:" + itemProduct, Toast.LENGTH_LONG).show();
                    title.setText(itemProduct.getTitle());
                    description.setText(itemProduct.getDescription());
                    if(itemProduct.getCategory() != null) {
                        setCategorySelected(itemProduct.getCategory().getIdCategory());}
                    if(itemProduct.getStore()!= null) {
                        setStoreSelected(itemProduct.getStore().getId());}
                    setImageSelected(itemProduct.getImage());
                }
                break;
        }
    }
}
