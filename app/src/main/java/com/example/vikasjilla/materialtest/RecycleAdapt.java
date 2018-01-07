package com.example.vikasjilla.materialtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Vikas Jilla on 04-01-2018.
 */


public class RecycleAdapt extends RecyclerView.Adapter<RecycleAdapt.MyViewHolder> {
    LayoutInflater mInflater;
    RecycleAdapt(Context context){
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.recycle_layout_item,parent,false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_recycle_item);
            mTextView = itemView.findViewById(R.id.textView_recycle_item);
        }
    }
}
