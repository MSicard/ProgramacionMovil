package com.iteso.dpm_s9.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Maritza on 26/09/2017.
 */

public class Store implements Parcelable {
    String name;
    ArrayList<ItemProduct> products;

    public Store(String name, ArrayList<ItemProduct> products) {
        this.name = name;
        this.products = products;
    }

    protected Store(Parcel in) {
        name = in.readString();
        products = in.createTypedArrayList(ItemProduct.CREATOR);
    }

    public static final Parcelable.Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(products);
    }
}
