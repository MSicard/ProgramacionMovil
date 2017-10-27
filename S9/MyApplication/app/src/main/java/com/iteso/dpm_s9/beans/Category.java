package com.iteso.dpm_s9.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maritza on 16/10/2017.
 */

public class Category implements Parcelable{

    private int idCategory;
    private String name;

    public Category(){

    }

    public Category(int idCategory, String name) {
        this.idCategory = idCategory;
        this.name = name;
    }

    protected Category(Parcel in) {
        idCategory = in.readInt();
        name = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idCategory);
        parcel.writeString(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
