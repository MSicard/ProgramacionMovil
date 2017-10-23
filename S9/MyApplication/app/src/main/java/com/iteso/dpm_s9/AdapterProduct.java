package com.iteso.dpm_s9;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iteso.dpm_s9.beans.ItemProduct;
import com.iteso.dpm_s9.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Maritza on 21/09/2017.
 */

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    private ArrayList<ItemProduct> mDataSet;
    private Context context;
    private int fragment;

    public AdapterProduct(ArrayList data, Context context, int fragment){
        this.mDataSet = data;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(AdapterProduct.ViewHolder holder, final int position) {
        holder.mProductTitle.setText(mDataSet.get(position).getTitle());
        holder.mProductStore.setText(mDataSet.get(position).getStore().getName());
        holder.mProductLocation.setText(
                mDataSet.get(position).getStore().getCity().getName() + ", Jalisco");
        holder.mProductPhone.setText(mDataSet.get(position).getStore().getPhone());
        holder.mProductLocation.setText(mDataSet.get(position).getStore().getCity().getName());
        switch(mDataSet.get(position).getImage()){
            case 0:
                holder.mProductImage.setImageResource(R.drawable.mac); break;
            case 1:
                holder.mProductImage.setImageResource(R.drawable.alienware); break;
        }
        Bitmap bitmap = ((BitmapDrawable)holder.mProductThumbnail.getDrawable()).getBitmap();
        //holder.mProductThumbnail.setImageBitmap(Tool.getRoundedBitmap(bitmap));


        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail(position);

            }
        });

        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.send_to)));
            }
        });

        holder.mProductPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + mDataSet.get(position).getStore().getPhone()));
                context.startActivity(intent);
            }
        });

        holder.mEventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityDetail.class);
                intent.putExtra("ITEM", mDataSet.get(position));
                context.startActivity(intent);
            }
        });

        holder.mEventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetail(position);
            }
        });
    }

    private void showDetail(final int position){
            Intent intent = new Intent(context, ActivityDetail.class);
            intent.putExtra(Constants.EXTRA_ITEM, mDataSet.get(position));
            intent.putExtra(Constants.EXTRA_FRAGMENT, fragment);
            ((ActivityMain) context).startActivityForResult(intent, Constants.INTENT_DETAIL);
        }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button mDetail;
        public Button mShare;
        public TextView mProductTitle;
        public TextView mProductStore;
        public TextView mProductLocation;
        public TextView mProductPhone;
        public ImageView mProductImage;
        public ImageView mProductThumbnail;
        public RelativeLayout mEventLayout;
        public ViewHolder(View v) {
            super(v);
            mEventLayout = (RelativeLayout) v.findViewById(R.id.item_product_layout);
            mDetail = (Button) v.findViewById(R.id.item_product_detail);
            mProductTitle = (TextView) v.findViewById(R.id.item_product_title);
            mProductStore = (TextView) v.findViewById(R.id.item_product_store);
            mProductLocation = (TextView) v.findViewById(R.id.item_product_location);
            mProductPhone = (TextView) v.findViewById(R.id.item_product_phone);
            mProductImage = (ImageView) v.findViewById(R.id.item_product_image);
            mProductThumbnail = (ImageView) v.findViewById(R.id.item_product_thumbnail);
            mShare = (Button)v.findViewById(R.id.item_product_share);
        }
    }

}
