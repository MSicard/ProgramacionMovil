package com.iteso.dpm_s9;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iteso.dpm_s9.beans.ItemProduct;
import com.iteso.dpm_s9.database.ControlProduct;
import com.iteso.dpm_s9.database.DataBaseHandler;
import com.iteso.dpm_s9.database.ControlItemProduct;
import com.iteso.dpm_s9.utils.Constants;

import java.util.ArrayList;
import java.util.Iterator;


public class FragmentElectronics extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private ArrayList myDataSet;
    DataBaseHandler dh;
    ControlProduct controlProduct;

    public FragmentElectronics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_electronics, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_electronics_recycler_view);
        //improve performance
        //recyclerView.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);

        ControlItemProduct controlItemProduct = new ControlItemProduct();
        myDataSet = controlItemProduct.getProductsWhere(
                "C.name = 'ELECTRONICS'", DataBaseHandler.KEY_PRODUCT_ID + " ASC",
                DataBaseHandler.getInstance(getActivity()));

        mAdapter = new AdapterProduct(myDataSet, getActivity(), Constants.FRAGMENT_ELECTRONICS);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ItemProduct itemProduct = data.getParcelableExtra(Constants.EXTRA_ITEM);
        Iterator<ItemProduct> iterator = myDataSet.iterator();
        int position = 0;
        while(iterator.hasNext()){
            ItemProduct item = iterator.next();
            if(item.getCode() == itemProduct.getCode()){
                myDataSet.set(position, itemProduct);
                break;
            }
            position++;
        }
        mAdapter.notifyDataSetChanged();
    }

}
