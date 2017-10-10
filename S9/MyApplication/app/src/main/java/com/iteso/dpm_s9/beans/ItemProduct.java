package com.iteso.dpm_s9.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maritza on 19/09/2017.
 */

public class ItemProduct implements Parcelable{
    private int image;
    private String title, location, store, phone, description;

    public ItemProduct() {
        image = 0;
        title = "";
        location = "";
        store = "";
        phone = "";
        description = "";
    }

    protected ItemProduct(Parcel in) {
        image = in.readInt();
        title = in.readString();
        location = in.readString();
        store = in.readString();
        phone = in.readString();
        description = in.readString();
    }

    public static final Parcelable.Creator<ItemProduct> CREATOR = new Creator<ItemProduct>() {
        @Override
        public ItemProduct createFromParcel(Parcel in) {
            return new ItemProduct(in);
        }

        @Override
        public ItemProduct[] newArray(int size) {
            return new ItemProduct[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(image);
        parcel.writeString(title);
        parcel.writeString(location);
        parcel.writeString(store);
        parcel.writeString(phone);
        parcel.writeString(description);
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ItemProduct{" +
                "image=" + image +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", store='" + store + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
