package com.example.vikasjilla.materialtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Vikas Jilla on 04-01-2018.
 */


public class RecycleAdapt extends RecyclerView.Adapter<RecycleAdapt.MyViewHolder> {
    LayoutInflater mInflater;
    int count = 4;
    ClickListener mClickListener;
    Context mContext;
    RecycleAdapt(Context context){
        mContext = context;
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
        return count;
    }

    void delete(int position){
        count--;
        notifyItemRemoved(position);
    }

    public void setListener(ClickListener clickListener){
        mClickListener = clickListener;
    }

    public interface ClickListener{
         void itemClicked(View v,int position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnTouchListener{
        ImageView mImageView;
        TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_recycle_item);
            mTextView = itemView.findViewById(R.id.textView_recycle_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext,""+getPosition(),Toast.LENGTH_SHORT).show();
            if(mClickListener != null){
                mClickListener.itemClicked(view,getPosition());
            }
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    }

}
